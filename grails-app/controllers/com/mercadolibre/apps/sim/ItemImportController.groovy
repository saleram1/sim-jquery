package com.mercadolibre.apps.sim


class ItemImportController {

	static scaffold = true
	static allowedMethods = [startUpload: "POST", showUploadProgress: "GET", start: "GET"]
		
	def showUploadProgress() {
		def importRec
		def redirectParams = [:]
		
		if (session.transaktions) {
			// Collect all the previous file Uploads and put in one container
			def fileSource = ItemImportFileSource.findByBsfuUUID(session.transaktions as String)

			if (fileSource) {
				importRec = ItemImport.findOrSaveWhere('category': fileSource.category, 'description': fileSource.description, 'bsfuUUID': fileSource.bsfuUUID)

				if (!importRec.files || importRec.files.size() == 0 ) {
					ItemImportFileSource.findAllByBsfuUUID(session.transaktions as String).each() { source -> 
						importRec.addToFiles(source)
					}
					importRec.save(failOnError: true, flush: true)
				}
				redirectParams = [action: "start", params: ['id': importRec.id]]
			}
		} else {
			redirectParams = [uri: "/uploads/new"]
		}
		redirect(redirectParams)
	}
	
	def headerWithStatus() {
		render(template: "header", model: ['itemImportInstance': ItemImport.get(params?.id)])
	}
	
	def start() {
		// clear the session once copied out...
		session.removeAttribute('transaktions') 
		[itemImportInstance: ItemImport.get(params?.id)]
		 	//ItemImport.findAllByBsfuUUID(session.transaktions as String)]
	}

	def startUpload(StartUploadCommand command) {
		if (command.action == "startUpload") {
			sendQueueJMSMessage("queue.import.notification", ['importTicketId': command.id])
		} // now we tell the world about it!!
		
		render "<strong>Upload started at ${new Date()}</strong>"
	}
}


class StartUploadCommand {
	String action
	Integer id
	Long bsfuUUID
}
