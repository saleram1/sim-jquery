// Place your Spring DSL code here
beans = {
	authService(com.mercadolibre.apps.auth.OAuthService)
	magentoService(com.mercadolibre.apps.magento.MagentoService)
	
	'VerifyController'(VerifyController) { bean ->
	  bean.autowire = 'byName'
	  authService = ref(authService)
	}
	'SignupController'(com.mercadolibre.apps.sim.data.bo.core.SignupController) { bean ->
	  bean.autowire = 'byName'
	  authService = ref(authService)
	  magentoService = ref(magentoService)
	}
	/// and many more !!!
	
}
