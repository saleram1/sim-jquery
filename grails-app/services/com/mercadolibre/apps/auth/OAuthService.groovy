package com.mercadolibre.apps.auth

import com.mercadolibre.apps.auth.OAuthAuthenticator
import com.sun.tools.internal.ws.processor.model.Response
import org.scribe.model.OAuthRequest
import com.sun.tools.internal.ws.processor.model.Response
import org.scribe.model.OAuthRequest
import org.scribe.model.Token;

class OAuthService {

  OAuthAuthenticator oAuthAuthenticator
    
  public OAuthService() {
        oAuthAuthenticator = new OAuthAuthenticator()
  }
  
  // Step 1
  Token getRequestToken(String magentoApiKey, String magentoApiSecret, String magentoBaseUrlOauth) {
           
    Token requestToken = oAuthAuthenticator.getRequestToken(magentoApiKey, magentoApiSecret, magentoBaseUrlOauth)
    requestToken
  }
  
  // Step 2
  String getAuthorizationUrl(Token requestToken) {
    String authorizationUrl = oAuthAuthenticator.getAuthorizationUrl(requestToken)
    authorizationUrl
  }

  // Step 3
  Token setAuthorizationCode(String authorizationCode, Token requestToken) {
    
    Token accessToken = oAuthAuthenticator.setAuthorizationCode(authorizationCode, requestToken)
    acessToken
  }
  
  def getProducts(Token accessToken, String magentoRestApiUrl) {
    
    OAuthRequest request = new OAuthRequest(Verb.GET, MAGENTO_REST_API_URL+ "/products?type=rest")
		service.signRequest(accessToken, request)
		Response response = request.send()
    
    println response.getCode()
		println response.getBody()
    println "End GetProducts"
  }
  
  def getProduct() {
    return null
  } 
  
  // More APIs
}
