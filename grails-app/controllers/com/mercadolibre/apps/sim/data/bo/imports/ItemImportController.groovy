package com.mercadolibre.apps.sim.data.bo.imports

import com.mercadolibre.apps.sim.data.bo.imports.ItemImport
import com.mercadolibre.apps.sim.data.bo.imports.ItemImportFileSource


class ItemImportController {

	static scaffold = true
	static allowedMethods = [startUpload: "POST", showUploadProgress: "GET", start: "GET"]
		
	def showUploadProgress() {
		def importRec
		def redirectParams = [uri: "/uploads/new"]
		
		if (session.ml_access_token && session.transaktions) {
			// Collect all the previous file Uploads and put in one container
			def fileSource = ItemImportFileSource.findByBsfuUUID(session.transaktions as String)

			if (fileSource) {
				importRec = ItemImport.findOrSaveWhere('category': fileSource.category, 'description': fileSource.description, 'bsfuUUID': fileSource.bsfuUUID)

				if (!importRec.files || importRec.files.size() == 0 ) {
					ItemImportFileSource.findAllByBsfuUUID(session.transaktions as String).each() { source -> 
						importRec.addToFiles(source)
					}
					importRec.save(failOnError: true)
				}
				redirectParams = [action: "start", params: ['id': importRec.id]]
			}
		}
		redirect(redirectParams)
	}
	
	def start() {
		// clear the session once copied out...
		session.removeAttribute('transaktions') 
		[itemImportInstance: ItemImport.get(params?.id)]
		 	//ItemImport.findAllByBsfuUUID(session.transaktions as String)]
	}

	def startUpload(StartUploadCommand command) {
		if (command.action == "startUpload") {
			// now we tell the world about it!!
			sendQueueJMSMessage("queue.import.notification", ['importTicketId': command.id, 'accessToken': session.ml_access_token])
            // save bsfuUUID to session in order to look up the latest upload
            session.bsfuUUID = command.bsfuUUID
		}
		render "<strong>Upload started at ${new Date()}</strong>"
	}
}



/*
	Each file in the batch will contain same bsfu ID or bootStrapFileUpload ID
*/

class StartUploadCommand {
	String action
	Integer id
	Long bsfuUUID
}
