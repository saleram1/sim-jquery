package com.mercadolibre.apps.sim.data.bo.core

/**
 *  DTO for Item which are listed in the new Fashion vertical
 *    @see ItemVariation - for this is the Item 'details' similar to a LineItem of a PO
 *
 */ 
 

///extends VanillaItemListing 
class FashionItemListing implements Serializable {
	/*String site_id*/
	String category_id
	String buying_mode = "buy_it_now"		// auction is possible as well
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
		
	// for some Category types, namely ones of the Fashionista variety
	List<ItemVariation> variations = Collections.emptyList()
}