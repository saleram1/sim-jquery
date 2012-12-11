package com.mercadolibre.apps.magento

import com.mercadolibre.apps.ml.MercadoLibreListingService
import com.mercadolibre.apps.sim.CategoryService
import com.mercadolibre.apps.sim.data.bo.core.ItemListing
import com.mercadolibre.apps.sim.data.bo.errors.ApiError
import com.mercadolibre.apps.sim.data.bo.imports.MagentoCatalogImportJob
import com.mercadolibre.apps.sim.data.bo.imports.MagentoStoreService

/**
 * Called by the JMS Queue handler after each job request is saved
 *
 * User: saleram
 */
class MagentoSimpleProductSlurperService {
  static transactional = false
  static exposes = ['jms']
  static destination = "queue.job.kickoff.notification"

  CategoryService categoryService
  MagentoStoreService magentoStoreService
  MercadoLibreListingService mercadoLibreListingService


  /**
   * Process all Simple Product in this category (details in catalogImportJob) and turn as many as possible
   * into MLA listings
   *
   * @param aMessage - contains accessToken, callerId, importJobId
   * @return success flag
   */
  def onMessage(aMessage) {
    log.info "Start Product Slurper Message:\n" + aMessage

    MagentoCatalogImportJob importJob = getCatalogImportJob(aMessage.importJobId)

    def allProductIds
    try {
      allProductIds =
        magentoStoreService.getMagentoProductsByUserAndCategory(aMessage.callerId, importJob.storeCategory as Integer)
      updateImportJobTotalListings(importJob, allProductIds.size())
      createMercadoLibreListings(allProductIds, importJob, aMessage.callerId, aMessage.accessToken)
    }
    catch (Throwable tr) {
      log.error "Exception on handling ${aMessage}"
      log.error tr.message, tr
      throw tr
    }
  }

  def createMercadoLibreListings(List productIds, MagentoCatalogImportJob importJob, Integer callerId, String accessToken) {
    log.info("No. of Products found: " + productIds?.size())

    Integer itemsListedWithMeli = 0
    Boolean isFashionista = categoryService.isFunkyFashionFootwearCategory(importJob.meliCategory)
//runAsync {
    long startTime = System.currentTimeMillis()

    productIds?.eachWithIndex { Map aProduct, Integer index ->
      Object meliListingResult
      if (isFashionista) {
        meliListingResult = mercadoLibreListingService.listFashionItem(aProduct, importJob, callerId, accessToken)

        if (meliListingResult instanceof ItemListing) {
          addToSmeliListings(importJob, meliListingResult)
        }
        else if (meliListingResult instanceof ApiError) {
          addToSmeliErrs(importJob, meliListingResult)
        }
      }
      else {
        meliListingResult = mercadoLibreListingService.listRegularItem(aProduct, importJob, callerId, accessToken)
      }
      //updateImportJobListingsOrErrors(importJob, meliListingId, aProduct)  ONCE COMPLETED INTERNAL BETA REVIEW
      updateImportJobProgress(importJob, ++itemsListedWithMeli)
    }

    //DONE!
    updateImportJobProgress(importJob, productIds.size(), 'COMPLETE')
    log.info "Job completed in ${(System.currentTimeMillis() - startTime / 1000)}s...."
  }


//HELPER METHODS
  /**
   * MagentoCatalogImportJob.get
   *
   * @return MagentoCatalogImportJob or null if not found
   */
  MagentoCatalogImportJob getCatalogImportJob(jobId) {
    MagentoCatalogImportJob importJob
    MagentoCatalogImportJob.withTransaction {
      importJob = MagentoCatalogImportJob.get(jobId)
    }
    importJob
  }


  void updateImportJobTotalListings(MagentoCatalogImportJob importJob, Integer totalItemsCount) {
    MagentoCatalogImportJob.withTransaction {
      importJob = MagentoCatalogImportJob.load(importJob.id)
      importJob.totalItemsCount = totalItemsCount
      if (importJob.save(flush: true)) {
        log.info "Updated size of job to: ${totalItemsCount}"
      }
    }
  }


  void updateImportJobProgress(MagentoCatalogImportJob importJob, Integer validItemsCount, String statusChange = 'PENDING') {
    MagentoCatalogImportJob.withTransaction {
      importJob = MagentoCatalogImportJob.load(importJob.id)
      importJob.validItemsCount = validItemsCount
      importJob.status = statusChange
      if (importJob.save(flush: true)) {
        log.info "Updated job items listed to: ${validItemsCount}"
      }
    }
  }


  void updateImportJobListingsOrErrors(MagentoCatalogImportJob importJob, String itemId, Map aProduct) {
    MagentoCatalogImportJob.withTransaction {
      importJob = MagentoCatalogImportJob.load(importJob.id)

      if (itemId && itemId.startsWith("ML")) {
        def myListing = new ItemListing(mercadoLibreItemId: itemId)
        myListing.save()
        importJob.addToListings(myListing)
      }
      else {
        def anError = new ApiError(cause: [aProduct], code: itemId, message: getMessage(itemId))
        anError.save()
        importJob.addToErrs(anError)
      }
      if (importJob.save(flush: true)) {
        log.info "Updated job associations"
      }
    }
  }


// GORM helpers
  def addToSmeliListings(MagentoCatalogImportJob importJob, ItemListing listing) {
    MagentoCatalogImportJob.withTransaction {
      listing.save(flush: true)
      importJob.addToListings(listing)
      importJob.save()
    }
  }

  def addToSmeliErrs(MagentoCatalogImportJob importJob, ApiError apiError) {
    MagentoCatalogImportJob.withTransaction {
      apiError.save(flush: true)
      importJob.addToErrs(apiError)
      importJob.save()
    }
  }


  String getMessage(code) {
    "Validation error"
  }
}