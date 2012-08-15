package com.mercadolibre.apps.sim.data.bo.catalog

/** 
 *  Entry in the virtual catalog table - example from upcdatabase
 *      {"valid":"true","number":"041333424019","itemname":"","description":"Duracell AAA Battery","price":"0.00","ratingsup":"0","ratingsdown":"0"}
 *
 */
class CatalogProduct {
	String description
	String domainId
	String id				// asin, gp_id, meli_id @TODO
	String imageURL
	Boolean showOnSearch
	String status
	BigDecimal suggestedPrice
	
/*
	"categories": - [
    "MLB9637",
    "MLB9614",
  ],

  "skus": - [
    "UPC:00027242641426",
    "MPN:VPL-DS100",
    "UPC:04901780873692",
    "UPC:00002724264140",
  ]*/

  static hasMany = [categories: String, skus: String]

  static mapping = {
    id generator: "uuid"
  }	
}
