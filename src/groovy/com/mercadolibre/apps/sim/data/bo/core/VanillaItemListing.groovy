package com.mercadolibre.apps.sim.data.bo.core

/**
 *  DTO for Item which are listed in the new Fashion vertical
 *
 */ 
class VanillaItemListing implements Serializable {
	static final String BRONZE = "bronze"
	static final String SILVER = "silver"
	static final String GOLD = "gold"
	
	String site_id
	String category_id
	String buying_mode = "buy_it_now"
	String listing_type_id = 
	String currency_id
	String title
	String description
	Integer available_quantity
	Double price
	String condition
	List<Map> pictures = Collections.emptyList()
}


/*
		// From the guides  
		//   "pictures":[
		//  {"source":"http://upload.wikimedia.org/wikipedia/en/a/a7/Original_Paperback_Cover.jpg"},
		//  {"source":"http://upload.wikimedia.org/wikipedia/en/2/2c/Harry_Potter_and_the_Philosopher%27s_Stone.jpg"}
		if (this.pictureURL) {
			domainAsMap['pictures'] = [['source': this.pictureURL]]
		}
*/