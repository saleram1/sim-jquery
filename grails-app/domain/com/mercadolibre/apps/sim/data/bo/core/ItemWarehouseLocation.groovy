package com.mercadolibre.apps.sim.data.bo.core

class ItemWarehouseLocation {
 
  //VARS
  String    addressLine1
  String    addressLine2
  String    addressLine3
  String    city
  /**
   * ISO country code
   */
  String    country
  String    email
  String    fax
  String    id

  /**
   * ISO language code
   */
  String    language
  String    name
  String    phone
  String    poBox
  String    poBoxCity
  String    poBoxZipCode
  String    region
  String    regionName
  Long      version
  String    zipCode

  Date dateCreated
  Date lastUpdated


  static constraints = {
	addressLine1(nullable:true, maxSize:35)
	addressLine2(nullable:true, maxSize:35)
	addressLine3(nullable:true, maxSize:35)
	city(nullable:true, maxSize:35)
	country(nullable:true, maxSize:2)
	email(nullable:true, maxSize:241)
	fax(nullable:true, maxSize:31)
	id(maxSize:32)
	language(nullable:true, maxSize:2)
	lastUpdated(nullable:true)
	name(nullable:true, maxSize:35)
	phone(nullable:true, maxSize:16)
	poBoxCity(nullable:true, maxSize:35)
	poBoxZipCode(nullable:true, maxSize:10)
	poBox(nullable:true, maxSize:10)
	region(nullable:true, maxSize:3)
	zipCode(nullable:true, maxSize:10)
  }

}
