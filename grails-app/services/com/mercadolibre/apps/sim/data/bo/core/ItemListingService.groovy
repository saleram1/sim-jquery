package com.mercadolibre.apps.sim.data.bo.core

import com.mercadolibre.apps.sim.ItemImport

class ItemListingService {

  int count = 0

  List getItemListing(String bsfuUUID) {
    //TODO: still need to do pagination
    ItemImport itemImport = ItemImport.findByBsfuUUID(bsfuUUID)

    def itemListings = []
    itemListings = itemImport.listings.asList()

    // set count instance variable
    count = itemListings.size()
    itemListings.each { println it }
    return itemListings
  }

  int count() {
    return count
  }
}
