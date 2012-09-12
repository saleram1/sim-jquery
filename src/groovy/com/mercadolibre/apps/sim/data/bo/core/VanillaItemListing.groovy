package com.mercadolibre.apps.sim.data.bo.core

/**
 *  DTO for Item which are listed on the ML Marketplace
 *
 */ 
class VanillaItemListing implements Serializable {
  String gp_id       // SKU or store code
	String category_id
	String buying_mode
  String listing_type_id
	String currency_id
	String title
	String description
	Integer available_quantity
	Double price
	String condition
  String pictureURL
  String pictureURL2
  String pictureURL3
  String pictureURL4
  String pictureURL5
  String pictureURL6

	List<String> getPictures() {
    [pictureURL, pictureURL2, pictureURL3, pictureURL4, pictureURL5, pictureURL6].findAll { it != null }
  }

  void setPictures(List<String> somePictures) {
    // read-only
  }
	
	String getSite_id() {
		return category_id?.substring(0, 3)
	}
	
	void setSite_id(String aSiteId) {
    // read-only
		// this.site_id = 
	}
}