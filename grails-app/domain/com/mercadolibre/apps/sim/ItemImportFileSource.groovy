package com.mercadolibre.apps.sim

import groovy.transform.ToString

//import com.lucastex.grails.fileuploader.UFile

@ToString
class ItemImportFileSource {
	String description
	String site
	String category
	File fileAttachment
	Date dateCreated
	
	static constraints = {
		id(display:false, attributes:[listable:false])
		description(nullable: true, blank: false)
		site(nullable: true, blank: false)
		category(nullable: true, blank: false)
	}
}