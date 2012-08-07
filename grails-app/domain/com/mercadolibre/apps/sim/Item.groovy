package com.mercadolibre.apps.sim


/**
 * Item contains a mapping between ML Item :id and the Sellers sku or global productId
 * @see ItemImport
 *
 */
class Item implements Comparable {
	String gp_id
	String site
	String category
	String title
	String description
	String condition
	String currency = "ARS"
	Double price
	Double shippingCosts
	String pictureURL
	Integer available_quantity
	String listing_type_id = "silver"
	String format  //  really buying_mode
	Integer duration = 60		//   really start_time plus end_time - @TODO need to support this at item level
	String location
	String shipsFrom
	String mercadoLibreItemId
	Boolean acceptsMercadoPago = true
	Date dateCreated
	Date lastUpdated
	
		
	static transients = ['compositeId','pictureMap',"description",   "condition",   "currency",   "pictureURL",   "pictureURL2",   "pictureURL3",   "pictureURL4",   "listing_type_id",   "format",   "shipsFrom"]
	
	static constraints = {
		id(display:false, attributes:[listable:false]) // do not show id anywhere
		acceptsMercadoPago(attributes:[listable:false])
		available_quantity(min:1, max:100000)
		category()
		condition(blank: false, nullable: false, inList: ["new","used"])
		currency(blank: false, nullable: false, inList: ["ARS", "BRL", "USD"])
		description(blank: false, nullable: true, attributes:[listable:false], widget: "textarea")
		duration(min:1, max:60)
		format(inList: ["buy_it_now","auction"])
		gp_id(nullable: true, blank: true, display:false)
		listing_type_id(inList: ["bronze","silver","gold"], attributes:[listable:false])
		location(nullable: true)
		mercadoLibreItemId(nullable: true)
		pictureURL(blank: false, nullable: true, url: true, attributes:[listable:false])
		price(max: 100000.0d)  // establish reasonable min and max 
		shippingCosts(nullable: true)
		site(inList: ["MLA", "MLB"], attributes:[listable:false])
		title(nullable: false, blank: false)
	}
	
	static Item getComposite(String compositeId) {
		// change this only, if your domain class has a composite key
		return Item.get(compositeId)
	}
	
	public String getCompositeId() {
		// change this only, if your domain class has a composite key
		return this.id
	}
	
	public void deleteAndClearReferences() {
		// and finally do what we really want
		this.delete(flush:true)
	}
	
	public int compareTo(Object o) {
		// TODO: change id to fitting order property
		return (id.compareTo(o.id))
	}

	Map toMap() {
		List props = ['site', 'format', 'listing_type_id', 'currency', 'title', 'description', 'category', 'available_quantity', 'price', 'condition']
		Map  domainAsMap = [:]
		
	    props.eachWithIndex() { aProp, idx -> 
			if (aProp in ['site','category','currency']) {
				domainAsMap["${aProp}_id"] = this."$aProp"
			}
			else if ('format' == aProp) {
				domainAsMap['buying_mode'] = this."$aProp"
			}
			else {
		        domainAsMap."$aProp" = this."$aProp"				
			}
	    }
	
		// From the guides  
		//   "pictures":[
		//  {"source":"http://upload.wikimedia.org/wikipedia/en/a/a7/Original_Paperback_Cover.jpg"},
		//  {"source":"http://upload.wikimedia.org/wikipedia/en/2/2c/Harry_Potter_and_the_Philosopher%27s_Stone.jpg"}
		if (this.pictureURL) {
			domainAsMap['pictures'] = [['source': this.pictureURL]]
		}
		return domainAsMap
	}
	
	String toString() {
		return "Item  -> [${id}] [${gp_id}] [${mercadoLibreItemId}] in category [${category}] - ${title} @ ${currency} ${price} \n";
	}	
}
