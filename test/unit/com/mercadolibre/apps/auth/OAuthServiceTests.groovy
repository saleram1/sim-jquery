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
	println authorizationUrl

    assertNotNull authorizationUrl
	assertTrue  "Looks like a URL", authorizationUrl.startsWith("http://")
  }

/*  void testValidAccessToken() {
    Token requestToken = oAuthService.getRequestToken("43adm7jjkff1adm0gxs301oykmvyx8v7", "gxmkqpd0304csrn19tchcbw11pmz5ay4", "http://ec2-23-23-25-84.compute-1.amazonaws.com/magento/index.php/")
    String authorizationUrl = oAuthService.getAuthorizationUrl(requestToken)

	/// NEEDS TO run in browser unless we want to use HtmlUnit to scrape the verifier
    Token accessToken = oAuthService.setAuthorizationCode(authorizationUrl, requestToken)

    println oAuthService.getProducts(token)
  } 
*/
}
