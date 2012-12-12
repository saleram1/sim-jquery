package com.mercadolibre.apps.magento

/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 11/26/12
 * Time: 11:59 AM
 * To change this template use File | Settings | File Templates.
 */
class MagentoSOAPPingService extends MagentoSOAPBase {


  Map pingMagentoStore(storeUrl, username, password) {
    withStopwatch("SOAPPingService.pingMagentoStore") {
      def sessionId = null

      try {
        if ((sessionId = initMagentoProxyForStore(storeUrl, username, password))) {
          if (sessionId instanceof MageConnectionDetails)  {
            log.info "Got Session: " +  ((sessionId as MageConnectionDetails).sessionId)
          }
          return [status: "OK", message: "${storeUrl} is alive"]
        }
        else {
          return [status: "ERROR", message: "Cannot reach ${storeUrl}", cause: ""]
        }
      }
      catch (Throwable tr) {
        log.error tr.message
        return [status: "ERROR", message: tr.message, cause: ["Cannot reach ${storeUrl} - Caused by ${tr.message}"]]
      }
    }
  }
}
