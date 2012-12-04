package com.mercadolibre.apps.magento

import magento.ArrayOfString
import magento.AssociativeEntity
import magento.CatalogAssignedProduct
import magento.CatalogCategoryAssignedProductsRequestParam
import magento.CatalogCategoryAssignedProductsResponseParam
import magento.CatalogInventoryStockItemListRequestParam
import magento.CatalogInventoryStockItemListResponseParam
import magento.CatalogProductEntity
import magento.CatalogProductInfoRequestParam
import magento.CatalogProductListRequestParam
import magento.CatalogProductReturnEntity
import magento.ComplexFilter
import magento.ComplexFilterArray
import magento.Filters

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
  
  /**
   * Critical call to retrieve actual SIMPLE Saleable product id's
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
	
	productDetailMap = ["productId":catalogProductReturnEntity.productId,
					"sku":catalogProductReturnEntity.sku,
					"set":catalogProductReturnEntity.set,
					"type":catalogProductReturnEntity.type,
					"categories":categories,
					"websites":websites,
					"createdAt":catalogProductReturnEntity.createdAt,
					"updatedAt":catalogProductReturnEntity.updatedAt,
					"typeId":catalogProductReturnEntity.typeId,
					"type":	catalogProductReturnEntity.type,
					"name":catalogProductReturnEntity.name,
					"description":catalogProductReturnEntity.description,
					"shortDescription":catalogProductReturnEntity.shortDescription,
					"weight":catalogProductReturnEntity.weight,
					"status":catalogProductReturnEntity.status,
					"urlKey":catalogProductReturnEntity.urlKey,
					"urlPath":catalogProductReturnEntity.urlPath,
					"visibility":catalogProductReturnEntity.visibility,
					"categoryIds":categoryIds,
					"websiteIds":websiteIds,
					"hasOptions":catalogProductReturnEntity.hasOptions,
					"giftMessageAvailable":catalogProductReturnEntity.giftMessageAvailable,
					"price":catalogProductReturnEntity.price,
					"specialPrice":catalogProductReturnEntity.specialPrice,
					"specialFromDate":catalogProductReturnEntity.specialFromDate,
					"specialToDate":catalogProductReturnEntity.specialToDate,
					"taxClassId":catalogProductReturnEntity.taxClassId,
					"tierPrice":catalogProductReturnEntity.tierPrice,
					"metaTitle":catalogProductReturnEntity.metaTitle,
					"metaKeyword":catalogProductReturnEntity.metaKeyword,
					"metaDescription":catalogProductReturnEntity.metaDescription,
					"customDesign":catalogProductReturnEntity.customDesign,
					"customLayoutUpdate":catalogProductReturnEntity.customLayoutUpdate,
					"optionsContainer":catalogProductReturnEntity.optionsContainer,
					"additionalAttributes":catalogProductReturnEntity.additionalAttributes,
					"enableGoogleCheckout":catalogProductReturnEntity.enableGooglecheckout
  					]
  	productDetailMap
  }
  
  /**
   * Critical call to retrieve actual SIMPLE Saleable product id's
   *
   * @param sessionId
   * @param productId
   * @return
   */
  Map getProductStockAttributes(String sessionId, String productId) {

	def productStrockAttributeMap = [:]
		  
	def cisilp = new CatalogInventoryStockItemListRequestParam()
//	ArrayOfString productIds = new ArrayOfString()
//	productIds.getComplexObjectArray()
	cisilp.productIds = [productId]  // since I don't know how to create an ArrayOfString object I will just try to pass in a single value list
	cisilp.sessionId = sessionId

	CatalogInventoryStockItemListResponseParam catalogInventoryStockItemListResponseParam = mageProxy.getMageApiModelServerWsiHandlerPort().catalogInventoryStockItemList(cisilp)
	
	return null
  }
}
