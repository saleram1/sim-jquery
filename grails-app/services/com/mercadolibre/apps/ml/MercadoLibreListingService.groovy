package com.mercadolibre.apps.ml

import com.mercadolibre.apps.sim.data.bo.imports.MagentoStoreService

import com.mercadolibre.apps.sim.CategoryService
import magento.CatalogInventoryStockItemEntity
import com.mercadolibre.apps.sim.data.bo.core.FashionItemListing
import com.mercadolibre.apps.sim.data.bo.imports.MagentoCatalogImportJob
import com.mercadolibre.apps.sim.data.bo.core.ItemVariation
import grails.converters.JSON
import com.mercadolibre.apps.sim.ImportService

/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 12/8/12
 * Time: 4:31 PM
 * To change this template use File | Settings | File Templates.
 */
class MercadoLibreListingService {
  static transactional = false

  MagentoStoreService magentoStoreService
  CategoryService categoryService
  ImportService importService

  /**
   * List the product and its variations in Meli marketplace under meliCategoryToList
   *
   * @param aProduct - a parent (configurable) product with its related SKUs to list
   * @param callerId - seller id
   * @param quantityPercentToList - how much of available quantity to list (0.10 to 1.00 for 100%)
   * @param meliCategoryToList
   * @return
   */
  def listFashionItem(Map aProduct, MagentoCatalogImportJob job, Integer callerId, String accessToken) {
    FashionItemListing fashionListing
    Double quantityPercentToList = job.stockPercentage / 100.0d

    aProduct.each() { String key, List simpleProductIds ->
      if (simpleProductIds.isEmpty()) {
        return  // SKIP SKU ${key} has no variations - cannot list
      }
      List parentInventory = magentoStoreService.getMagentoProductInventoryByProductId(callerId, [key])

      Map details = magentoStoreService.getMagentoProductDetailsByProductId(callerId, parentInventory.get(0).productId)
      // build up parent object based on these title,description,price
      fashionListing =
        new FashionItemListing(title: details['name'], description: details['description'], category_id: job.meliCategory, listing_type_id: job.listingType, currency_id: "ARS", condition: "new")

      String colorValueId = decodeMeliColor(job.meliCategory, details['additionalAttributes'].find() { key == 'color' })

      //simpleProductIds array represents solely the INVENTORY for a given size - NO other bits are needed
      List inventoryList = magentoStoreService.getMagentoProductInventoryByProductId(callerId, simpleProductIds)

      Double availableQuantity
      simpleProductIds.eachWithIndex() { String sku, Integer idx ->
        String sizeValueId = decodeMeliSize(job.meliCategory, (sku.minus(key)))

        availableQuantity =
          getItemVariationStock((inventoryList.get(idx) as CatalogInventoryStockItemEntity), sku, quantityPercentToList)

        if (availableQuantity > 0.0) {
          fashionListing.variations.add(
            new ItemVariation(sku, sizeValueId, Math.round(availableQuantity * quantityPercentToList),
                new BigDecimal(details['price']), details['pictureURLs'], colorValueId)
          )
        }
      }
    }

    if (!fashionListing.variations.empty) {
      println (fashionListing as JSON)
      println importService.pushItemToMarketplace2(fashionListing, accessToken)
    }
  }

  def decodeMeliColor(String meliCategoryId, String colorName) {
    def sourceList = categoryService.getFashionCategoryAttribute(meliCategoryId, "Color Primario")['values']
    if (!sourceList) {
      return null
    }
    else {
      return (categoryService.getIdValueForColorValue(sourceList, colorName) ?: "92028")
    }
  }

  def decodeMeliSize(String meliCategoryId, String sizeName) {
    def sourceList = categoryService.getFashionCategoryAttribute(meliCategoryId, "Talle")['values']
    if (!sourceList) {
      return null
    }
    else {
      return (categoryService.getIdValueForSizeValue(sourceList, sizeName) ?: "82073")
    }
  }


  Double getItemVariationStock(CatalogInventoryStockItemEntity stockItemEntity, String sku, quantityPercentToList) {
    Boolean itemInStock = (stockItemEntity.isInStock == '1')
    Double quantityToList = (stockItemEntity.qty as Double)

    if (itemInStock && quantityToList >= (1.0d / quantityPercentToList)) {
      return quantityToList
    }
    else {
      log.warn("SKU ${sku} is too low or Out of stock.  Qty = ${quantityToList}")
      return 0.0d
    }
  }



  //MOVE IT
  def listRegularItem(Map aProduct, Integer callerId) {
    log.info aProduct
  }
}