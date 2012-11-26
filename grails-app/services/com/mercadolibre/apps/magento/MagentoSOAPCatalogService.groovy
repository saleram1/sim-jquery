package com.mercadolibre.apps.magento

import magento.CatalogAssignedProduct
import magento.CatalogCategoryAssignedProductsRequestParam
import magento.CatalogCategoryAssignedProductsResponseParam
import magento.LoginParam
import magento.MagentoService

/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 11/21/12
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
class MagentoSOAPCatalogService extends MagentoSOAPBase {

  List getProductIdsInCategory(storeUrl,
                               apiUser,
                               apiKey,
                               categoryId) {
    try {
      if (!mageProxy) {
        String session = initMagentoProxyForStore(storeUrl)
        sessionStores.put(new URL(storeUrl), session)
      }

      CatalogCategoryAssignedProductsRequestParam cap = new CatalogCategoryAssignedProductsRequestParam()
      cap.sessionId = sessionStores.get(new URL(storeUrl))
      cap.categoryId = categoryId
      cap.store = ""

      return getProductsAssignedToCategory(cap)
    }
    catch (Throwable tr) {
      log.error tr.message, tr
    }
  }


  List getProductsAssignedToCategory(CatalogCategoryAssignedProductsRequestParam cap) {
    CatalogCategoryAssignedProductsResponseParam responseParam =
      mageProxy.getMageApiModelServerWsiHandlerPort().catalogCategoryAssignedProducts(cap)

    responseParam.result.complexObjectArray.each() { CatalogAssignedProduct product ->
      println "Got Product? type=${product.type}  entityId=${product.productId}  SKU=${product.sku} "
    }

    return responseParam.result.complexObjectArray as List
  }
}