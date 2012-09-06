package com.mercadolibre.apps.sim.data.bo.core

/**
 *  DTO for Item which are listed in the new Fashion vertical
 * @see ItemVariation - for this is the Item 'details' similar to a LineItem of a PO
 *
 */
class FashionItemListing implements Serializable {
  String gp_id       // SKU
  String category_id
  String buying_mode
  String listing_type_id = "bronze"
  String currency_id
  String title
  String description
  Integer available_quantity
  Double price
  String condition

  String getSite_id() {
    return category_id?.substring(0, 3)
  }

  void setSite_id(String aSiteId) {
    // this.site_id =
  }

  // for some Category types, namely ones of the Fashionista variety
  List<ItemVariation> variations = Collections.emptyList()
}