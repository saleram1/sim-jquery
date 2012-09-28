package com.mercadolibre.apps.sim.data.bo.core

class ItemListing {

    def ItemService

    String gp_id    // assigned sku or UPC
    String mercadoLibreItemId    // use this to lookup most of the attributes

    // The following comes from Meli Items API
    String category_id
    String title
    String description
    String condition
    String currency_id
    Double price
    String pictureURL
    Integer available_quantity
    String listing_type_id
    String buying_mode
    String location
    Boolean acceptsMercadoPago = true
    Date dateCreated         // need to fix this so that it can be sent to mercado
    Date lastUpdated                                                              // need to fix this so it can be sent to mercado


    static transients = ["category_id", "title", "description", "condition", "currency_id", "price",  "pictureURL", "available_quantity", "listing_type_id", "buying_mode", "location", "acceptsMercadoPago", "dateCreated", "lastUpdated"]

    static constraints = {
        id(display: false, attributes: [listable: false]) // do not show id anywhere
        gp_id(nullable: true, blank: true, display: false)
        mercadoLibreItemId(nullable: true)
    }

    def afterLoad() {
        // get all transient properties from Mercado Libre items api
        ItemCommand itemCommand = ItemService.getItemCommand(mercadoLibreItemId)
        // I could probably set the properties or params of each to each other, but not sure if it will overwrite the gp_id and mercadoLIbreItemId to null.  I will test later and refactor below when I know better
        category_id = itemCommand.category_id
        title = itemCommand.title
        description = itemCommand.description
        condition = itemCommand.condition
        currency_id = itemCommand.currency_id
        price = itemCommand.price
        pictureURL = itemCommand.pictureURL
        available_quantity = itemCommand.available_quantity
        listing_type_id = itemCommand.listing_type_id
        buying_mode = itemCommand.buying_mode
        //location = itemCommand.location
        acceptsMercadoPago = itemCommand.acceptsMercadoPago
        dateCreated = itemCommand.dateCreated
        lastUpdated = itemCommand.lastUpdated
    }

    static ItemListing getComposite(String compositeId) {
        // change this only, if your domain class has a composite key
        return ItemListing.get(compositeId)
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
        return "ItemListing  -> [${id}] [${gp_id}] [${mercadoLibreItemId}] in category_id [${category_id}] - ${title} @ ${currency_id} ${price} \n";
    }
}