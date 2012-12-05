package com.mercadolibre.apps.magento

import magento.ArrayOfString
import magento.AssociativeEntity
import magento.CatalogAssignedProduct
import magento.CatalogAttributeOptionEntityArray
import magento.CatalogCategoryAssignedProductsRequestParam
import magento.CatalogCategoryAssignedProductsResponseParam
import magento.CatalogProductAttributeMediaListRequestParam
import magento.CatalogProductAttributeMediaListResponseParam
import magento.CatalogProductAttributeOptionsRequestParam
import magento.CatalogProductAttributeOptionsResponseParam
import magento.CatalogProductEntity
import magento.CatalogProductImageEntityArray
import magento.CatalogProductInfoRequestParam
import magento.CatalogProductListRequestParam
import magento.CatalogProductReturnEntity
import magento.ComplexFilter
import magento.ComplexFilterArray
import magento.Filters
import magento.CatalogInventoryStockItemListRequestParam
import magento.CatalogInventoryStockItemListResponseParam

/**
 * Catalog related SOAP calls to Module: Mage_Catalog
 *
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 12/12/12
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


  /**
   *
   * @param cap
   * @return List < String >  sku or productId
   */
  List getProductsAssignedToCategory(CatalogCategoryAssignedProductsRequestParam cap) {
    def productIds = []

    CatalogCategoryAssignedProductsResponseParam responseParam =
      mageProxy.getMageApiModelServerWsiHandlerPort().catalogCategoryAssignedProducts(cap)

    // this list should be ALL - let the caller prune out the Configurable if desired
    responseParam.result?.complexObjectArray?.each() { CatalogAssignedProduct product ->
      println "Product? type=${product.type}  Entity ID ${product.productId}   sku = ${product.sku}"

      productIds << "${product.sku}"

      def related = getRelatedProductsForProductId(cap.sessionId, product.sku)
      related?.each { CatalogProductEntity prod ->
        println "    -->>  Child Product? type=${prod.type}  Quantity: ${prod.name}  sku = ${prod.sku} size = ${prod.websiteIds.toString()} "
        if (!productIds.contains("${prod.sku}")) {
          productIds << "${prod.sku}"
        }
      }
    }

    println getProductStockAttributes(cap.sessionId, productIds)

    productIds
    //  return responseParam.result.complexObjectArray.toList()
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
   * Critical call to retrieve actual SIMPLE Saleable product id's
   * skuList << "${product.sku}"
   *
   * @param sessionId
   * @param productId
   * @return
   */
  Map getProductDetails(String sessionId, String productId) {

    def productDetailMap = [:]

    def cpip = new CatalogProductInfoRequestParam()
    cpip.productId = productId
    cpip.sessionId = sessionId
    cpip.store = ""

    CatalogProductReturnEntity catalogProductReturnEntity = mageProxy.getMageApiModelServerWsiHandlerPort().catalogProductInfo(cpip).result

    ArrayOfString categoryArray = catalogProductReturnEntity.categories
    def categories = categoryArray.complexObjectArray

    ArrayOfString websiteArray = catalogProductReturnEntity.websites
    def websites = websiteArray.complexObjectArray

    ArrayOfString categoryIdArray = catalogProductReturnEntity.categoryIds
    def categoryIds = categoryIdArray.complexObjectArray

    ArrayOfString webSiteIdsArray = catalogProductReturnEntity.websiteIds
    def websiteIds = webSiteIdsArray.complexObjectArray


    productDetailMap = ["productId": catalogProductReturnEntity.productId,
        "sku": catalogProductReturnEntity.sku,
        "set": catalogProductReturnEntity.set,
        "type": catalogProductReturnEntity.type,
        "categories": categories,
        "websites": websites,
        "createdAt": catalogProductReturnEntity.createdAt,
        "updatedAt": catalogProductReturnEntity.updatedAt,
        "typeId": catalogProductReturnEntity.typeId,
        "type": catalogProductReturnEntity.type,
        "name": catalogProductReturnEntity.name,
        "description": catalogProductReturnEntity.description,
        "shortDescription": catalogProductReturnEntity.shortDescription,
        "weight": catalogProductReturnEntity.weight,
        "status": catalogProductReturnEntity.status,
        "urlKey": catalogProductReturnEntity.urlKey,
        "urlPath": catalogProductReturnEntity.urlPath,
        "visibility": catalogProductReturnEntity.visibility,
        "categoryIds": categoryIds,
        "websiteIds": websiteIds,
        "hasOptions": catalogProductReturnEntity.hasOptions,
        "giftMessageAvailable": catalogProductReturnEntity.giftMessageAvailable,
        "price": catalogProductReturnEntity.price,
        "specialPrice": catalogProductReturnEntity.specialPrice,
        "specialFromDate": catalogProductReturnEntity.specialFromDate,
        "specialToDate": catalogProductReturnEntity.specialToDate,
        "taxClassId": catalogProductReturnEntity.taxClassId,
        "tierPrice": catalogProductReturnEntity.tierPrice,
        "metaTitle": catalogProductReturnEntity.metaTitle,
        "metaKeyword": catalogProductReturnEntity.metaKeyword,
        "metaDescription": catalogProductReturnEntity.metaDescription,
        "customDesign": catalogProductReturnEntity.customDesign,
        "customLayoutUpdate": catalogProductReturnEntity.customLayoutUpdate,
        "optionsContainer": catalogProductReturnEntity.optionsContainer,
        "additionalAttributes": catalogProductReturnEntity.additionalAttributes,
        "enableGoogleCheckout": catalogProductReturnEntity.enableGooglecheckout
    ]
    productDetailMap
  }


  /**
   * Get a list of images for a productId
   *
   * @param sessionId
   * @param productId
   * @return List of images
   */
  List getProductImages(String sessionId, String productId) {
    def productImageList = []

    def cpamlp = new CatalogProductAttributeMediaListRequestParam()
    cpamlp.sessionId = sessionId
    cpamlp.productId = productId
    cpamlp.store = ""

    CatalogProductAttributeMediaListResponseParam catalogProductAttributeMediaListResponseParam = mageProxy.getMageApiModelServerWsiHandlerPort().catalogProductAttributeMediaList(cpamlp)
    CatalogProductImageEntityArray catalogProductImageEntityArray = catalogProductAttributeMediaListResponseParam.result
    catalogProductImageEntityArray.complexObjectArray.each {
      productImageList.add(it.url)
    }
    productImageList
  }


  /**
<<<<<<< HEAD
   * call to get inventory information for set of Products
=======
   * Get a list of product options based on attributeId - this comes from the product list
   *
   * @param sessionId
   * @param attributeId
   * @return List of images
   */
  List getProductOptions(String sessionId, String attributeId) {

	def productOptionList = []
	
	CatalogProductAttributeOptionsRequestParam cpaop = new CatalogProductAttributeOptionsRequestParam()

	cpaop.sessionId = sessionId
	cpaop.store = ""
	cpaop.attributeId = attributeId

	CatalogProductAttributeOptionsResponseParam catalogProductAttributeOptionsResponseParam = mageProxy.getMageApiModelServerWsiHandlerPort().catalogProductAttributeOptions(cpaop)

	CatalogAttributeOptionEntityArray catalogAttributeOptionEntityArray = catalogProductAttributeOptionsResponseParam.result
	
	catalogAttributeOptionEntityArray.complexObjectArray.each {
		def productOptionExpando = new Expando()
		productOptionExpando.label = it.label
		productOptionExpando.value = it.value
		
		productOptionList.add(productOptionExpando)
	}
	productOptionList
  }
  
  /**
   * call to get inventory information for product
>>>>>>> 42b83f2235460099c583d25f5b4d99770776e03c
   *
   * @param sessionId
   * @param productIds
   * @return List of product inventory values
   */
  List getProductStockAttributes(String sessionId, List productIds) {


    def catalogInventoryStockItems = []

    def stockItemListRequestParam = new CatalogInventoryStockItemListRequestParam()
    stockItemListRequestParam.sessionId  = sessionId
    stockItemListRequestParam.productIds = getArrayOfStringFromGroovyList(productIds)

    CatalogInventoryStockItemListResponseParam catalogInventoryStockItemListResponseParam =
      mageProxy.getMageApiModelServerWsiHandlerPort().catalogInventoryStockItemList(stockItemListRequestParam)

    return catalogInventoryStockItemListResponseParam.result.complexObjectArray.toList()
  }


  ArrayOfString getArrayOfStringFromGroovyList(aList) {
    ArrayOfString arrayOfString = new ArrayOfString()

    aList?.each() { aString ->
      arrayOfString.complexObjectArray.add(aString.toString())
    }
    arrayOfString
  }
}
