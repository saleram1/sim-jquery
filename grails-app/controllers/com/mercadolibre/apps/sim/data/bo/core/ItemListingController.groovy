package com.mercadolibre.apps.sim.data.bo.core

class ItemListingController {

  def itemListingService

  static scaffold = true
  static navigation = ["list"]

  def list() {

  }

  def listForReceipt() {
    [itemListingInstanceList: itemListingService.getItemListing(session.bsfuUUID as String), itemListingInstanceTotal: itemListingService.count() ]
  }

}