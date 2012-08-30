package com.mercadolibre.apps.sim

import com.mercadolibre.apps.sim.data.bo.core.FashionItemListing
import com.mercadolibre.apps.sim.data.bo.core.ItemVariation
import com.mercadolibre.apps.sim.data.bo.core.VanillaItemListing
import grails.converters.JSON

class TestController {
	
	// Examples for the new Vanilla/Fashion Item DTOs
	def index() {		
/*		def listing = new VanillaItemListing(
				category_id: "MLA78884",
				buying_mode: "buy_it_now",
				listing_type_id: "bronze",
				currency_id: "ARS",
				title: "NOW we can be proper 9997 footwear - 2012 - NO ofertar",
				description: "HTML is allowed <em>here in the description</em>",
				available_quantity: 10,
				price: 529.99,
				condition: "new")
			
		// this pictures / source business required for NON-fashion categories
		listing.pictures = []
		listing.pictures.add([source: "http://farm7.staticflickr.com/6062/6087409376_f360493109_b.jpg"])
		listing.pictures.add([source: "http://farm8.staticflickr.com/7250/7485001596_2c200e5200_b.jpg"])
		listing.pictures.add([source: "http://farm9.staticflickr.com/8143/7341534274_d15703d6cd_b.jpg"])
*/

		//
		// Fashion listing
		//	
		def listing = new FashionItemListing(site_id: "MLA",
				category_id: "MLA9997",
				buying_mode: "buy_it_now",
				listing_type_id: "bronze",
				currency_id: "ARS",
				title: "NOW we can be proper 9997 footwear - 2012 - NO ofertar",
				description: "HTML is allowed <em>here in the description</em>",
				available_quantity: 10,
				price: 109.95,
				condition: "new")	
			
		listing.variations = []
		listing.variations.add(new ItemVariation("92028", "82059", "34", 10, 109.95, ["http://farm7.staticflickr.com/6062/6087409376_f360493109_b.jpg"]))
		listing.variations.add(new ItemVariation("92028", "82059", "36", 10, 119.95, ["http://farm8.staticflickr.com/7250/7485001596_2c200e5200_b.jpg"]))
		listing.variations.add(new ItemVariation("92028", "82059", "38", 10, 129.95, ["http://farm9.staticflickr.com/8143/7341534274_d15703d6cd_b.jpg"]))

		render(listing as JSON)
	}
}
