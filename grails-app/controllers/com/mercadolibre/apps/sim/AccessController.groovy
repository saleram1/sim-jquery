package com.mercadolibre.apps.sim


class AccessController {
	
	def index(String extraBits) {
		println "extraBits: " + extraBits
		println "URI: " + request.getRequestURI()
		println ''
		
		params.each() { param ->
			if (param.key == 'access_token') {
				log.info "User: " + params.user_id
				log.info "Access Token: " + params.access_token
				session.ml_access_token = param.value
				redirect(uri: "/uploads/new")
			}
		}
		
	}
}