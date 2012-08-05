package com.mercadolibre.apps.sim

/**
 *   ImportToMarketPlaceService
 *
 */

class ImportToMarketPlaceService {
  static transactional = false
  static exposes = ['jms']
  static destination = "queue.import.notification"

  ImportService importService

  def onMessage(aMessage) {
    ItemImport whatsToImport = ItemImport.get((aMessage['importTicketId'] as Long))
    println whatsToImport
	whatsToImport.files?.each() { file -> 
		println file
				
	    // this method shall return a number of errors , number of Items added
	    def arry = importService.importContactsFromCSV(file.path)
	}
	
    return;
  }
}