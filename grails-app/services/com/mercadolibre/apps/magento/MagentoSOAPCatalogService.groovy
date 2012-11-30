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
import magento.CatalogProductEntity
import com.mercadolibre.apps.sim.data.bo.core.Shoppe

/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 11/21/12
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
class MagentoSOAPCatalogService extends MagentoSOAPBase {
  //
  // Should return only productId as related to SIMPLE product - such that inventory data can be retrieved
  // getSimpleProductIdsByCategory( storeURL, apiUsername, apiKey, categoryId )
  //
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

    // this list should be ALL - let the caller prune out the Configurable if desired
    responseParam.result?.complexObjectArray?.each() { CatalogAssignedProduct product ->
      println "Product? type=${product.type}  Entity ID ${product.productId}   sku = ${product.sku}"

      def related = getRelatedProductsForProductId(cap.sessionId, product.sku)
      related?.each { CatalogProductEntity prod ->
        println "    -->>  Child Product? type=${prod.type}  Quantity: ${prod.name}  sku = ${prod.sku} size = ${prod.websiteIds.toString()} "
      }
    }

    return responseParam.result.complexObjectArray.toList()
  }

  /**
   * Critical call to retrieve actual SIMPLE Saleable product id's
   *
   * @param sessionId
   * @param sku
   * @return
   */
  List getRelatedProductsForProductId(String sessionId, String sku) {
/*
    CatalogProductLinkListRequestParam linkListRequestParam = new CatalogProductLinkListRequestParam()
    linkListRequestParam.sessionId = sessionId
    linkListRequestParam.productId = entityId as String
    linkListRequestParam.type = "related"
    mageProxy.getMageApiModelServerWsiHandlerPort().catalogProductLinkList(linkListRequestParam).result.complexObjectArray.toList()
*/
    Filters filters = new Filters()
    // it is OVERcomplex this time
    filters.complexFilter = new ComplexFilterArray()
    filters.complexFilter.complexObjectArray.add(new ComplexFilter(key: "sku", value: new AssociativeEntity(key: "like", value: "${sku}%")))

    def cplp = new CatalogProductListRequestParam()
    cplp.sessionId = sessionId
    cplp.filters = filters
    cplp.store = ""

    mageProxy.getMageApiModelServerWsiHandlerPort().catalogProductList(cplp).result.complexObjectArray.toList()

  }


  /**
   *
   * @param sessionId
   * @param sku
   * @return
   */
  Map getSimpleProductDetailsForProductId(String sessionId, String sku) {
    return [:]
  }
}
