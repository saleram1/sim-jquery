package com.mercadolibre.apps.sim.data.bo.core


class FashionItemListing implements Serializable {
	String site_id = "MLA"
	String category_id = "MLA9997"
	String buying_mode = "buy_it_now"
	String listing_type_id = "bronze"	// silver, gold
	String currency_id = "ARS"
	String title = "Test QA Item per Fashionista 9997 - 2012 - NO ofertar"
	String description = "HTML is allowed <em>here in the description</em>"
	Integer available_quantity = 10
	Double price = 529.99
	String condition = "new"
	List<Map> pictures = Collections.emptyList()

	// for some Category types, namely fashion
	List<Map> variations
}