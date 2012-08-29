package com.mercadolibre.apps.sim

import com.mercadolibre.apps.sim.data.bo.core.FashionItemListing
import com.mercadolibre.apps.sim.data.bo.core.ItemVariation
import grails.converters.JSON

class TestController {
	
	def index() {
		def listing = new FashionItemListing()
		
		listing.variations = []
		listing.variations.add(new ItemVariation("92028", "82059", "82068", 10, 199.95, ["http://farm7.staticflickr.com/6062/6087409376_f360493109_b.jpg"]))
		// index.gsp
		
		render(listing as JSON)
	}
}