// Place your Spring DSL code here
beans = {
	authService(com.mercadolibre.apps.auth.OAuthService)
	
	'VerifyController'(VerifyController) { bean ->
	  bean.autowire = 'byName'
	  authService = ref(authService)
	}
}
