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
	
	List getCategoryTree(storeUrl, apiUser, apiKey, categoryId) {
	  String sessionId = null
  
	  try {
		if ((sessionId = initMagentoProxyForStore(storeUrl, apiUser, apiKey))) {
		  log.info "Session: ${sessionId}"
  
		  // magento.CatalogCategoryTreeRequestParam
		  
		  //CatalogCategoryTree cat = new CatalogCategoryTree()
		  CatalogCategoryTreeRequestParam cat = new CatalogCategoryTreeRequestParam()
		  cat.sessionId = sessionId
		  
		  // This is the top level category
		  CatalogCategoryTreeResponseParam responseParam = mageProxy.getMageApiModelServerWsiHandlerPort().catalogCategoryTree(cat)
		  println responseParam.result.categoryId
		  
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
	 	
//	  // magento.ArrayOfCatalogCategoryEntities
//	  ArrayOfCatalogCategoryEntities arrayOfCatalogCategoryEntities = responseParam.result.children
//	  List<CatalogCategoryEntity> listOfCatalogCategoryEntities = arrayOfCatalogCategoryEntities.getComplexObjectArray()
//	  listOfCatalogCategoryEntities.each {
//		  it.categoryId
//	  }
		
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
//	List getRelatedProductsForProductId(String sessionId, String sku) {
//  /*
//	  CatalogProductLinkListRequestParam linkListRequestParam = new CatalogProductLinkListRequestParam()
//	  linkListRequestParam.sessionId = sessionId
//	  linkListRequestParam.productId = entityId as String
//	  linkListRequestParam.type = "related"
//	  mageProxy.getMageApiModelServerWsiHandlerPort().catalogProductLinkList(linkListRequestParam).result.complexObjectArray.toList()
//  */
//	  Filters filters = new Filters()
//	  // it is OVERcomplex this time
//	  filters.complexFilter = new ComplexFilterArray()
//	  filters.complexFilter.complexObjectArray.add(new ComplexFilter(key: "sku", value: new AssociativeEntity(key: "like", value: "${sku}%")))
//  
//	  def cplp = new CatalogProductListRequestParam()
//	  cplp.sessionId = sessionId
//	  cplp.filters = filters
//	  cplp.store = ""
//  
//	  mageProxy.getMageApiModelServerWsiHandlerPort().catalogProductList(cplp).result.complexObjectArray.toList()
//  
//	}
//  
//  
//	/**
//	 *
//	 * @param sessionId
//	 * @param sku
//	 * @return
//	 */
//	Map getSimpleProductDetailsForProductId(String sessionId, String sku) {
//	  return [:]
//	}
	

}
