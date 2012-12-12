package com.mercadolibre.apps.sim.data.bo.imports

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import net.sf.json.JSONArray
import com.mercadolibre.apps.sim.data.bo.errors.ApiError
import com.mercadolibre.apps.sim.data.bo.core.User
import com.mercadolibre.apps.magento.MagentoSOAPCatalogService
import com.mercadolibre.apps.magento.MageConnectionDetails


class MagentoStoreService {

  MagentoSOAPCatalogService magentoSOAPCatalogService


  def findShoppeDetailsByCallerId(Integer callerId) {
    String magentoBaseURI, apiUser, apiKey

    User.withTransaction() {
      if (User.findByCallerId(callerId)) {
        User shoppeUser = User.findByCallerId(callerId)
        magentoBaseURI = shoppeUser.company.webAddress
        apiUser = shoppeUser.company.apiUser
        apiKey  = shoppeUser.company.apiKey
      }
    }
    [magentoBaseURI, apiUser, apiKey]
  }

//METHODS
  List getMagentoProductsByUserAndCategory(Integer callerId, Integer categoryId) {
    withStopwatch("SOAPCatalogService.getProductIdsInCategory") {
      def (magentoBaseURI, apiUser, apiKey) = findShoppeDetailsByCallerId(callerId)
      def productStubs =
        magentoSOAPCatalogService.getProductIdsInCategory(magentoBaseURI, apiUser, apiKey, categoryId)

      if (!productStubs) {
        return Collections.emptyList()
      }
      else {
        return productStubs
      }
    }
  }

  /**
   * Adds three map buckets to the empty map - containing details and images
   *
   * @param callerId
   * @param productId  - sku
   * @return
   */
  Map getMagentoProductDetailsByProductId(Integer callerId, String productId) {
    withStopwatch("SOAPCatalogService.getProductDetails") {
      log.info "getMagentoProductDetailsByProductId: Caller Id is: ${callerId}  Mage Product Id is: ${productId}"

      def (magentoBaseURI, apiUser, apiKey) = findShoppeDetailsByCallerId(callerId)
      MageConnectionDetails mcd =
        magentoSOAPCatalogService.initMagentoProxyForStore(magentoBaseURI, apiUser, apiKey)

      Map allDetailsMap = [:]
      allDetailsMap.putAll(magentoSOAPCatalogService.getProductDetails(mcd.mageProxy, mcd.sessionId, productId))
      //allDetailsMap.putAll(magentoSOAPCatalogService.getProductImages(mcd.mageProxy, mcd.sessionId, productId))

      return allDetailsMap
    }
  }


  List getMagentoProductInventoryByProductId(Integer callerId, List productIds) {
    withStopwatch("SOAPCatalogService.getProductStockAttributes") {
      def (magentoBaseURI, apiUser, apiKey) = findShoppeDetailsByCallerId(callerId)

      MageConnectionDetails mcd =
        magentoSOAPCatalogService.initMagentoProxyForStore(magentoBaseURI, apiUser, apiKey)

      return magentoSOAPCatalogService.getProductStockAttributes(mcd.mageProxy, mcd.sessionId, productIds)
    }
  }
}