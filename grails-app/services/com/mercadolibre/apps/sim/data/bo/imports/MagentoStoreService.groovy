package com.mercadolibre.apps.sim.data.bo.imports

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import net.sf.json.JSONArray
import com.mercadolibre.apps.sim.data.bo.errors.ApiError
import com.mercadolibre.apps.sim.data.bo.core.User


class MagentoStoreService {

  final static String canonical_test_store_base = "http://ec2-107-22-49-30.compute-1.amazonaws.com/"


  /**
   * Returns a list of Map with the entityId (catalog_product.id) as Key and the entity data (all available)
   * as the value in the map
   *
   * @param userId - user where we retrieve their Magento store
   * @param limit  - max number of products
   * @param offset
   * @return
   */
  Map<String,Map> getMagentoProductsByUser(Integer userId, Integer limit = 100) {
    def productMap = [:]

    def builder = new HTTPBuilder()
    try {
      builder.request(canonical_test_store_base,
          groovyx.net.http.Method.GET,
          groovyx.net.http.ContentType.JSON) {
        uri.path = "/magento/api/rest/products"
        uri.query = [type: 'rest', limit: Math.min(limit,100)]
        headers.'Accept' = "*/*"

        response.success = { resp, json ->
          json.each() {
            (it.value as Map).put("category_id", getCategoryIdForProduct(it.key as Long))
            productMap[it.key] = it.value
          }
        }
        response.failure = { resp, json ->
          log.error resp.status
        }
      }
    }
    catch (Exception ex) {
      log.error ex.message, ex
    }
    //println productMap.'3'
    //return productMap.'3'

    productMap
  }


  def getMagentoProductsByUserAndCategory(Integer userId, Long categoryId) {
    return getMagentoProductsByUser(userId).findAll { it.value['category_id'] == categoryId }
  }


  /**
   * Return the category id for given product using Magento REST
   *
   * @param productId
   * @return
   */
  Long getCategoryIdForProduct(Long productId) {
    Long categoryId = 0L

    def builder = new HTTPBuilder()
    try {
      builder.request(canonical_test_store_base,
          groovyx.net.http.Method.GET,
          groovyx.net.http.ContentType.JSON) {
        uri.path = "/magento/api/rest/products/${productId}/categories"
        uri.query = [type: 'rest']
        headers.'Accept' = "*/*"

        response.success = { resp, json ->
          categoryId = (json[0]['category_id']) as Long
        }
        response.failure = { resp, json ->
          log.error resp.status
        }
      }
    }
    catch (Exception ex) {
      log.error ex.message, ex
    }
    return categoryId
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
  
  // Note: productId and itemId are equal in the catalog_product_entity and cataloginventory_stock_item tables in Magento's mysql db
  Double getInventoryForItem(Long itemId) {
    Double inventoryQty = 0

    def builder = new HTTPBuilder()
    try {
      builder.request(canonical_test_store_base,
          groovyx.net.http.Method.GET,
          groovyx.net.http.ContentType.JSON) {
        uri.path = "/magento/api/rest/stockitems/${itemId}"
        uri.query = [type: 'rest']
        headers.'Accept' = "*/*"

        response.success = { resp, json ->
          inventoryQty = (json['qty']) as Long
        }
        response.failure = { resp, json ->
          log.error resp.status
        }
      }
    }
    catch (Exception ex) {
      log.error ex.message, ex
    }
    return inventoryQty
  }
}
