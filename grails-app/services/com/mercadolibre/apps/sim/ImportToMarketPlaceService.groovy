package com.mercadolibre.apps.sim

/**
 *   ImportToMarketPlaceService
 *
 */

class ImportToMarketPlaceService {
  static transactional = true
  static exposes = ['jms']
  static destination = "queue.import.notification"

  ImportService importService

  // lookup ItemImport based on param ==> importTicketId
  def onMessage(aMessage) {
    ItemImport whatsToImport = ItemImport.get((aMessage['importTicketId'] as Long))

	if (whatsToImport.status == "PENDING") {
		whatsToImport.files?.each() { file -> 
			println "Processing file ${file}"
				
		    // this method shall return a number of errors , number of Items added
		    def arry = importService.importContactsFromCSV(file.path)
			whatsToImport.validItemsCount += arry[0]
			whatsToImport.errorItemsCount += arry[1] - arry[0]
			whatsToImport.status = "SENT_TO_MARKETPLACE"
			whatsToImport.save()
		}
		whatsToImport.status = 'COMPLETED'
		whatsToImport.save(flush: true)
	}
    return
  }
}
