package com.mercadolibre.apps.sim

class ItemListing {

    String mlId
    String title
    Integer quantity     // number of items listed.
    // Integer numFailedListing   // number of items that were not  - we can do this at a later time

    static hasMany = [ items:Item ]

    //METHODS
    public String toString() {
        return "ItemList Receipt -> id [${id}]"
    }
}
