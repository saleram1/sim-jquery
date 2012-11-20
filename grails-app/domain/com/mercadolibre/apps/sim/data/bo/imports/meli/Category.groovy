package com.mercadolibre.apps.sim.data.bo.imports.meli


/**
 * Represents a category on the ML Marketplace
 * freshened daily from Quartz job --
 *
 */
class Category {
  
  String name
  String meliId
    
  static constraints = {
     name(nullable: false)
     meliId(nullable: false)
  }
  
  String toString() {
    return "Category Name: " + name + " - " + "Meli Id: " + meliId
  }
}
