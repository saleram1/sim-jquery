package com.mercadolibre.apps.sim.data.bo.core


class Shoppe
implements Serializable {

  //VARS
  String            id
  User	  		    manager
  String            name
  String            number
  String 			webAddress


  //GORM
  static hasMany = [locations: Location]

  static constraints = {
    id(maxSize: 32)
    locations(nullable: true)
    name(maxSize: 100, blank: false, nullable: true)
    number(maxSize: 20, blank: false, nullable: true)
	webAddress(nullable: true, url: true)
  }

  static mapping = {
    cache true
    id generator: "uuid"
  }

  //METHODS
  public String toString() {
    return "Shoppe -> id [${id}], name [${name}], number [${number}], managed by [${manager}]"
  }
}