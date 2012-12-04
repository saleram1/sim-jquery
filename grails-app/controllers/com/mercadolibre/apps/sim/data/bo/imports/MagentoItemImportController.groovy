package com.mercadolibre.apps.sim.data.bo.imports

import com.mercadolibre.apps.sim.CategoryService
import com.mercadolibre.apps.sim.ImportService
import com.mercadolibre.apps.sim.data.bo.core.ItemListing

/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 11/8/12
 * Time: 3:35 PM
 * To change this template use File | Settings | File Templates.
 */
class MagentoItemImportController {

	static scaffold = true
	static allowedMethods = [startImport: "POST", showUploadProgress: "GET", start: "GET"]


/*
  def startImport(StartMagentoExportCommand command) {
    def newItemId = importListingsFromMage(command)
    render newItemId as String

        def newListingId = importService.pushItemToMarketplace2(listing, session.ml_access_token)

        meliItemIds << newListingId
        saveNewListing(newListingId)
*/


  def saveNewListing(String itemId) {
    log.info "Saving item listing for: ${itemId}\n\n"

    ItemListing.withTransaction(){
      def il = new ItemListing(mercadoLibreItemId: itemId)
      if (il.validate()) {
        il.save(flush:  true)
      }
      else {
        il.errors.fieldErrors.each {
          log.error it
        }
      }
    }
  }
}