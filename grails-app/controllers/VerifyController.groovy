
import  com.mercadolibre.apps.auth.OAuthService
import grails.converters.JSON
import org.scribe.model.Token;

class VerifyController {
	
	OAuthService authService
	
	def index(VerifyCommand command) {
		String authorizationUrl

		if (command.validate()) {
			Token requestToken = authService.getRequestToken(command.apiKey, command.sharedSecret, command.apiBaseURL)
		    authorizationUrl = authService.getAuthorizationUrl(requestToken)
			session.ml_request_token = requestToken
		}
		log.info "authorizationUrl: ${authorizationUrl}"

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