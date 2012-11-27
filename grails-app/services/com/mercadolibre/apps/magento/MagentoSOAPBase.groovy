package com.mercadolibre.apps.magento

import magento.LoginParam

/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 11/26/12
 * Time: 10:59 AM
 * To change this template use File | Settings | File Templates.
 */
abstract class MagentoSOAPBase {

  // contains one handle to a Magento store
  magento.MagentoService mageProxy

  // contains Map to many Magento stores - based on store baseUrl up to /index.php
  Map<URL,MageConnectionDetails> sessionStores = [:]

  /**
   * Returns valid session on successful call
   * @param storeURL
   * @return SessionID string
   */
  String initMagentoProxyForStore(String storeURL, String username = "m2m", String password = "2dxkp14lggreh7f5m8ejqmzyskwhzoj0") {
    URL realStoreURL
    try {
      realStoreURL = new URL("${storeURL}/api/v2_soap?wsdl=1")
    }
    finally  {
      log.info "Trying to connect API v2 endpoint: ${storeURL} with user ${username.toLowerCase()}"
    }

    try {
      mageProxy = new magento.MagentoService(realStoreURL)
      mageProxy.getMageApiModelServerWsiHandlerPort().startSession()

      LoginParam lp = new LoginParam()
      lp.username = username
      lp.apiKey = password

      String session =
        mageProxy.getMageApiModelServerWsiHandlerPort().login(lp).result
      saveSessionForFutureUse(mageProxy, session, storeURL)

      return session
    }
    catch (Throwable throwable) {
      throw new RuntimeException(throwable.message, throwable)
      //return throwable.message
    }
  }

  def saveSessionForFutureUse(proxy, String session, String storeUrl) {
    def mcd = new MageConnectionDetails(mageProxy: proxy, sessionId: session)
    sessionStores.put(storeUrl, mcd)
  }
}


final class MageConnectionDetails {
  magento.MagentoService mageProxy = null
  String sessionId
}
