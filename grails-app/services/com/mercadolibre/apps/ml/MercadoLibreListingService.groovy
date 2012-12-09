package com.mercadolibre.apps.ml

import com.mercadolibre.apps.sim.data.bo.imports.MagentoStoreService
import magento.CatalogInventoryStockItemEntity

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


  def listFashionItem(Map aProduct, Integer callerId, Double quantityPercentToList, String meliCategoryToList) {
    aProduct.each() { String key, List simpleProductIds ->
      if (simpleProductIds.isEmpty()) {
        /// SKIP
        //throw new IllegalArgumentException("SKU ${key} has no variations - cannot list")
      }
      else {
        // must GET the entityId not the sku - in order to get anything useful
        List parentInventory = magentoStoreService.getMagentoProductInventoryByProductId(callerId, [key])

        Map details = magentoStoreService.getMagentoProductDetailsByProductId(callerId, parentInventory.get(0).productId)
        println details   /// build up parent object based on these title,description,price
        println ''

        /// check if job.meliCategory supports this product
        //     !isColorSupported(aProduct, "Roja", "MLA9997")
        List inventoryList = magentoStoreService.getMagentoProductInventoryByProductId(callerId, simpleProductIds)

        simpleProductIds.eachWithIndex() { String sku, Integer idx ->
          magento.CatalogInventoryStockItemEntity stockItemEntity =
            inventoryList.get(idx) as magento.CatalogInventoryStockItemEntity

          Boolean itemInStock = (stockItemEntity.isInStock == '1')
          Double quantityToList = (stockItemEntity.qty as Double)

          if (itemInStock && quantityToList >= (1.0d / quantityPercentToList)) {
            log.info "OK item ${sku} has enough to sell on Meli let's list this - add to Variations array"

            //pushItemToMarketPlace2
          }
          else {
            log.warn("SKU ${sku} is out of stock ${quantityToList}")
          }
        }
      }
    }
  }


  def listRegularItem(Map aProduct, Integer callerId) {
    log.info aProduct
  }


  /**
   * Find out from the category and the current colour if we can SKIP it

   * @param aProduct
   * @param meliCategory
   * @return
   */
  Boolean isColorSupported(Map aProduct, String colorDecode, String meliCategory) {
    return true
  }
}