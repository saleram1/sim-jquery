package com.mercadolibre.apps.sim

import groovy.transform.ToString

class ItemImportFileSource {	
	String bsfuUUID
	Integer callerId
	String category
	String description
	String digest
	String originalFilename
	String path
	Boolean pipelineComplete = false
	Date dateCreated
	
	static constraints = {
		bsfuUUID(nullable: false, blank: false)
		callerId()
		category(nullable: true, blank: true)
		description(nullable: true, blank: true)
		digest(nullable: true, blank: false)
		id(display:false, attributes:[listable:false])
		originalFilename()
		path()
		pipelineComplete()
	}
	
	static mapping = {
		version false
	}
	
	String toString() {
		"${originalFilename} -> ${path}"
	}
}