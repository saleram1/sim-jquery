package com.mercadolibre.apps.sim.data.bo.imports

/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 11/8/12
 * Time: 2:51 PM
 * To change this template use File | Settings | File Templates.
 */

import groovy.transform.ToString

@ToString
class MagentoItemImport implements Comparable {
  String listingType = 'bronze'
  String storeCategory
  String meliCategory
 	String currency =  "ARS"
  Double  stockPercentage = 50.0d
  String htmlDescription

  static constraints = {
    id(display:false, attributes:[listable:false]) // do not show id anywhere
    htmlDescription(widget: 'textarea', nullable: true, blank: false)
    listingType(nullable: false, inList: ['bronze', 'silver', 'gold'])
    meliCategory()
    stockPercentage(min: 0.0d, max: 100.0d)
    storeCategory(nullable: true, blank: true)
	}

	static mapping = {
    version false
	}


	public int compareTo(Object o) {
		// TODO: change id to fitting order property
		return (id.compareTo(o.id))
	}
}