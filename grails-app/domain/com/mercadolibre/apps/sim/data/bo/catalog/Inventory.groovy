package com.mercadolibre.apps.sim.data.bo.catalog

import com.mercadolibre.apps.sim.data.bo.core.Location


/** 
 *  Entry in the virtual catalog table - example from upcdatabase
 *      {"valid":"true","number":"041333424019","itemname":"","description":"Duracell AAA Battery","price":"0.00","ratingsup":"0","ratingsdown":"0"}
 *
 */
class Inventory implements Serializable {
	String id		
	CatalogProduct product
	Location location
	Integer quantity
	
  static mapping = {
    id generator: "uuid"
  }	
}
