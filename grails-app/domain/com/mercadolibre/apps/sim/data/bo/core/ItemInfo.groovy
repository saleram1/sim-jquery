package com.mercadolibre.apps.sim.data.bo.core

class ItemInfo {
  // These attributes come from PB

  static belongsTo = [itemListing: ItemListing]

  String countryOfOriginISO

  // Harmonized System Code
  // This is a 6 digit integer represented as a String since it may be necessary to specify the preceding 0 digits if less than 1000
  String hscCode
  Float originalPrice = 0f
  String originalCurrency
  Float landedDutyPrice = 0f
  // Float shippingCosts = 0f
  String shippingType
  Boolean internationalItem = false

  static constraints = {
    countryOfOriginISO(blank: false, nullable: false, inList: ["AR", "BR", "US"])
    hscCode(blank: false, nullable: false)
    originalPrice()
    originalCurrency(blank: false, nullable: false, inList: ["ARS", "BRL", "USD"])
    landedDutyPrice()
    //shippingCosts()
    shippingType(blank: true, nullable: true)            // not determined until checkout time - do we need this here???
    internationalItem()
  }
}
