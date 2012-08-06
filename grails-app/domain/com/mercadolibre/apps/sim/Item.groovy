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
	String condition = "new"
	String currency = "USD"
	Double price
	String pictureURL
	String pictureURL2
	String pictureURL3
	String pictureURL4
	Integer available_quantity
	String listing_type_id = "silver"
	String format  //  really buying_mode
	Integer duration = 60		//   really start_time plus end_time - @TODO need to support this at item level
	String shipsFrom
	String mercadoLibreItemId
	Boolean acceptsMercadoPago = true
	Date dateCreated
	Date lastUpdated
		
	static transients = ['compositeId','pictureMap',"description",   "condition",   "currency",   "pictureURL",   "pictureURL2",   "pictureURL3",   "pictureURL4",   "listing_type_id",   "format",   "shipsFrom"]
	
	static constraints = {
		id(display:false, attributes:[listable:false]) // do not show id anywhere
		gp_id(nullable: true, blank: true, display:false)
		site(inList: ["MLA", "MLB"], attributes:[listable:false])
		category()
		title(nullable: false, blank: false)
		description(blank: false, nullable: true, attributes:[listable:false], widget: "textarea")
		format(inList: ["buy_it_now","auction"])
		duration(min:1, max:60)
		price()  // establish reasonable min and max 
		currency(blank: false, nullable: false, inList: ["ARS", "BRL", "USD"])
		available_quantity(min:1, max:100000)
		listing_type_id(inList: ["bronze","silver","gold"], attributes:[listable:false])
		condition(blank: false, nullable: false, inList: ["new","used"])
		pictureURL(blank: false, nullable: true, url: true, attributes:[listable:false])
		pictureURL2(blank: false, nullable: true, url: true)
		pictureURL3(blank: false, nullable: true, url: true)
		pictureURL4(blank: false, nullable: true, url: true)
		acceptsMercadoPago(attributes:[listable:false])
		lastUpdated(nullable: true, attributes:[listable:false])
		dateCreated(nullable: true, attributes:[listable:false])
		mercadoLibreItemId(nullable: true)
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
		List props = ['site','format','listing_type_id','currency','title','description','category','available_quantity','price','condition']
		Map  domainAsMap = [:]
		
	    props.each { prop -> 
			if (prop in ['site','category','currency']) {
				domainAsMap["${prop}_id"] = this."$prop"
			}
			else if ('format' == prop) {
				domainAsMap['buying_mode'] = this."$prop"
			}
			else {
		        domainAsMap."$prop" = this."$prop"				
			}
	    }
		//"pictures":[
		//{"source":"http://upload.wikimedia.org/wikipedia/en/a/a7/Original_Paperback_Cover.jpg"},
		//{"source":"http://upload.wikimedia.org/wikipedia/en/2/2c/Harry_Potter_and_the_Philosopher%27s_Stone.jpg"}
			
		if (this.pictureURL) {
			domainAsMap['pictures'] = [['source': this.pictureURL]]
		}
		domainAsMap
	}
	
	String toString() {
		return "Item  -> [${id}] [${gp_id}] [${mercadoLibreItemId}] in category [${category}] - ${title} @ ${price} ${currency}";
		//return (toMap() as JSON).toString()
	}	
}
