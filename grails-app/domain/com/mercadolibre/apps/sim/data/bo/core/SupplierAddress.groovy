package com.mercadolibre.apps.sim.data.bo.core


class SupplierAddress extends Address 
implements Serializable {

  SupplierAddressType type

  //GORM
  static belongsTo = [supplierDetails: SupplierDetails]

  static mapping = {
    id generator: "uuid"
	tablePerHierarchy false
  }
}