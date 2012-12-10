package com.mercadolibre.apps.magento

import magento.*
import magento.MagentoService

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

  static transactional = false

  //
  // Should return only productId as related to SIMPLE product - such that inventory data can be retrieved
  // getSimpleProductIdsByCategory( storeURL, apiUsername, apiKey, categoryId )
  //
  List<Map> getProductIdsInCategory(String storeUrl, String apiUser, String apiKey, Integer categoryId) {
    MageConnectionDetails mcd = null

    log.info("... searching for product in ${categoryId} using username ${apiUser}")

    try {
      if ((mcd = initMagentoProxyForStore(storeUrl, apiUser, apiKey))) {
        log.info "Session: ${mcd.sessionId}"

        CatalogCategoryAssignedProductsRequestParam cap = new CatalogCategoryAssignedProductsRequestParam()
        cap.sessionId = mcd.sessionId
        cap.categoryId = categoryId
        cap.store = ""

        return getAllProductsAssignedToCategory(mcd,
            categoryId, mcd.mageProxy.getMageApiModelServerWsiHandlerPort().catalogCategoryAssignedProducts(cap))
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
   * Take each of the results under this one category and query for those N+1 api calls incurred
   *
   * @param cap - catagory param wrapper
   * @return List < String >  sku or productId
   */
  List<Map> getAllProductsAssignedToCategory(MageConnectionDetails mcd, Integer categId, CatalogCategoryAssignedProductsResponseParam responseParam) {
    if (responseParam?.result?.complexObjectArray?.isEmpty()) {
      log.warn "No results for ${responseParam} in category ${categId}"
      return Collections.emptyList()
    }

    def productIds = []

    responseParam.result?.complexObjectArray?.each() { CatalogAssignedProduct product ->
      /// Create the map with first sku (six-digit)
      def productMap = new LinkedHashMap<String,List>()

      /// add to the map a new empty list to indicate no children
      productMap.put(product.sku, new Vector<String>())

      def related = getRelatedProductsForProductId(mcd.mageProxy, mcd.sessionId, product.sku)
      related?.each { CatalogProductEntity prod ->
        def childList = productMap.get(product.sku)
        def childType = "${prod.type}"
        if (!childList.contains("${prod.sku}") && childType != 'configurable') {
          childList.add("${prod.sku}")
        }
      }

      if (productMap.get(product.sku).size() != 0 &&
          ((related?.size() - 1) != productMap.get(product.sku).size())) {
        log.warn "related.size = ${related ?: related.size()}   productMap = ${productMap.get(product.sku).size()}"
      }

      productIds.add(productMap)
    }
    productIds
  }


  /**
   * Critical call to retrieve actual SIMPLE Saleable product id's
   *
   * @param sessionId
   * @param sku
   * @return
   */
  List getRelatedProductsForProductId(magento.MagentoService mageProxy, String sessionId, String sku) {
/*
    CatalogProductLinkListRequestParam linkListRequestParam = new CatalogProductLinkListRequestParam()
    linkListRequestParam.sessionId = sessionId
    linkListRequestParam.productId = entityId as String
    linkListRequestParam.type = "related"
    mageProxy.getMageApiModelServerWsiHandlerPort().catalogProductLinkList(linkListRequestParam).result.complexObjectArray.toList()
*/
    Filters filters = new Filters()
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
   *
   * @param sessionId
   * @param productId
   * @return
   */
  Map getProductDetails(magento.MagentoService mageProxy, String sessionId, String productId) {
    def productDetailMap = [:]

    def cpip = new CatalogProductInfoRequestParam()
    cpip.sessionId = sessionId
    cpip.productId = productId  //not SKU - this is the entityId
    // DO NOT TOUCH
    /// in Magento 1.5 and above, if your SKUs are numeric, it now fails to find them.
    ///  Passing in "sku" as the productIdentifierType doesn't seem to help either
    //cpip.identifierType = "sku"
    cpip.store = ""

    cpip.attributes = new CatalogProductRequestAttributes()
    cpip.attributes.additionalAttributes = new ArrayOfString()
    cpip.attributes.additionalAttributes.complexObjectArray.add("color")
    cpip.attributes.additionalAttributes.complexObjectArray.add("marca")
    cpip.attributes.additionalAttributes.complexObjectArray.add("gender")
    cpip.attributes.additionalAttributes.complexObjectArray.add("size")
    cpip.attributes.additionalAttributes.complexObjectArray.add("accesorio_size")
    cpip.attributes.additionalAttributes.complexObjectArray.add("shoe_size")

    CatalogProductReturnEntity catalogProductReturnEntity = mageProxy.getMageApiModelServerWsiHandlerPort().catalogProductInfo(cpip).result

    ArrayOfString categoryArray = catalogProductReturnEntity.categories
    def categories = categoryArray.complexObjectArray

    ArrayOfString websiteArray = catalogProductReturnEntity.websites
    def websites = websiteArray.complexObjectArray

    AssociativeArray aa = catalogProductReturnEntity.additionalAttributes
    def listOfAdditionalAttributeMap = []
    def additionalAttributeMap = [:]
    aa?.complexObjectArray?.each {
      additionalAttributeMap = ["key": it.key, "value": it.value]
      listOfAdditionalAttributeMap.add(additionalAttributeMap)
    }
    def additionalAttributes = listOfAdditionalAttributeMap // just to maintain convention of names from Magento - no other reason

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
        "enableGoogleCheckout": catalogProductReturnEntity.enableGooglecheckout,
        "additionalAttributes": additionalAttributes,
        "pictureURLs": getProductImages(mageProxy, sessionId, productId)
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
  List getProductImages(magento.MagentoService mageProxy, String sessionId, String productId) {
    def cpamlp = new CatalogProductAttributeMediaListRequestParam()
    cpamlp.sessionId = sessionId
    cpamlp.productId = productId  //not SKU - this is the entityId
    cpamlp.store = ""

    CatalogProductAttributeMediaListResponseParam catalogProductAttributeMediaListResponseParam = mageProxy.getMageApiModelServerWsiHandlerPort().catalogProductAttributeMediaList(cpamlp)
    CatalogProductImageEntityArray catalogProductImageEntityArray = catalogProductAttributeMediaListResponseParam.result
    return catalogProductImageEntityArray.complexObjectArray.toList().collect {
      it.url
    }
  }


  /**
   * Get a list of product stock info based on productIds - this is a mini product list
   *
   * @param sessionId
   * @param productIds
   * @return List of product inventory values - one per SKU
   */
  List getProductStockAttributes(magento.MagentoService mageProxy, String sessionId, List productIds) {

    def stockItemListRequestParam = new CatalogInventoryStockItemListRequestParam()
    stockItemListRequestParam.sessionId = sessionId
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