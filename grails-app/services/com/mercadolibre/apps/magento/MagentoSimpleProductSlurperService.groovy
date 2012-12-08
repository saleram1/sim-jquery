package com.mercadolibre.apps.magento

import com.mercadolibre.apps.sim.data.bo.imports.MagentoCatalogImportJob
import com.mercadolibre.apps.sim.CategoryService
import com.mercadolibre.apps.sim.data.bo.imports.MagentoStoreService

/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 11/30/12
 * Time: 8:48 PM
 * To change this template use File | Settings | File Templates.
 */
class MagentoSimpleProductSlurperService {

  CategoryService categoryService
  MagentoStoreService magentoStoreService

  static transactional = false
  static exposes = ['jms']
  static destination = "queue.job.kickoff.notification"

  /**
   * Process all Simple Product in this category (details in catalogImportJob) and turn as many as possible
   * into MLA listings
   *
   * @param aMessage - contains accessToken, callerId, importJobId
   * @return success flag
   */
  def onMessage(aMessage) {
    log.info "Start Product Slurper Message:\n" + aMessage

    MagentoCatalogImportJob importJob
    MagentoCatalogImportJob.withTransaction {
      importJob = MagentoCatalogImportJob.get(aMessage.importJobId)
    }

    def allProductIds
    try {
      allProductIds =
        magentoStoreService.getMagentoProductsByUserAndCategory(aMessage.callerId, importJob.storeCategory as Integer)
    }
    catch (Throwable tr) {
      log.error tr.message
    }


    MagentoCatalogImportJob.withTransaction {
      importJob = MagentoCatalogImportJob.load(aMessage.importJobId)
      importJob.totalItemsCount = allProductIds.size()
      if (importJob.save()) {
        log.info "Updated size of job to: ${allProductIds.size()}"
      }
    }

    //importListingsFromMage(importJob, aMessage.callerId, aMessage.accessToken)
    return
  }


  def importListingsFromMage(MagentoCatalogImportJob job, Integer callerId, String accessToken = "") {
    def meliItemIds = []

    if (categoryService.isValidCategory(job.meliCategory)) {
      log.info "Retrieving filtered by CategoryId: ${job.storeCategory}"
      def allProduct = magentoStoreService.getMagentoProductsByUserAndCategory(callerId, job.storeCategory)

      log.info("Products found: " + allProduct?.size())

      allProduct?.each { aProduct ->
        meliItemIds << (aProduct.sku.toString())


      }
    } //endif
    else {
      log.warn "Cannot support category_id id: ${job.meliCategory} in the current version. Please upgrade to 1.1"
    }

    return meliItemIds
  }


/*
        VanillaItemListing listing = new VanillaItemListing(category_id: job.meliCategory, listing_type_id: job.listingType,
          currency_id: "ARS", price: aProduct['regular_price_without_tax'], available_quantity: aProduct['is_saleable'] as Integer,
          title: aProduct['short_description'], description: aProduct['description'], seller_custom_field: aProduct['sku'],
          condition: "new", pictureURL: aProduct['image_url']
        )

*/
}
