package com.mercadolibre.apps.magento

import org.scribe.model.OAuthRequest
import org.scribe.model.Response
import org.scribe.model.Token
import org.scribe.model.Verb
@Deprecated
class MagentoService {

  def getProducts(Token accessToken, String magentoRestApiUrl) {
    
    // TODO: Need some dependency injection here.
    OAuthRequest request = new OAuthRequest(Verb.GET, MAGENTO_REST_API_URL+ "/products?type=rest")
		service.signRequest(accessToken, request)
		Response response = request.send()
    
    if (response.getCode() >= 300) {
      throw RuntimeException("Could not get Products from Magento")
    }  // TODO:  Move this into it's own typed runtime exception.
    
    String responseBody = response.getBody()

    // TODO: Instead of returning json probably worth returning this in a list of products?
    // If it's variable, then, I dunno.
    responseBody
  }
  
  def getProduct(Token accessToken, String magentoRestApiUrl, String productId) {

    // TODO: Need some dependency injection here.
    OAuthRequest request = new OAuthRequest(Verb.GET, MAGENTO_REST_API_URL+ "/products/${productId}?type=rest&ilike=rest&ineed=rest&limit=1")
		service.signRequest(accessToken, request)
		Response response = request.send()

    if (response.getCode() >= 300) {
      throw RuntimeException("Could not get Products from Magento")
    }  // TODO:  Move this into it's own typed runtime exception.
    
    String responseBody = response.getBody()

    // TODO: Instead of returning json probably worth returning this as a product representation?
    // If it's variable, then, I dunno.
    responseBody
  } 
  
  def getProductCategory(Token accessToken, String magentoRestApiUrl, String productId) {
      // TODO: Need some dependency injection here.
    OAuthRequest request = new OAuthRequest(Verb.GET, MAGENTO_REST_API_URL+ "/products/${productId}/categories?type=rest")
		service.signRequest(accessToken, request)
		Response response = request.send()
    
    println response.getCode()
    
    if (response.getCode() >= 300) {
      throw RuntimeException("Could not get Products from Magento")
    }  // TODO:  Move this into it's own typed runtime exception.
    
    String responseBody = response.getBody()

    // TODO: Instead of returning json probably worth returning this as a product representation?
    // If it's variable, then, I dunno.
    responseBody
  }
}