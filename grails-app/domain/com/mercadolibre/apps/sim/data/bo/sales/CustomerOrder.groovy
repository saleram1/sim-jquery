package com.mercadolibre.apps.sim.data.bo.sales

import com.mercadolibre.apps.sim.data.bo.catalog.CatalogItem
import com.mercadolibre.apps.sim.data.bo.core.*


//CatalogItem  plus  Quantity

// for 

// CustomerDetails

class CustomerOrder {
	
  String id
  CustomerDetails customer
  CatalogItem item
  Integer quantity
	
  static mapping = {
    id generator: "uuid"
  }	
}
