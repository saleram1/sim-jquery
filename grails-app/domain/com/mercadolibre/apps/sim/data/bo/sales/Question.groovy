package com.mercadolibre.apps.sim.data.bo.sales

import com.mercadolibre.apps.sim.data.bo.catalog.CatalogItem

class Question {
  String id
  String text
	
  //GORM
  static belongsTo = [item: CatalogItem]
  static mapping = {
    cache true
    id generator: "uuid"
  }	
}
