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

//
//      {accessToken=APP_USR-12815-120316-9aa7c965201328eb629eb8883c7990cd__B_K__-85958951, importJobId=1}
//
  def onMessage(aMessage) {
    log.info aMessage

    MagentoCatalogImportJob importJob

    int currentCount = 0
    MagentoCatalogImportJob.withTransaction {
      importJob = MagentoCatalogImportJob.get(aMessage.importJobId)
      currentCount = importJob.validItemsCount
    }

    importListingsFromMage(importJob, aMessage.callerId)


    // while not done - update the total items by ten per cent
    while (importJob.validItemsCount < importJob.totalItemsCount) {
      Thread.sleep(4800L)

      MagentoCatalogImportJob.withTransaction {
        importJob = MagentoCatalogImportJob.get(aMessage.importJobId)
        currentCount = importJob.validItemsCount
      }

      MagentoCatalogImportJob.withTransaction {
        importJob = MagentoCatalogImportJob.load(aMessage.importJobId)
        importJob.validItemsCount = currentCount + 10
        importJob.save()
      }
    }

    MagentoCatalogImportJob.withTransaction {
      importJob = MagentoCatalogImportJob.get(aMessage.importJobId)
      importJob.validItemsCount = 100
      importJob.totalItemsCount = 100
      importJob.status = 'COMPLETE'
      importJob.description = "really, this job is complete according to Mage"
      importJob.save(flush: true)
    }
    true
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
