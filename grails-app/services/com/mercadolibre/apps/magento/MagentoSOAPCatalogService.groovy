package com.mercadolibre.apps.magento

import magento.CatalogAssignedProduct
import magento.CatalogCategoryAssignedProductsRequestParam
import magento.CatalogCategoryAssignedProductsResponseParam
import magento.LoginParam

/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 11/21/12
 * Time: 12:06 PM
 * To change this template use File | Settings | File Templates.
 */
class MagentoSOAPCatalogService {

  magento.MagentoService mageProxy = null


  List getProductIdsInCategory(storeUrl, apiUser, apiKey, categoryId) {
    try {
      if (!mageProxy)
        mageProxy = new magento.MagentoService(new URL("http://ec2-107-22-49-30.compute-1.amazonaws.com/magento/index.php/api/v2_soap?wsdl=1"))

      LoginParam lp = new LoginParam()
      lp.username = "m2m"
      lp.apiKey   = "2dxkp14lggreh7f5m8ejqmzyskwhzoj0"

      String session = mageProxy.getMageApiModelServerWsiHandlerPort().login(lp).result
      println "SessionID: ${session}"

      CatalogCategoryAssignedProductsRequestParam cap = new CatalogCategoryAssignedProductsRequestParam()
      cap.sessionId = session
      cap.categoryId = categoryId
      cap.store = ""

      getProductsAssignedToCategory(cap)

      cap.categoryId = 4
      getProductsAssignedToCategory(cap)
    }
    catch (Throwable tr) {
      log.error tr.message, tr
    }
  }

  List getProductsAssignedToCategory(CatalogCategoryAssignedProductsRequestParam cap) {
    CatalogCategoryAssignedProductsResponseParam responseParam =
      mageProxy.getMageApiModelServerWsiHandlerPort().catalogCategoryAssignedProducts(cap)

    responseParam.result.complexObjectArray.each() { CatalogAssignedProduct product ->
      println "Got Product? type=${product.type}  ${product.productId} = ${product.sku} "
    }

    return responseParam.result.complexObjectArray as List
  }

}
