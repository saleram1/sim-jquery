package com.mercadolibre.apps.auth



import grails.test.mixin.*
import org.junit.*
import org.scribe.model.Token;

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(OAuthService)
class OAuthServiceTests {

  def oAuthService

  @Before
  void setUp() {
    oAuthService = new OAuthService()
  }
  
  void testAuthGetAuthorizationUrl() {
    Token requestToken = oAuthService.getRequestToken("43adm7jjkff1adm0gxs301oykmvyx8v7", "gxmkqpd0304csrn19tchcbw11pmz5ay4", "http://ec2-23-23-25-84.compute-1.amazonaws.com/magento/index.php/")
    String authorizationUrl = oAuthService.getAuthorizationUrl(requestToken)
    
    println "*************************  this is hte AuthorizationUrl ***************"
    println authorizationUrl
//    Token accessToken = oAuthService.setAuthorizationCode(authorizationUrl, requestToken)
    
    
//    oAuthService.getProducts(token)
  }
  
    
  
}
