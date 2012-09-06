package com.mercadolibre.apps.sim

/**
 * Item contains a mapping between ML Item :id and the Sellers sku or global productId
 * @see ItemImport
 *
 */
class Item implements Comparable {
  String gp_id    // assigned sku or UPC
  String category_id
  String title
  String description
  String condition
  String currency_id
  Double price
  String pictureURL
  Integer available_quantity
  String listing_type_id = "bronze"  // unless Seller overrides
  String buying_mode
  String location
  String mercadoLibreItemId
  Boolean acceptsMercadoPago = true
  Date dateCreated
  Date lastUpdated


  static transients = ['compositeId', "description", "condition", "currency_id", "pictureURL", "listing_type_id", "buying_mode"]

  static constraints = {
    id(display: false, attributes: [listable: false]) // do not show id anywhere
    acceptsMercadoPago(attributes: [listable: false])
    available_quantity(min: 1, max: 100000)
    category_id()
    condition(blank: false, nullable: false, inList: ["new", "used"])
    currency_id(blank: false, nullable: false, inList: ["ARS", "BRL", "USD"])
    description(blank: false, nullable: true, attributes: [listable: false], widget: "textarea")
    buying_mode(inList: ["buy_it_now", "auction"])
    gp_id(nullable: true, blank: true, display: false)
    listing_type_id(inList: ["bronze", "silver", "gold"], attributes: [listable: false])
    location(nullable: true)
    mercadoLibreItemId(nullable: true)
    pictureURL(blank: false, nullable: true, url: true, attributes: [listable: false])
    price(max: 100000.0d)  // establish reasonable min and max
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
    this.delete(flush: true)
  }

  public int compareTo(Object o) {
    // TODO: change id to fitting order property
    return (id.compareTo(o.id))
  }

  Map toMap() {
    List props = ['buying_mode', 'listing_type_id', 'currency_id', 'title', 'description', 'category_id', 'available_quantity', 'price', 'condition']
    Map domainAsMap = [:]

    props.eachWithIndex() { aProp, idx ->
      domainAsMap."$aProp" = this."$aProp"
    }

    //Site is based on the Category
    if (this.category_id) {
      domainAsMap['site_id'] = this.category_id.substring(0, 3)
    }

    // From the guides
    //   "pictures":[
    //  {"source":"http://upload.wikimedia.org/wikipedia/en/a/a7/Original_Paperback_Cover.jpg"},
    //  {"source":"http://upload.wikimedia.org/wikipedia/en/2/2c/Harry_Potter_and_the_Philosopher%27s_Stone.jpg"}
    if (this.pictureURL) {
      domainAsMap['pictures'] = [['source': this.pictureURL]]
    }

    domainAsMap
  }

  String toString() {
    return "Item  -> [${id}] [${gp_id}] [${mercadoLibreItemId}] in category_id [${category_id}] - ${title} @ ${currency_id} ${price} \n";
  }
}
