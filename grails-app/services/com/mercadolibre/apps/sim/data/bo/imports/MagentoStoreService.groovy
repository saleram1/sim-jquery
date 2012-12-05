package com.mercadolibre.apps.sim.data.bo.imports

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import net.sf.json.JSONArray
import com.mercadolibre.apps.sim.data.bo.errors.ApiError
import com.mercadolibre.apps.sim.data.bo.core.User
import com.mercadolibre.apps.magento.MagentoSOAPCatalogService


class MagentoStoreService {

  MagentoSOAPCatalogService magentoSOAPCatalogService

  final static String dev_and_test_store_base = "http://ec2-107-22-49-30.compute-1.amazonaws.com/"


  Integer getMagentoProductCountByUserAndCategory(Integer callerId, Integer categoryId) {
    def magentoBaseURI = null
    def apiUser
    def apiKey

    User.withTransaction() {
      if (User.findByCallerId(callerId)) {
        User shoppeUser = User.findByCallerId(callerId)
        magentoBaseURI = shoppeUser.company.webAddress
        apiUser = shoppeUser.company.apiUser
        apiKey  = shoppeUser.company.apiKey
      }
    }

    def productStubs =
      magentoSOAPCatalogService.getProductIdsInCategory(magentoBaseURI, apiUser, apiKey, categoryId)

    return productStubs?.size()
  }


  List getMagentoProductsByUserAndCategory(Integer callerId, Integer categoryId) {
    def magentoBaseURI = null
    def apiUser
    def apiKey

    User.withTransaction() {
      if (User.findByCallerId(callerId)) {
        User shoppeUser = User.findByCallerId(callerId)
        magentoBaseURI = shoppeUser.company.webAddress
        apiUser = shoppeUser.company.apiUser
        apiKey  = shoppeUser.company.apiKey

        log.info "Shoppe URL is ${magentoBaseURI} ... connecting as user ${apiUser}"
        log.info "Mage Category Id is ${categoryId}"
        // logging
      }
    }

    def productStubs =
      magentoSOAPCatalogService.getProductIdsInCategory(magentoBaseURI, apiUser, apiKey, categoryId)

    if (!productStubs) {
      return Collections.emptyList()
    }
    else {
      return productStubs
    }
  }


  /**
   * @TODO have to make a change in the signupController such that the webAddress represents the Seller's
   * Magento store
   *
   * @param callerId
   * @return
   */
  String getStoreBaseURIForUser(Integer callerId) {
    User aUser = User.findByCallerId(callerId)

    if (aUser) {
      aUser.company.webAddress
    }
    else {
      ""
    }
  }
}
