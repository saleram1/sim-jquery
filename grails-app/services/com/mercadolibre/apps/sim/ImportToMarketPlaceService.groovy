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

  def onMessage(aMessage) {
    // lookup ItemImport based on param ==> importTicketId
    ItemImport whatsToImport = ItemImport.get((aMessage['importTicketId'] as Long))

    if (whatsToImport.status == "PENDING" && whatsToImport.validItemsCount == 0) {
      whatsToImport.files?.each() { ItemImportFileSource fileSource ->
        log.info "Processing file ${fileSource.originalFilename}"

        // this method shall return a number of errors , number of Items added
        def arry = importService.importContactsFromCSV(whatsToImport, fileSource, aMessage['accessToken'])
        whatsToImport.validItemsCount += arry[0]
        whatsToImport.errorItemsCount += arry[1] - arry[0]
        whatsToImport.status = "SENT_TO_MARKETPLACE"
        whatsToImport.save()
      }
      whatsToImport.status = "COMPLETED"
      whatsToImport.save(flush: true)
    }
    return
  }
}
