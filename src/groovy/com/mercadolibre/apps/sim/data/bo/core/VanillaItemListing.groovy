package com.mercadolibre.apps.sim.data.bo.core

/**
 *  DTO for Item which are listed on the ML Marketplace
 *
 */ 
class VanillaItemListing implements Serializable {
	/*String site_id*/
	String category_id
	String buying_mode = "buy_it_now"		// auction is permissable as well
	String listing_type_id = "silver"
	String currency_id = "USD"
	String title
	String description
	Integer available_quantity
	Double price
	String condition
	List<Map> pictures = Collections.emptyList()
	
	String getSite_id() {
		return category_id?.substring(0, 3)
	}
	
	void setSite_id(String aSiteId) {
		// this.site_id = 
	}
}