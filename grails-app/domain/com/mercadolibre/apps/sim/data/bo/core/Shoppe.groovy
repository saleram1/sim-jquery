package com.mercadolibre.apps.sim.data.bo.core

import groovy.transform.ToString


@ToString
class Shoppe
implements Serializable {

  //VARS
  String id
  User manager
  String name
  String number
  // supplied by Mage Admin
  String apiKey
  String sharedSecret
  String webAddress

  //GORM
  static hasMany = [locations: Location]

  static constraints = {
    id(maxSize: 32)
    apiKey(nullable: true)
    locations(nullable: true)
    manager(nullable: true)
    name(maxSize: 100, blank: false, nullable: false)
    number(maxSize: 20, blank: false, nullable: true)
    sharedSecret(nullable: true)
    webAddress(nullable: true, url: true)
  }

  static mapping = {
    id generator: "uuid"
  }
}