package com.mercadolibre.apps.sim.data.bo.core

import groovy.transform.ToString

/**
 * Class to represent the variations in an Item, which should be called ==> ItemDetail
 * rather than a 'variation' - it contains separate fields for price, quantity, pictures which are 
 * normally in Item body 
 */
@ToString
class ItemVariation implements Serializable {
	List<VariationAttribute> attribute_combinations
	Long available_quantity
  BigDecimal price
	String seller_custom_field
	List<String> picture_ids
	
	// Each variation contains price, size, color, and available quantity
	ItemVariation(String sku, String sizeId, Long quantity, BigDecimal aPrice, List pictures,
                String primaryColorId, String secondaryColorId = "82059")
  {
    seller_custom_field = sku
		available_quantity = quantity
		price = aPrice

		attribute_combinations = []
    attribute_combinations.add(new VariationAttribute("73002", sizeId)) // Talle (Size) cannot be spec'd value_name ==>  34 36 38
    attribute_combinations.add(new VariationAttribute("83000", primaryColorId))
		//attribute_combinations.add(new VariationAttribute("73001", secondaryColorId))

		picture_ids = []

    //FOTTER QA
    if (!pictures.isEmpty()) {
      picture_ids.addAll(
        pictures.collect() { String pic ->
          pic.replaceAll("107.20.159.40", "staging.fotter.net")
        }
      )
    }
    ///FOTTER QA

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