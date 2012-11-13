package com.mercadolibre.apps.sim.data.bo.core

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.ISODateTimeFormat
import org.joda.time.LocalTime

class ItemService {

  ItemCommand getItemCommand(String itemId) {

    ItemCommand itemCommand = new ItemCommand()

    def builder = new HTTPBuilder("https://api.mercadolibre.com")

    try {
      builder.contentType = ContentType.JSON
      builder.get(path: "/items/${itemId}") { resp, json ->
        itemCommand.category_id = json['category_id']
        itemCommand.title = json['title']
        itemCommand.condition = json['condition']
        itemCommand.currency_id = json['currency_id']
        itemCommand.price = json['price']
        itemCommand.available_quantity = json['available_quantity']
        itemCommand.listing_type_id = json['listing_type_id']
        itemCommand.buying_mode = json['buying_mode']
        itemCommand.acceptsMercadoPago = json['accepts_mercadopago']
        itemCommand.status = json['status']

        String dateCreatedString = json['date_created']
        DateTimeFormatter parser = ISODateTimeFormat.dateTime()
        org.joda.time.DateTime dt = parser.parseDateTime(dateCreatedString)
        Date dateCreated = dt.toDate()

        String lastUpdatedString = json['date_created']
        dt = parser.parseDateTime(lastUpdatedString)
        Date lastUpdated = dt.toDate()

        itemCommand.dateCreated = dateCreated
        itemCommand.lastUpdated = lastUpdated

      }
    }
    catch (Exception ex) {
      if (ex?.message == 'Not Found') {
        log.warn "User ${itemId} not found !!"
      }
      else {
        log.error ex.message, ex
      }
    }
    itemCommand
  }

}