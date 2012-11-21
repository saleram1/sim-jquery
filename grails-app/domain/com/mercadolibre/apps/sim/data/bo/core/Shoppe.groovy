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
  String apiUser
  String apiKey         // password for the API user
  String webAddress

  // OAuth 1.0a support coming in rev 1.8
  String accessToken
  String accessTokenSecret


  //GORM
  static hasMany = [locations: Location]

  static constraints = {
    id(maxSize: 32)
    apiKey(nullable: false)
    apiUser(nullable: false)
    locations(nullable: true)
    manager(nullable: true)
    name(maxSize: 100, blank: false, nullable: false)
    number(maxSize: 20, blank: false, nullable: true)
    webAddress(nullable: false, url: true)
    accessToken(nullable: true)
    accessTokenSecret(nullable: true)
  }

  static mapping = {
    id generator: "uuid"
  }

  Boolean hasValidToken() {
	return this.accessToken && this.accessTokenSecret
  }

  org.scribe.model.Token getValidToken() {
	return new org.scribe.model.Token(this.accessToken, this.accessTokenSecret)
  }
}