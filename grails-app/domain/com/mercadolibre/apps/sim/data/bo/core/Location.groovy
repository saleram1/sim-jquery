package com.mercadolibre.apps.sim.data.bo.core

/** 
 *  Location meaning where to find this in the store
 *
 */
class Location extends Address {
  String id
  String name

  static mapping = {
    cache true
    id generator: "uuid"
  }	
}
