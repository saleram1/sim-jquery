/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mercadolibre.apps.auth;

import java.util.Scanner;
import org.scribe.builder.MagentoServiceBuilder;
import org.scribe.builder.api.MagentoOauth10a;
import org.scribe.model.Token;
import org.scribe.model.Verifier;

/**
 *
 * @author javageek
 */
public class OAuthAuthenticator {
  
  org.scribe.oauth.OAuthService service;
  Scanner in;
  
  public Token getRequestToken(String magentoApiKey, String magentoApiSecret, String magentoBaseUrlOauth) {
    		
    service = new MagentoServiceBuilder()
				.provider(MagentoOauth10a.class, magentoBaseUrlOauth)
				.apiKey(magentoApiKey)
				.apiSecret(magentoApiSecret)
				.debug()
				.build();
    
    
    // start
		in = new Scanner(System.in);
		System.out.println("Magento's OAuth Workflow");
		System.out.println();
		// Obtain the Request Token
		System.out.println("Fetching the Request Token...");
		Token requestToken = service.getRequestToken();
		System.out.println("Got the Request Token!");
		System.out.println();
    return requestToken;
  }
  
  public String getAuthorizationUrl (Token requestToken) {
    
    // Obtain the Authorization URL
		System.out.println("Fetching the Authorization URL...");
		String authorizationUrl = service.getAuthorizationUrl(requestToken);
    return authorizationUrl; 
  }
  
  public Token setAuthorizationCode(String authorizationCode, Token requestToken) {

		Verifier verifier = new Verifier(authorizationCode);
		System.out.println();
    
         // Trade the Request Token and Verfier for the Access Token
		System.out.println("Trading the Request Token for an Access Token...");
		Token accessToken = service.getAccessToken(requestToken, verifier);
		System.out.println("Got the Access Token!");
		System.out.println("(if your curious it looks like this: "
				+ accessToken + " )");
		System.out.println();
    
    return accessToken;
  }
  
  public Token initializeAuthenticateAuthorize(String magentoApiKey, String magentoApiSecret, String magentoBaseUrlOauth) {
    
    
//    final String MAGENTO_API_KEY = "43adm7jjkff1adm0gxs301oykmvyx8v7";
//		final String MAGENTO_API_SECRET = "gxmkqpd0304csrn19tchcbw11pmz5ay4";
//		final String MAGENTO_REST_API_URL = "http://ec2-23-23-25-84.compute-1.amazonaws.com/magento/api/rest/";
//    
//    final String MAGENTO_BASE_URL_OAUTH = "http://ec2-23-23-25-84.compute-1.amazonaws.com/magento/index.php/";
    
    // three-legged oauth
		org.scribe.oauth.OAuthService service = new MagentoServiceBuilder()
				.provider(MagentoOauth10a.class, magentoBaseUrlOauth)
				.apiKey(magentoApiKey)
				.apiSecret(magentoApiSecret)
				.debug()
				.build();
    
    
    // start
		Scanner in = new Scanner(System.in);
		System.out.println("Magento's OAuth Workflow");
		System.out.println();
		// Obtain the Request Token
		System.out.println("Fetching the Request Token...");
		Token requestToken = service.getRequestToken();
		System.out.println("Got the Request Token!");
		System.out.println();
    
    // Obtain the Authorization URL
		System.out.println("Fetching the Authorization URL...");
		String authorizationUrl = service.getAuthorizationUrl(requestToken);
		System.out.println("Got the Authorization URL!");
		System.out.println("Now go and authorize Main here:");
		System.out.println(authorizationUrl);
		System.out.println("And paste the authorization code here");
		System.out.print(">>");
		Verifier verifier = new Verifier(in.nextLine());
		System.out.println();
    
     // Trade the Request Token and Verfier for the Access Token
		System.out.println("Trading the Request Token for an Access Token...");
		Token accessToken = service.getAccessToken(requestToken, verifier);
		System.out.println("Got the Access Token!");
		System.out.println("(if your curious it looks like this: "
				+ accessToken + " )");
		System.out.println();
    
    return accessToken;
  }
}
