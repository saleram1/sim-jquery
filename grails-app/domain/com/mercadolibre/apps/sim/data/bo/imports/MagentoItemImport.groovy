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
 	String buyingMode ='buy_it_now'
 	String currency =  "ARS"
 	String productSelection = 'Products in Selected Categories'
  Double  stockPercentage = 50.0d
  String htmlDescription

  static constraints = {
    id(display:false, attributes:[listable:false]) // do not show id anywhere
    buyingMode(nullable:  false, inList: ['buy_it_now'])
    htmlDescription(widget: 'textarea', nullable: true, blank: false)
    listingType(nullable: false, inList: ['bronze', 'silver', 'gold'])
    meliCategory()
    productSelection(nullable:  false, inList: ['Products in Selected Categories'])
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