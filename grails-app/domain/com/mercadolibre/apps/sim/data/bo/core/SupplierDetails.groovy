package com.mercadolibre.apps.sim.data.bo.core


class SupplierDetails
implements Serializable {
  private static final long serialVersionUID = 74321321L

  //VARS
  String                id
  Long                  callerId
  String                name

  /**
   * This is a possibly unpadded supplier ERP ID.
   */
  String                number

  //GORM
  static hasMany = [addresses: SupplierAddress]

  static constraints = {
    addresses(nullable: true)
	callerId(nullable: true)
    id(maxSize: 32)
    name(nullable: true)
    number(maxSize: 10, blank: false, nullable: false)
  }

  static mapping = {
    cache true
    id generator: "uuid"
  }

  //METHODS
  public String toString() {
    return "Supplier Details -> id [${id}], name [${name}], number [${number}]"
  }
}