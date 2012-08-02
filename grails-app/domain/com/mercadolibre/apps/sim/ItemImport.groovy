package com.mercadolibre.apps.sim

//import com.lucastex.grails.fileuploader.UFile

class ItemImport implements Comparable {
	String status = "PENDING"
	String description
	String errorMessages
	File fileAttachment
	Integer errorItemsCount = 0
	Integer validItemsCount = 0
	Boolean itemsReady = true
	Date dateCreated
	
	static transients = ['compositeId','totalItemsProcessed','status']
	
	static hasMany = [errs: ApiError]
	
	static constraints = {
		id(display:false, attributes:[listable:false]) // do not show id anywhere
		fileAttachment(nullable: false)
		description(nullable: true)
		validItemsCount()
		errorItemsCount()
		errorMessages(nullable: true)
		status(attributes:[listable:false], inList:['PENDING', 'READY', 'SENT_TO_MARKETPLACE', 'HAS_ERRORS'])
	}
	
	static ItemImport getComposite(String compositeId) {
		// change this only, if your domain class has a composite key
		return ItemImport.get(compositeId)
	}
	
	public String getCompositeId() {
		// change this only, if your domain class has a composite key
		return this.id
	}
	
	public void deleteAndClearReferences() {
		// and finally do what we really want
		this.delete(flush:true)
	}
	
	public int compareTo(Object o) {
		// TODO: change id to fitting order property
		return (id.compareTo(o.id))
	}
	
	Integer getTotalItemsProcessed() {
		validItemsCount + errorItemsCount
	}
	
	void setTotalItemsProcessed(Integer aTotalItemsProcessed) {
		//
	}
	
	String getStatus() {
		if (!itemsReady) {
			"PENDING"
		}
		else {
			"READY"
		}
	}
	
	void setStatus(String status) {
		if (status == 'READY') {
			itemsReady = true
		}
	}
		
	String toString() {
		return "ItemImport  -> [${id}] [${status}]";
	}	
}