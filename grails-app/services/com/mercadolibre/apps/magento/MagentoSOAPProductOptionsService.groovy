package com.mercadolibre.apps.magento

import magento.CatalogAttributeOptionEntityArray
import magento.CatalogProductAttributeOptionsRequestParam
import magento.CatalogProductAttributeOptionsResponseParam
import magento.CatalogProductCustomOptionListArray
import magento.CatalogProductCustomOptionListRequestParam
import magento.CatalogProductCustomOptionListResponseParam
import magento.CatalogProductCustomOptionTypesArray
import magento.CatalogProductCustomOptionTypesRequestParam
import magento.CatalogProductCustomOptionTypesResponseParam

/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 12/4/12
 * Time: 5:02 PM
 * To change this template use File | Settings | File Templates.
 */
class MagentoSOAPProductOptionsService extends MagentoSOAPBase {

  static transactional = false

  /**
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
   * Get a list of product options types based on sessionId
   *
   * @param sessionId
   * @return List of Product Custom Option Types (expando object containing the various attributes)
   */ 
  List getProductCustomOptionTypes(String sessionId) {
	  
	def customProductOptionTypeList = []
  
	CatalogProductCustomOptionTypesRequestParam cpcotp = new CatalogProductCustomOptionTypesRequestParam()
	cpcotp.sessionId = sessionId
	  
	CatalogProductCustomOptionTypesResponseParam catalogProductCustomOptionTypesResponseParam = mageProxy.getMageApiModelServerWsiHandlerPort().catalogProductCustomOptionTypes(cpcotp)
	
	CatalogProductCustomOptionTypesArray catalogProductCustomOptionTypesArray = catalogProductCustomOptionTypesResponseParam.result
	  
	catalogProductCustomOptionTypesArray.complexObjectArray.each {
	  def productCustomOptionExpando = new Expando()
	  productCustomOptionExpando.label = it.label
	  productCustomOptionExpando.value = it.value
	  
	  customProductOptionTypeList.add(productCustomOptionExpando)
	}
	customProductOptionTypeList
  }
  
  /**
   * Get a list of product options list based on sessionId
   *
   * @param sessionId
   * @param productId
   * @return List of Product Custom Option List (expando object containing the various attributes)
   */
  List getProductCustomOptionList(String sessionId, String productId) {
	  
	def customProductOptionList = []
  
	CatalogProductCustomOptionListRequestParam cpcolp = new CatalogProductCustomOptionListRequestParam()
	cpcolp.sessionId = sessionId
	cpcolp.productId = productId
	cpcolp.store = ""
	
	CatalogProductCustomOptionListResponseParam catalogProductCustomOptionListResponseParam = mageProxy.getMageApiModelServerWsiHandlerPort().catalogProductCustomOptionList(cpcolp)
	
	CatalogProductCustomOptionListArray catalogProductCustomOptionListArray = catalogProductCustomOptionListResponseParam.result
	
	catalogProductCustomOptionListArray.complexObjectArray.each {
	  def productCustomOptionListExpando = new Expando()
	  productCustomOptionListExpando.optionId = it.optionId
	  productCustomOptionListExpando.title = it.title
	  productCustomOptionListExpando.type = it.type
	  productCustomOptionListExpando.sortOrder = it.sortOrder
	  
	  customProductOptionList.add(productCustomOptionListExpando)
	}
	customProductOptionList
  }
  
  /**
   * Get a list of product options values based on sessionId
   *
   * @param sessionId
   * @param productId
   * @return List of Product Custom Option Value (expando object containing the various attributes)
   */
  List getProductCustomOptionValue(String sessionId, String productId) {
	  
	def customProductOptionList = []
  
	CatalogProductCustomOptionListRequestParam cpcolp = new CatalogProductCustomOptionListRequestParam()
	cpcolp.sessionId = sessionId
	cpcolp.productId = productId
	cpcolp.store = ""
	
	CatalogProductCustomOptionListResponseParam catalogProductCustomOptionListResponseParam = mageProxy.getMageApiModelServerWsiHandlerPort().catalogProductCustomOptionList(cpcolp)
	
	CatalogProductCustomOptionListArray catalogProductCustomOptionListArray = catalogProductCustomOptionListResponseParam.result
	
	catalogProductCustomOptionListArray.complexObjectArray.each {
	  def productCustomOptionListExpando = new Expando()
	  productCustomOptionListExpando.optionId = it.optionId
	  productCustomOptionListExpando.title = it.title
	  productCustomOptionListExpando.type = it.type
	  productCustomOptionListExpando.sortOrder = it.sortOrder
	  
	  customProductOptionList.add(productCustomOptionListExpando)
	}
	customProductOptionList
  }
}
