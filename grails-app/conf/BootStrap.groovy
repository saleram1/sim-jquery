
import com.mercadolibre.apps.sim.data.bo.core.FashionItemListing
import com.mercadolibre.apps.sim.data.bo.core.ItemVariation
import com.mercadolibre.apps.sim.data.bo.core.VariationAttribute

import grails.converters.JSON

class BootStrap {

    def init = { servletContext ->	

		JSON.registerObjectMarshaller(VariationAttribute, 3) {
			def map = [:]
			map.id = it.id
			map.value_id = it.value_id ?: ""
			map.value_name = it.value_name ?: ""
			return map
		}

		// Deep workaround...
		JSON.registerObjectMarshaller(ItemVariation, 3) {
			def map = [:]
			map.attribute_combinations = it.attribute_combinations as List
            map.attribute_combinations.sort { o1,o2 -> o1.id <=> o2.id }
			map.price = it.price
			map.available_quantity = it.available_quantity
            map.picture_ids = it.picture_ids
			map.seller_custom_field = it.seller_custom_field

			return map
		}

		JSON.registerObjectMarshaller(FashionItemListing, 3) {
			def returnArray = [:]
			returnArray["site_id"] = it.site_id
			returnArray["category_id"] = it.category_id
			returnArray["title"] = it.title
			returnArray["price"] = it.price
			returnArray["currency_id"] = it.currency_id
			returnArray["available_quantity"] = it.available_quantity			
			returnArray["listing_type_id"] = it.listing_type_id
			returnArray["condition"] = it.condition

			returnArray["description"] = it.description
			returnArray["accepts_mercadopago"] = "true"
			returnArray["variations"] = it.variations

			return returnArray
		}

//		JSON.registerObjectMarshaller(new SimpleEnumMarshaller(), 2)
//		JSON.registerObjectMarshaller(Item, 3) { ItemMarshaller.marshall(it) }
    }

    def destroy = {

    }
}
