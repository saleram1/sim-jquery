package com.mercadolibre.apps.sim.data.bo.core


/**
 * Represents an Item to be listed for sale in MercadoLibre
 *
 * @author Johanan Lancaon
 *
 */
class ItemListing {

  def ItemService

  // Non-meli attributes that come from PB
  ItemInfo itemInfo

  String gp_id    // assigned sku or UPC
  String mercadoLibreItemId    // use this to lookup most of the attributes

  // The following comes from Meli Items API
  String category_id
  String title
  String description
  String condition
  String currency_id
  String status
  Double price
  String pictureURL
  Integer available_quantity
  String listing_type_id
  String buying_mode
  String location
  Boolean acceptsMercadoPago = true
  Date dateCreated         // need to fix this so that it can be sent to mercado
  Date lastUpdated                                                              // need to fix this so it can be sent to mercado

  static hasMany = [questions: Question]

  static transients = ["category_id", "title", "description", "condition", "currency_id", "price", "pictureURL", "available_quantity", "listing_type_id", "buying_mode", "location", "acceptsMercadoPago", "dateCreated", "lastUpdated", "status"]

  static constraints = {
    itemInfo(nullable: true)
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
    status = itemCommand.status
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

  String toString() {
    return "ItemListing  -> [${id}] [${gp_id}] [${mercadoLibreItemId}] in category_id [${category_id}] - ${title} @ ${currency_id} ${price} \n";
  }
}