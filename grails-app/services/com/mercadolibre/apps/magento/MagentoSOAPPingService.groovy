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
    String sessionId = null

    try {
      if ((sessionId = initMagentoProxyForStore(storeUrl, username, password))) {
        log.info "Got Session: ${sessionId}"
        return [status: "OK", message: "${storeUrl} is alive"]
      }
      else {
        return [status: "ERROR", message: "Cannot reach ${storeUrl}", cause: "${sessionId}"]
      }
    }
    catch (Throwable tr) {
      log.error tr.message
      return [status: "ERROR", message: "Cannot reach ${storeUrl}", cause: ["Caused by ${tr.message}"]]
    }
  }
}
