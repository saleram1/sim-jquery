package com.mercadolibre.apps.sim.data.bo.core

/**
 * Created with IntelliJ IDEA.
 * User: javageek
 * Date: 9/27/12
 * Time: 4:56 PM
 * To change this template use File | Settings | File Templates.
 */

class ItemCommand {

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
    Date dateCreated
    Date lastUpdated

    String toString() {
        return "Category_id: " + category_id + " title: " + title + " description: " + description
    }
}
