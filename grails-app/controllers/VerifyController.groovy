
import com.mercadolibre.apps.auth.OAuthService

import org.scribe.model.Token
import com.mercadolibre.apps.magento.MagentoSOAPPingService
import grails.converters.JSON;


/**
 * Takes the user to initialize OAuth flow to the Magento store 
 * if the required params are not present, takes you to the home page
 *
 *
 *  curl -v -k -X GET "http://localhost:8080/sim/verify?apiKey=k6jcg57urgrpx3tdocwehmk7jnytvyfv&username=m2muser&apiBaseURL=http://ec2-107-22-49-30.compute-1.amazonaws.com/magento/index.php/"
 */
class VerifyController {
	
	OAuthService authService
  MagentoSOAPPingService magentoSOAPPingService

	
	def index(VerifyCommand command) {
		String authorizationUrl
    def pingResult

		if (command.validate()) {
      pingResult =
        magentoSOAPPingService.pingMagentoStore(command.apiBaseURL, command.username, command.apiKey)

/*
			Token requestToken = authService.getRequestToken(command.apiKey, command.username, command.apiBaseURL)
		    authorizationUrl = authService.getAuthorizationUrl(requestToken)
			session.ml_request_token = requestToken
			
			log.info session.ml_request_token
		  redirect ([url: authorizationUrl])
*/

      render pingResult as JSON
    }
	}
}


class VerifyCommand implements Serializable {
	String apiBaseURL
	String apiKey
	String username
	
	static constraints = {
		apiBaseURL(nullable: false, blank: false)
		apiKey(nullable: false, blank: false)
		username(nullable: false, blank: false)
	}
}