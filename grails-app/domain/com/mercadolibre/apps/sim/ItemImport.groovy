package com.mercadolibre.apps.sim

import groovy.transform.ToString
import com.mercadolibre.apps.sim.data.bo.core.ItemListing
import com.mercadolibre.apps.sim.data.bo.errors.ApiError

@ToString
class ItemImport implements Comparable {
	String bsfuUUID
	String status = "PENDING"
	String category
	String description
	Integer errorItemsCount = 0
	Integer validItemsCount = 0
	
	static transients = ['compositeId', 'totalItemsProcessed', 'site']

    // Adding ItemListing hasMany
	static hasMany = [files: ItemImportFileSource, errs: ApiError, listings: ItemListing]
	
	static constraints = {
		id(display:false, attributes:[listable:false]) // do not show id anywhere
		bsfuUUID(nullable: false, blank: false, unique: true)
		category(nullable: true, blank: true)
		description(nullable: true, blank: true)
		status(attributes:[listable:false], inList:['PENDING', 'READY', 'SENT_TO_MARKETPLACE', 'HAS_ERRORS', 'COMPLETED'])
	}
	
	static mapping = {
        version false
        autoTimestamp false
	}
	
	public int compareTo(Object o) {
		// TODO: change id to fitting order property
		return (bsfuUUID.compareTo(o.bsfuUUID))
	}

	/** 
	 * TO BE REMOVED - when the category_id field is eliminated
	 */
	String getSite() {
		return category?.substring(0,3)
	}
	
	void setSite(String aSite) {
		// noop
	}
	
	Integer getTotalItemsProcessed() {
		validItemsCount + errorItemsCount
	}
	
	void setTotalItemsProcessed(Integer aTotalItemsProcessed) {
		//
	}
}