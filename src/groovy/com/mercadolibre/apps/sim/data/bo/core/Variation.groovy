package com.mercadolibre.apps.sim.data.bo.core


class AttributeCombination implements Serializable {
	String id
	String value_id
	//String value_name
}

/**
 * Class to represent the variations in an Item, which should be called ==> ItemDetail
 * rather than a 'variation' - it contains separate fields for price, quantity, pictures which are 
 * normally in the main Item body 
 */
class Variation implements Serializable {
	List<AttributeCombination> attribute_combinations
	Double available_quantity
	Double sold_quantity
	Double price
	List<String> picture_ids
	
	// these are independent id's *per Category*  "92028", "82059"
	
	Variation(String primaryColorId = "92028", String secondaryColorId = "82059", String sizeId, Integer quantity, BigDecimal aPrice, List pictures) {
		attribute_combinations = []
		attribute_combinations.add(new AttributeCombination(id: "83000", value_id: primaryColorId))
		attribute_combinations.add(new AttributeCombination(id: "73001", value_id: secondaryColorId))
		attribute_combinations.add(new AttributeCombination(id: "73002", value_id: sizeId))
		
		available_quantity = quantity
		sold_quantity = 0
		price = aPrice
		
		picture_ids = []
		picture_ids.addAll(pictures)	// use addAll ( ) from the list
	}
}