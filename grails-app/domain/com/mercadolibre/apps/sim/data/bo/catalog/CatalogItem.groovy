package com.mercadolibre.apps.sim.data.bo.catalog

import com.mercadolibre.apps.sim.data.bo.core.Location
import java.awt.Dimension

/**
 *  CatalogItem implies a base item - divorced from its ItemListing
 *  ClearPath inspired the dimensions field
 *
 *  @see https://wiki.ecommerce.pb.com/display/TECH2/ClearPath+API+-+addCommodity
 */
class CatalogItem implements Comparable {
	String id
	String sku
	String category
	String title
	String description
	String currency
  BigDecimal price
  BigDecimal shippingCosts
	String pictureURL
	Integer available_quantity
	Location location
	String shipsFrom
	String mercadoLibreItemId
	Boolean acceptsMercadoPago = true
  PackageDimension dimension
  BigDecimal weight
  String weightUOM

	Date dateCreated
	Date lastUpdated

	static constraints = {
    acceptsMercadoPago()
    available_quantity(min:1, max:100000)
    category()
    currency(blank: false, nullable: false, inList: ["ARS", "BRL", "USD"])
    description(blank: false, nullable: true, widget: "textarea")
    dimension(nullable:  true)
    id(display:false, attributes:[listable:false]) // do not show id anywhere
    location(nullable: false)
    mercadoLibreItemId(nullable: true)
    pictureURL(blank: false, nullable: true, url: true, attributes:[listable:false])
    price()  // establish reasonable min and max
    shippingCosts(nullable: false)
    sku(nullable: true, blank: true, display:false)
    title(nullable: false, blank: false)
    weightUOM(nullable: true, blank: false)
    weight(nullable: true)
	}


	int compareTo(Object o) {
		// TODO: change id to fitting order property
		return (id.compareTo(o.id))
	}


	String toString() {
    return "CatalogItem  -> [${id}] [${mercadoLibreItemId}] in category_id [${category}] - ${title} @ ${currency} ${price} \n";
	}	
}
