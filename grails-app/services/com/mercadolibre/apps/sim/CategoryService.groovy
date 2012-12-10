package com.mercadolibre.apps.sim

import com.mercadolibre.apps.sim.data.bo.imports.meli.Category

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import net.sf.json.JSONArray
import grails.plugin.cache.Cacheable


class CategoryService {

  static transactional = false

  /**
   * Test category validity by its id and name
   * @param categoryId
   * @return
   */
  @Cacheable("categoryCache")
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
        log.warn "Category '${categoryId}' not found !!"
      }
      else {
        log.error ex.message, ex
      }
    }

    return valid
  }


  /**
   * Test category validity by its id and name - Fashion based category will have
   * under it some very strange bits for the Required extra fields
   *
   * @param categoryId - e.g. MLA1458
   * @return  true if a child of 'Ropa' , false otherwise
   */
  @Cacheable("categoryCache")
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
   * Turn the Meli category into a domain object
   *
   * @param categoryId
   * @return Return an object if the ML Category is found, null if not
   */
  @Cacheable("categoryCache")
  Category getCategory(String categoryId) {
    Boolean valid
    Category aCategory

    def builder = new HTTPBuilder("https://api.mercadolibre.com")
    try {
      builder.contentType = ContentType.JSON
      builder.get(path: "/categories/${categoryId}") { resp, json ->
        valid = (resp.status == 200 && json['id'] && json['name'])
        if (valid) {
          List parents = json['path_from_root'].collect { it.name } as List

          aCategory = new Category(meliId: json['id'], name: json['name'], fqn:  parents.toListString(100))
        }
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

    if (valid) {
      return aCategory
    }
    else {
      return null
    }
  }

  String getCategoryNotFoundMessage(String category = "MLX123") {
    def notFound = """{
        "message": "Category not found: ${category}",
        "error": "not_found",
        "status": 404,
        "cause": [
        ],
      }"""

    return notFound
  }


  /**
   * Find out from the category and the current colour if we can SKIP it

   * @param sourceList -  listOfPossibleColor id,name pairs
   * @param categoryId -   meliCategory
   * @param colorValue  -  '17'
   * @return true if colorValue is legit
   *
   * EXAMPLE usage

   if (!isColorValueSupported(categoryId, details['additionalAttributes'].find() { key == 'color' })) {
     useGray = true
   }
   *
   */
  Boolean isColorValueSupported(String meliCategory, String colorValue) {
    def sourceList = this.getFashionCategoryAttribute(meliCategory, "Color Primario")['values']
    if (!sourceList) {
      return false
    }
    else {
      return (getIdValueForColorValue(sourceList, colorValue) != null ? true : false)
    }
  }

  String getIdValueForColorValue(List sourceList, String colorName) {
    def attributeValues = sourceList.find() {
      colorName?.equalsIgnoreCase(it.name)
    }
    return attributeValues?.id
  }

  String getIdValueForSizeValue(List sourceList, String sizeName) {
    def attributeValues = sourceList.find() {
      it.name == sizeName
    }
    return attributeValues?.id
  }


  /**
   * Return id/name pairs which indicate legal values on ML in this category
   *
   * @param categoryId
   * @param attributeTag e.g. 'Talle'
   * @return Map
   */
  @Cacheable("categoryCache")
  Map getFashionCategoryAttribute(String categoryId, String attributeTag) {
    def attributeMap = null

    def builder = new HTTPBuilder("https://api.mercadolibre.com")
    try {
      builder.contentType = ContentType.JSON
      builder.get(path: "/categories/${categoryId}/attributes") { resp, json ->

        if ((json as JSONArray).isEmpty()) {
          attributeMap = [:]
        }
        else {
          (json as JSONArray).toList().each() { attr ->
            if (attributeTag == attr['name']) {
              attributeMap = [id: attr['id'], name: attr['name'], type: attr['type'], value_type: 'list', values: attr['values']]
            }
          }
        }
      }
    }
    catch (Exception ex) {
      log.error ex.message, ex
    }

    return attributeMap
  }


  /**
   * Assuming category count is zero prior to calling us - will load the entire tree
   * /site/MLA/categories/all
   *
   * @return number of categories loaded
   */
  def loadMeliCategories() {
	runAsync {
      String aLine

      BufferedReader bufferedReader = new BufferedReader(new FileReader("/tmp/mercadoCatFileIds.txt"))
      //BufferedReader bufferedReader = new BufferedReader(new StringReader(MeliFlatCategories.categoryIds))
	
	  // TODO: check to see if we have the latest category tree from meli
      while ( (aLine = bufferedReader.readLine()) != null ) {
        Category cat =
          getCategory(aLine.trim())

          if (cat?.save(flush: true)) {
            log.info "Saved ${cat}"
          }
        }
  	  }
    }
  }


final class MeliFlatCategories {
  def static categoryIds =
"""MLA9997
MLA78884
MLA44393
MLA5725
MLA4711
MLA1772
MLA86379
MLA11066
MLA11090
MLA86350
MLA86351
MLA86374
MLA86352
MLA4712
MLA61175
MLA86435
MLA86432
MLA86442
MLA86441
MLA86439
MLA99246
MLA86440
MLA86438
MLA86437
MLA86434
MLA86436
MLA86433
MLA61213
MLA86637
MLA86638
MLA86639
MLA86640
MLA86641
MLA99247
MLA86642
MLA86643
MLA86644
MLA86645
MLA86646
MLA86647
MLA61214
MLA86648
MLA86649
MLA86650
MLA86651
MLA86652
MLA99248
MLA86653
MLA86654
MLA86655
MLA86656
MLA86657
MLA86658
MLA61215
MLA86670
MLA86671
MLA86672
MLA86673
MLA86674
MLA99250
MLA86675
MLA86676
MLA86677
MLA86678
MLA86679
MLA86680
MLA61174
MLA86659
MLA86660
MLA86661
MLA86662
MLA86663
MLA99249
MLA86664
MLA86665
MLA86666
MLA86667
MLA86668
MLA86669
MLA86380
MLA22297
MLA11076
MLA86376
MLA86377
MLA61160
MLA61217
MLA61161
MLA61216
MLA59863
MLA61162
MLA61176
MLA86375
MLA22204
MLA86381
MLA86378
MLA86371
MLA3931
MLA22296
MLA22298
MLA61249
MLA22281
"""
}