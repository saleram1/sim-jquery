package com.mercadolibre.apps.magento

import magento.ArrayOfCatalogCategoryEntities
import magento.CatalogAssignedProduct
import magento.CatalogCategoryEntity
import magento.CatalogCategoryTreeRequestParam
import magento.CatalogCategoryTreeResponseParam
import magento.CatalogProductEntity

/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 11/26/12
 * Time: 10:58 AM
 * To change this template use File | Settings | File Templates.
 */
class MagentoSOAPCategoryService extends MagentoSOAPBase {

  static transactional = false

  List getCategoryTree(storeUrl, apiUser, apiKey, categoryId) {
    String sessionId = null

    try {
      if ((sessionId = initMagentoProxyForStore(storeUrl, apiUser, apiKey))) {
        log.info "Session: ${sessionId}"

        CatalogCategoryTreeRequestParam cat = new CatalogCategoryTreeRequestParam()
        cat.sessionId = sessionId

        // This is the top level category
        CatalogCategoryTreeResponseParam responseParam = mageProxy.getMageApiModelServerWsiHandlerPort().catalogCategoryTree(cat)

        // Get Children Categories
        def categoryList
        categoryList.add()
        categoryList.add(getChildrenCategories(cat))

        return categoryList as List
      }
      else {
        return Collections.emptyList()
      }
    }
    catch (Throwable tr) {
      log.error tr.message
    }
  }


  List getChildrenCategories(CatalogCategoryTreeRequestParam cat) {

    def childrenCategoryList

    CatalogCategoryTreeResponseParam responseParam = mageProxy.getMageApiModelServerWsiHandlerPort().catalogCategoryTree(cat)

    // First get the Categories at this level and append them to the childrenCategoryList
    ArrayOfCatalogCategoryEntities arrayOfCatalogCategoryEntities = responseParam.result.children
    List<CatalogCategoryEntity> listOfCatalogCategoryEntities = arrayOfCatalogCategoryEntities.getComplexObjectArray()
    listOfCatalogCategoryEntities.each {
      it.categoryId
      it.name
      it.parentId
    }

    ////TODO
    // IS THIS UNFINISHED ?
//	  // magento.ArrayOfCatalogCategoryEntities
//	  ArrayOfCatalogCategoryEntities arrayOfCatalogCategoryEntities = responseParam.result.children
//	  List<CatalogCategoryEntity> listOfCatalogCategoryEntities = arrayOfCatalogCategoryEntities.getComplexObjectArray()
//	  listOfCatalogCategoryEntities.each {
//		  it.categoryId
//	  }

    return null
  }
}
