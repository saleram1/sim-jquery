package com.mercadolibre.apps.sim.data.bo.imports

import groovy.transform.ToString

class ItemImportFileSource {	
	String bsfuUUID
	Integer callerId
	String category
	String description
	String digest
	String originalFilename
	String path
	Date dateCreated
	
	static constraints = {
		id(display:false, attributes:[listable:false])
		bsfuUUID(nullable: false, blank: false)
		callerId()
		category(nullable: true, blank: true)
		description(nullable: true, blank: true)
		digest(nullable: true, blank: false)
			///, unique: true)
		originalFilename()
		path()
	}
	
	static mapping = {
		version false
	}
	
	String toString() {
		"${originalFilename} -> ${path}"
	}
}
