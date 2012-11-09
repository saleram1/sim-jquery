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
 	String productSelection = "All Products"

	static constraints = {
    id(display:false, attributes:[listable:false]) // do not show id anywhere
    buyingMode(nullable:  false, inList: ['auction', 'buy_it_now'])
    listingType(nullable: false, inList: ['free', 'bronze', 'silver', 'gold'])
    meliCategory()
    productSelection(nullable:  false, inList: ['All Products', 'Selected Category Ids'])
    storeCategory(nullable: true, blank: true)
	}

	static mapping = {
    version false
	}

//  static transients = ['listingType', 'storeCategory', 'meliCategory', 'buyingMode', 'currency']

	public int compareTo(Object o) {
		// TODO: change id to fitting order property
		return (id.compareTo(o.id))
	}
}