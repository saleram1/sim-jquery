package com.mercadolibre.apps.sim.data.bo.core

/**
 *  DTO for Item which are listed in the new Fashion vertical
 * @see ItemVariation - for this is the Item 'details' similar to a LineItem of a PO
 *
 */
class FashionItemListing implements Serializable {
  /*
  {
    "title": "Test QA Item per Fashionista 9997 - 2012 - NO ofertar",
    "description",
    "category_id": "MLA9997",
    "currency_id": "ARS",
    "buying_mode": "buy_it_now",
    "listing_type_id": "silver",
    "condition": "new",
   */

  String title
  String description
  String category_id
  String currency_id
  final String buying_mode = "buy_it_now"
  String listing_type_id
  String condition

  String getSite_id() {
    return category_id?.substring(0, 3)
  }

  void setSite_id(String aSiteId) {
    // this.site_id =
  }

//    "variations": [
  List<ItemVariation> variations = []
}