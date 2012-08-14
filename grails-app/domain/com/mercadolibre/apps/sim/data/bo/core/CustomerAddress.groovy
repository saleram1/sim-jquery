package com.mercadolibre.apps.sim.data.bo.core


class CustomerAddress extends Address 
implements Serializable {

  //GORM
  static belongsTo = [customerDetails: CustomerDetails]

  static mapping = {
    id generator: "uuid"
	tablePerHierarchy false
  }
}