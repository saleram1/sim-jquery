
import com.mercadolibre.apps.sim.data.bo.core.FashionItemListing
import com.mercadolibre.apps.sim.data.bo.core.ItemVariation
import com.mercadolibre.apps.sim.data.bo.core.VanillaItemListing
import com.mercadolibre.apps.sim.data.bo.core.VariationAttribute
import grails.converters.JSON

class BootStrap {

	def getBaseItemMap(it) {
		def map = [:]
		
		map["site_id"] = it.site_id
		map["category_id"] = it.category_id
		map["title"] = it.title
		map["price"] = it.price
		map["currency_id"] = it.currency_id
		map["available_quantity"] = it.available_quantity			
		map["listing_type_id"] = it.listing_type_id
		map["condition"] = it.condition
		map["description"] = it.description
		map["accepts_mercadopago"] = "true"
		map
	}

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
			if (it.seller_custom_field && it.seller_custom_field.trim().size() > 0)
				map.seller_custom_field = it.seller_custom_field
			return map
		}

		JSON.registerObjectMarshaller(FashionItemListing, 2) {
			def map = getBaseItemMap(it)	
			map["variations"] = it.variations
			return map
		}

		JSON.registerObjectMarshaller(VanillaItemListing, 3) {
			def map = getBaseItemMap(it)	
			map["pictures"] = it.pictures
			return map
		}

// The Advanced requirements will use / register a Marshaller
//		JSON.registerObjectMarshaller(Item, 3) { ItemMarshaller.marshall(it) }
    }

    def destroy = {

    }
}
