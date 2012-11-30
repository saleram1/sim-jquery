package com.mercadolibre.apps.sim.data.bo.imports

import com.mercadolibre.apps.sim.CategoryService
import com.mercadolibre.apps.sim.ImportService
import com.mercadolibre.apps.sim.data.bo.core.VanillaItemListing

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


  def startImport(StartMagentoExportCommand command) {
    def newItemId = importListingsFromMage(command)
    render newItemId as String
  }


  def importListingsFromMage(StartMagentoExportCommand command) {
    log.info "importListingsFromMage:  using accessToken: " + session.ml_access_token
    def meliItemIds = []

/* CANNOT SUPPORT ALL PRODUCT atm --   if (command.productSelection?.toUpperCase().startsWith("ALL")) {
        log.info "Retrieving ALL products in Magento Store"
        allProduct = magentoStoreService.getMagentoProductsByUser(session.ml_caller_id)
      }
      else {  */

    if (categoryService.isValidCategory(command.meliCategory)) {
      log.info "Retrieving filtered by CategoryId: ${command.storeCategory}"
      def allProduct = magentoStoreService.getMagentoProductsByUserAndCategory(session.ml_caller_id, command.storeCategory)

      log.info("Products found: " + allProduct.size())
/////////////////////////////////////
//      allProduct.each() { k,v ->
//        def aProduct = v
/////////////////////////////////////
      allProduct.each { aProduct ->
        meliItemIds << (aProduct.sku.toString())

        // Step 1 - get associated products if configurable type


        // Step 2 - retrieve details for each of that collection and store as a ItemVariation

        // Step 3 - Map size and color to the ML codes tables for each of the variations


        // Step 4 [FINAL] - combine 'parent' configurable product PLUS associated and pass all those with non
        // non-zero qty to the Meli listing

      }
    } //endif
    else {
      log.warn "Cannot support category_id id: ${command.meliCategory} in the current version. Please upgrade to 1.1"
    }

    return meliItemIds
  }


/*
        VanillaItemListing listing = new VanillaItemListing(category_id: command.meliCategory, listing_type_id: command.listingType,
          currency_id: "ARS", price: aProduct['regular_price_without_tax'], available_quantity: aProduct['is_saleable'] as Integer,
          title: aProduct['short_description'], description: aProduct['description'], seller_custom_field: aProduct['sku'],
          condition: "new", pictureURL: aProduct['image_url']
        )

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


/*
  Each of these will trigger an export of Stock Items from Magento and create new listings on ML

    productSelection=Products in Selected Categories

    sizeAttributeName=size
    sizeAppendedToSKU=on
    colorAttributeName=color
    storeCategory=3
    meliCategory=MLA9997
    buyingMode=buy_it_now
    action=startImport
    controller=magentoItemImport

 */
class StartMagentoExportCommand {
  String buyingMode
  String listingType
  String meliCategory
  String productSelection
  Integer storeCategory
  String sizeAttributeName
  Boolean sizeAppendedToSKU
  Boolean colorAppendedToSKU
  String colorAttributeName
}
