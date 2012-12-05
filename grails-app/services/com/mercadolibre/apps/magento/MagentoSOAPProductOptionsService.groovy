package com.mercadolibre.apps.magento

import magento.CatalogProductAttributeOptionsRequestParam
import magento.CatalogProductAttributeOptionsResponseParam
import magento.CatalogAttributeOptionEntityArray

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

}
