package com.mercadolibre.apps.sim

import groovy.transform.ToString

//import com.lucastex.grails.fileuploader.UFile

@ToString
class ItemImport {
	String status = "PENDING"
	String description
//	UFile fileAttachment
	Integer errorItemsCount = 0
	Integer validItemsCount = 0
	Date dateCreated
	
	static transients = ['compositeId','totalItemsProcessed']
	
	
	static constraints = {
		id(display:false, attributes:[listable:false])
		status(attributes:[listable:false], inList:['PENDING', 'READY', 'SENT_TO_MARKETPLACE', 'HAS_ERRORS'])
		description(nullable: true)
		validItemsCount()
		errorItemsCount()
	}

	static hasMany = [errs: ApiError]
	
	Integer getTotalItemsProcessed() {
		validItemsCount + errorItemsCount
	}
	
	void setTotalItemsProcessed(Integer aTotalItemsProcessed) {
		//
	}
		
}