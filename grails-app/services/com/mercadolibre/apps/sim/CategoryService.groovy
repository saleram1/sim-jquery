package com.mercadolibre.apps.sim

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType

import net.sf.json.JSONArray

class CategoryService {

  static transactional = false

  /**
   * Test category validity by its id and name
   * @param categoryId
   * @return
   */
  Boolean isValidCategory(String categoryId) {
    Boolean valid = false

    def builder = new HTTPBuilder("https://api.mercadolibre.com")

    try {
      builder.contentType = ContentType.JSON
      builder.get(path: "/categories/${categoryId}") { resp, json ->
        valid = (resp.status == 200 && json['id'] && json['name'] && json['total_items_in_this_category'] > 0)
      }
    }
    catch (Exception ex) {
      if (ex?.message == 'Not Found') {
        valid = false
        log.warn "Category ${categoryId} not found !!"
      }
      else {
        log.error ex.message, ex
      }
    }

    return valid
  }


  /**
   * Test category validity by its id and name
   * @param categoryId
   * @return
   */
  Boolean isFunkyFashionFootwearCategory(String categoryId) {
    Boolean isFunky = false

    def builder = new HTTPBuilder("https://api.mercadolibre.com")
    try {
      builder.contentType = ContentType.JSON
      builder.get(path: "/categories/${categoryId}/attributes") { resp, json ->
        isFunky = (resp.status == 200 && !(json as JSONArray).isEmpty())
      }
    }
    catch (Exception ex) {
      log.error ex.message, ex
    }
    return isFunky
  }


  /**
   * Search by category id instead of query string
   * @param siteId
   * @param friendlyCategoryId
   * @param limit
   * @param offset
   * @return
   */
  List findAllItemsByCategoryId(String siteId, String friendlyCategoryId, Integer limit = 200, Integer offset = 0) {
    def itemListings = []

    withHttp(uri: globalConfigService.get("config.apiBaseURL")) {
      def responseData = get(path: "/sites/${siteId}/search",
          query: [category: friendlyCategoryId, limit: limit, offset: offset, max: limit])
      assert responseData instanceof net.sf.json.JSONObject

      responseData['results'].eachWithIndex() { item, idx ->
        items.add(item['id'])
      }
    }
    return items
  }
}
