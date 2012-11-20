package com.mercadolibre.apps.sim.data.bo.imports.meli

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
