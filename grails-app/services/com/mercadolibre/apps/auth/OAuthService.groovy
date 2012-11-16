package com.mercadolibre.apps.auth

import com.mercadolibre.apps.auth.OAuthAuthenticator
import org.scribe.model.Response
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
    accessToken
  }
}
