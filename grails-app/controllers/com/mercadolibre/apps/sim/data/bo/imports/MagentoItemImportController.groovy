package com.mercadolibre.apps.sim.data.bo.imports

import com.mercadolibre.apps.sim.data.bo.errors.ApiError
import grails.converters.JSON
import com.mercadolibre.apps.sim.CategoryService
import com.mercadolibre.apps.sim.ImportService
import com.mercadolibre.apps.sim.data.bo.core.VanillaItemListing
import org.json.simple.ItemList
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
	static allowedMethods = [startUpload: "POST", showUploadProgress: "GET", start: "GET"]

  CategoryService categoryService
  ImportService importService
  MagentoStoreService magentoStoreService

  def startImport(StartMageCatalogSlurpCommand command) {
    def newItemId = importListingsFromMage(command)
    newItemId.each() {
      log.info "ML item listed https://api.mercadolibre.com/items/${it}"
    }
    render newItemId as String
  }

  def importListingsFromMage(StartMageCatalogSlurpCommand command) {
    log.info "importListingsFromMage:  using accessToken: " + session.ml_access_token

    def meliItemIds = []

    if (categoryService.isValidCategory(command.meliCategory)) {
      def allProduct = null
      if (command.productSelection?.toUpperCase().startsWith("ALL")) {
        log.info "Retrieving ALL products in Magento Store"
        allProduct = magentoStoreService.getMagentoProductsByUser(session.ml_caller_id)
      }
      else {
        log.info "Retrieving filtered by CategoryId: ${command.storeCategory}"
        allProduct = magentoStoreService.getMagentoProductsByUserAndCategory(session.ml_caller_id, command.storeCategory as Long)
      }
      log.info("Products found: " + allProduct.size())

      allProduct.each() { k,v ->
        def aProduct = v

        VanillaItemListing listing = new VanillaItemListing(category_id: command.meliCategory, listing_type_id: command.listingType,
          currency_id: "ARS", price: aProduct['regular_price_without_tax'], available_quantity: aProduct['is_saleable'] as Integer,
          title: aProduct['short_description'], description: aProduct['description'], seller_custom_field: aProduct['sku'],
          condition: "new", pictureURL: aProduct['image_url']
        )

        def newListingId = importService.pushItemToMarketplace2(listing, session.ml_access_token)

        meliItemIds << newListingId
        saveNewListing(newListingId)
      }
    }
    else {
      log.warn "Cannot support category_id id: ${command.meliCategory} in the current version"
    }

    return meliItemIds
  }

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


/*
	Each of these will trigger listings to Magentooo

	listingType=bronze
	productSelection=Selected Category Ids
	storeCategory=
	meliCategory=MLA78884
	buyingMode=buy_it_now=

*/

class StartMageCatalogSlurpCommand {
	String action
  String buyingMode
  String listingType
  String meliCategory
  String productSelection
  String storeCategory
}
