package com.mercadolibre.apps.sim.data.bo.core

class ItemListingController {

  def itemListingService

  def scaffold = true
  static navigation = ["list"]


  def listForReceipt() {
    [itemListingInstanceList: itemListingService.getItemListing(session.bsfuUUID as String), itemListingInstanceTotal: itemListingService.count() ]
  }

}