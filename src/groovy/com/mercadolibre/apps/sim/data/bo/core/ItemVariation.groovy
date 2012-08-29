package com.mercadolibre.apps.sim.data.bo.core

/**
 * Class to represent the variations in an Item, which should be called ==> ItemDetail
 * rather than a 'variation' - it contains separate fields for price, quantity, pictures which are 
 * normally in Item body 
 */
class ItemVariation implements Serializable {
	List<VariationAttribute> attribute_combinations
	Double available_quantity = 0
	Double price = 0
	String seller_custom_field = ""
	List<String> picture_ids
	
	// these are independent codes or id's *per Category*  "92028", "82059"
	
	ItemVariation(String primaryColorId = "92028", String secondaryColorId = "82059", 
		          String size, Integer quantity, BigDecimal aPrice, List pictures) {

		available_quantity = quantity
		price = aPrice

		attribute_combinations = []
		
		// Talle (Size) 34 36 38 
		attribute_combinations.add(new VariationAttribute("83000", primaryColorId))
		attribute_combinations.add(new VariationAttribute("73001", secondaryColorId))
		attribute_combinations.add(new VariationAttribute("73002", null, size))
		
		
/*		attribute_combinations.add(new VariationAttribute("83000", primaryColorId))
		attribute_combinations.add(new VariationAttribute("73001", secondaryColorId))
		attribute_combinations.add(new VariationAttribute("73002", size))
*/
		picture_ids = []
		picture_ids.addAll(pictures)	// use addAll ( ) from the list
	}
}


class VariationAttribute implements Serializable {
	String id
	String value_id
	String value_name
	
	VariationAttribute(String anId, String aValueId) {
		this.id = anId
		this.value_id = aValueId
		this.value_name = ""
	}
	
	VariationAttribute(String anId, String aValueId, String aValueName) {
		this.id = anId
		this.value_id = aValueId
		this.value_name = aValueName
	}
}
