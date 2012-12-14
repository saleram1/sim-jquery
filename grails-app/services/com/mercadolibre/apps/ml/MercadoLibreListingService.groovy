package com.mercadolibre.apps.ml

import com.mercadolibre.apps.sim.CategoryService
import com.mercadolibre.apps.sim.data.bo.core.FashionItemListing
import com.mercadolibre.apps.sim.data.bo.core.ItemVariation
import com.mercadolibre.apps.sim.data.bo.imports.MagentoCatalogImportJob
import com.mercadolibre.apps.sim.data.bo.imports.MagentoStoreService
import magento.CatalogInventoryStockItemEntity
import com.mercadolibre.apps.sim.integration.fotter.FotterMagicDecoder
import com.mercadolibre.apps.sim.integration.fotter.FotterDescriptionMaker

/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 12/8/12
 * Time: 4:31 PM
 * To change this template use File | Settings | File Templates.
 */
class MercadoLibreListingService {
  static transactional = false
  static final default_currency_id = "ARS"
      ///https://v1.api.mercadolibre.com/sites/MLA

  CategoryService categoryService
  MagentoStoreService magentoStoreService
  MercadoLibreItemService mercadoLibreItemService

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

    aProduct.each() { String key, List simpleProductIds ->
      if (simpleProductIds.isEmpty()) {
        return  // SKIP SKU ${key} has no variations - cannot list
      }
      List parentInventory = magentoStoreService.getMagentoProductInventoryByProductId(callerId, [key])
      Map details = magentoStoreService.getMagentoProductDetailsByProductId(callerId, parentInventory.get(0).productId)

      //simpleProductIds array represents solely the inventory for a given size - NO other bits are needed
      List childInventoryList = magentoStoreService.getMagentoProductInventoryByProductId(callerId, simpleProductIds)
//MARCA
      String brandName = FotterMagicDecoder.decodeFotterMarcaOrBrand(details['additionalAttributes'].find() { it.key == 'marca'})
      fashionListing =
        new FashionItemListing(title: (brandName + details['name']), description: FotterDescriptionMaker.getDescriptionText(job.htmlDescription, details['description']),
            category_id: job.meliCategory, listing_type_id: job.listingType, currency_id: default_currency_id, condition: "new")
//COLOUR
      def sourceList = categoryService.getFashionCategoryAttribute(job.meliCategory, "Color Primario")['values']
      String colorValueId = FotterMagicDecoder.decodeFotterColor(sourceList, details['additionalAttributes'].find() { it.key == 'color' })

//SIZES
      def sourceList2 = categoryService.getFashionCategoryAttribute(job.meliCategory, "Talle")['values']

      // TODO - use strictly the job flag to go into the child product
      if (job.sizeAppendedToSKU || true) {
        simpleProductIds.eachWithIndex() { String sku, Integer idx ->
          Long availableQuantity =
            getItemVariationStock((childInventoryList.get(idx) as CatalogInventoryStockItemEntity), sku, job.stockPercentage / 100.0d)

          if (availableQuantity >= 1L) {
            String sizeValueId = FotterMagicDecoder.decodeFotterSize(sourceList2, (sku.minus(key)))

            fashionListing.variations.add(
              new ItemVariation(sku, sizeValueId, availableQuantity,
                  new BigDecimal(details['price']), details['pictureURLs'], colorValueId)
            )
          }
        }
      }
    }

    if (!fashionListing.variations.empty) {
      log.info "Attempting to list Meli ->  ${fashionListing} with ${fashionListing.variations.size()} Variations"
      return mercadoLibreItemService.postItemToMarketplaceEx("<<SKU coming soon>>", fashionListing, accessToken, job)
    }
  }


  def listRegularItem() {

  }

  /**
   * Get the quantity available for this sku, based on Stock qty and quantityPercent
   *
   * @param stockItemEntity
   * @param sku  - only needed for potential Out-of-stock warn
   * @param quantityPercentToList  10% 50%  100%
   * @return 0.0 if not enough stock - quantityTotal otherwise
   */
  long getItemVariationStock(CatalogInventoryStockItemEntity stockItemEntity, String sku, quantityPercentToList) {
    Boolean itemInStock = (stockItemEntity.isInStock == '1')
    Double quantityAvailableToStore = (stockItemEntity.qty as Double)

    if (itemInStock && quantityAvailableToStore >= (1.0d / quantityPercentToList)) {
      return Math.round(quantityAvailableToStore * quantityPercentToList)
    }
    else {
      //log.warn("SKU ${sku} is low or Out of stock.  Qty = ${quantityAvailableToStore}")
      return 0
    }
  }
}