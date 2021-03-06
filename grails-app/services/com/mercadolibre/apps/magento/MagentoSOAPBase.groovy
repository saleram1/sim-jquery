package com.mercadolibre.apps.magento

import magento.LoginParam
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;


/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 11/26/12
 * Time: 10:59 AM
 * To change this template use File | Settings | File Templates.
 */
abstract class MagentoSOAPBase {

  // contains Map to many Magento stores - based on store baseUrl up to /index.php
  Map<String,MageConnectionDetails> sessionStores = [:]



  /**
   * Retrieve existing sessionId if there is one - init method will do the same
   *
   * @param storeURL
   * @return   connectionDetails with sessionId as a property
   */
  MageConnectionDetails getMagentoSessionForStore(String storeURL) {
    return sessionStores.get(storeURL)
  }


  /**
   * Returns valid session on successful call
   * @param storeURL
   * @return SessionID string
   */
  MageConnectionDetails initMagentoProxyForStore(String storeURL, String username = "m2m", String password = "2dxkp14lggreh7f5m8ejqmzyskwhzoj0") {
    if (getMagentoSessionForStore(storeURL)) {
      return getMagentoSessionForStore(storeURL)
    }


    // for those who have yet to connect - WE SALUTE YOU!
    URL realStoreURL
    try {
      realStoreURL = new URL("${storeURL}/api/v2_soap?wsdl=1")
    }
    finally  {
      log.info "Trying to connect API v2 endpoint: ${realStoreURL} with user ${username.toLowerCase()}"
    }

    // contains one handle to a Magento store
    magento.MagentoService mageProxy

    try {
      mageProxy = new magento.MagentoService(realStoreURL)
      /// ALWAYS disable message chunking
      Client client = ClientProxy.getClient(mageProxy.getMageApiModelServerWsiHandlerPort());
      if (client != null) {
        HTTPConduit conduit = (HTTPConduit) client.getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();

        /*
           ConnectionTimeout
           ================== Specifies the amount of time, in milliseconds, that the client will attempt to establish
                              a connection before it times out. The default is 30000 (30 seconds).
                              0 specifies that the client will continue to attempt to open a connection indefinitely.
        */
        /*
           ReceiveTimeout
           ==================Specifies the amount of time, in milliseconds, that the client will wait for
                             a response before it times out. The default is 60000.
                             0 specifies that the client will wait indefinitely.
        */
        /*
          Chunking is by default enabled in CXF webservices so we are disabling it.
        */
        policy.setAllowChunking(false);
        policy.setConnectionTimeout(30000);
        policy.setReceiveTimeout(15000);
        conduit.setClient(policy);
        log.info("setAllowChunking: " + policy.allowChunking)
      }

      /*
      HTTPConduit http = (HTTPConduit) client.getConduit();
      HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
      httpClientPolicy.setAllowChunking(false);
      httpClientPolicy.setAutoRedirect(false)
      httpClientPolicy.setReceiveTimeout(60000)
      httpClientPolicy.setMaxRetransmits(0)
      http.setClient(httpClientPolicy);
      */
      mageProxy.getMageApiModelServerWsiHandlerPort().startSession()

      LoginParam lp = new LoginParam()
      lp.username = username
      lp.apiKey = password

      String session =
        mageProxy.getMageApiModelServerWsiHandlerPort().login(lp).result

      log.info "New session ... ${session} for store base ${storeURL}"
      return cacheSessionForFutureUse(session, storeURL, mageProxy)
    }
    catch (Throwable throwable) {
      throw new java.net.ConnectException(throwable.message)
    }
  }

  // takes session and puts in a Map based on every unique store URL
  def cacheSessionForFutureUse(String session, String storeUrl, Object proxy) {
    if (!session || !storeUrl || !proxy) {
      throw new IllegalArgumentException("SessionId Store URL and client proxy are all required params!")
    }

    def mcd = new MageConnectionDetails(mageProxy: proxy, sessionId: session, lastAccessTime: System.currentTimeMillis())
    sessionStores.put(storeUrl, mcd)
    mcd
  }
}


class MageConnectionDetails {
  magento.MagentoService mageProxy
  String sessionId
  Long   lastAccessTime
}
