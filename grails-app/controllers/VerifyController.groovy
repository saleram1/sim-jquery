
import com.mercadolibre.apps.auth.OAuthService
import grails.converters.JSON
import org.scribe.model.Token;


/**
 * Takes the user to initialize OAuth flow to the Magento store 
 * if the required params are not present, takes you to the home page
 *
 *
 *  curl -v -k -X GET "http://localhost:8080/sim/verify?apiKey=k6jcg57urgrpx3tdocwehmk7jnytvyfv&sharedSecret=j7epse81i2nmkv95e2l5ozbg5vb2hvqp&apiBaseURL=http://ec2-107-22-49-30.compute-1.amazonaws.com/magento/index.php/"
 */
class VerifyController {
	
	OAuthService authService
	
	def index(VerifyCommand command) {
		String authorizationUrl

		if (command.validate()) {
			Token requestToken = authService.getRequestToken(command.apiKey, command.sharedSecret, command.apiBaseURL)
		    authorizationUrl = authService.getAuthorizationUrl(requestToken)
			session.ml_request_token = requestToken
			
			log.info session.ml_request_token
			
		}
		log.info "popup will redirect to authorizationUrl: ${authorizationUrl}"

		redirect ([url: authorizationUrl])	    
	}
}


class VerifyCommand implements Serializable {
	String apiBaseURL
	String apiKey
	String sharedSecret
	
	static constraints = {
		apiBaseURL(nullable: false, blank: false)
		apiKey(nullable: false, blank: false)
		sharedSecret(nullable: false, blank: false)		
	}
}