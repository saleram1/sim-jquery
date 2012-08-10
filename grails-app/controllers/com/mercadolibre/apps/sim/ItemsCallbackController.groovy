package com.mercadolibre.apps.sim

import grails.converters.JSON


class ItemsCallbackController {

//	def itemUpdaterService

    def index() { 
		log.warn request.JSON
//		
//		itemUpdaterService.consumeMessage(request.JSON))
//	
		def responseMap = ['message': "Items message processed", 'error': "none", 'status': 200, 'cause': []]
		response.status = 200
		render(responseMap as JSON)
	}
}