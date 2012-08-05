package com.mercadolibre.apps.sim

class ItemImportController {

	static scaffold = true
	static navigation = ["list", "create", "show"]
	
	def showUploadProgress() {
		def importRec = null
		if (session.transaktions) {
			// Collect all the previous file Uploads and put in one container
			def fileSource = ItemImportFileSource.findByBsfuUUID(session.transaktions as String)
			
			if (fileSource) {
				importRec = ItemImport.findOrCreateWhere('category': fileSource.category, 'description': fileSource.description, 'bsfuUUID': fileSource.bsfuUUID)

				ItemImportFileSource.findAllByBsfuUUID(session.transaktions as String).each() { source -> 
					importRec.addToFiles(source)
				}
				importRec.save(failOnError: true, flush: true)
				// now we tell the world about it!!
				sendJMSMessage("queue.import.notification", ['importTicketId': importRec.id])
			}
		} else {
			redirect(uri: "/uploads/new")
		}
		redirect(action: "show", params: ['id': importRec.id])
	}
}
