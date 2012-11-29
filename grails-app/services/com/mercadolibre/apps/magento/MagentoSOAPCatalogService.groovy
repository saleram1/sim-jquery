package com.mercadolibre.apps.magento

import magento.CatalogAssignedProduct
import magento.CatalogCategoryAssignedProductsRequestParam
import magento.CatalogCategoryAssignedProductsResponseParam
import magento.LoginParam
import magento.CatalogProductLinkAssignRequestParam
import magento.CatalogProductLinkListRequestParam
import magento.CatalogProductListRequestParam
import magento.Filters
import magento.AssociativeEntity
import magento.ComplexFilter
import magento.AssociativeArray
import magento.ComplexFilterArray

/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 11/21/12
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
class MagentoSOAPCatalogService extends MagentoSOAPBase {

  List getProductIdsInCategory(storeUrl, apiUser, apiKey, categoryId) {
    String sessionId = null

    try {
      if ((sessionId = initMagentoProxyForStore(storeUrl, apiUser, apiKey))) {
        log.info "Session: ${sessionId}"

        CatalogCategoryAssignedProductsRequestParam cap = new CatalogCategoryAssignedProductsRequestParam()
        cap.sessionId = sessionId
        cap.categoryId = categoryId
        cap.store = ""

        return getProductsAssignedToCategory(cap) as List
      }
      else {
        return Collections.emptyList()
      }
    }
    catch (Throwable tr) {
      log.error tr.message
    }
  }


  List getProductsAssignedToCategory(CatalogCategoryAssignedProductsRequestParam cap) {
    CatalogCategoryAssignedProductsResponseParam responseParam =
      mageProxy.getMageApiModelServerWsiHandlerPort().catalogCategoryAssignedProducts(cap)

    responseParam.result.complexObjectArray.each() { CatalogAssignedProduct product ->
      println "Got Product? type=${product.type}  Entity ID ${product.productId}   sku = ${product.sku}"
    }

    return responseParam.result.complexObjectArray.toList()
  }


  List getRelatedProductsForProductId(String sessionId, Integer entityId, String sku) {
    Filters filters = new Filters()
    filters.filter  = new AssociativeArray()
    filters.filter.complexObjectArray.add(new AssociativeEntity(key: "type_id", value: "simple"))

    AssociativeEntity likeThisSkuFilter = new AssociativeEntity(key: "like", value: "${sku}%")
    def filterWhereClause = new AssociativeArray()
    filterWhereClause.complexObjectArray.add(likeThisSkuFilter)

    // it is complex this time
    filters.complexFilter = new ComplexFilterArray()
    filters.complexFilter.complexObjectArray.add(new ComplexFilter(key: "sku", value: likeThisSkuFilter))

    def cplp = new CatalogProductListRequestParam()
    cplp.sessionId = sessionId
    cplp.filters = filters
    cplp.store = ""

    mageProxy.getMageApiModelServerWsiHandlerPort().catalogProductList(cplp).result.complexObjectArray.toList()
  }
}
