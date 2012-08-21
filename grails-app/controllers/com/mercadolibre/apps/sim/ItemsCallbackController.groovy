package com.mercadolibre.apps.sim

import grails.converters.JSON


class ItemsCallbackController {

    def index() { 
		log.warn 'This item was now uploaded to the site:'
		log.warn request.JSON
//		
//		itemUpdaterService.consumeMessage(request.JSON))
//	
		def responseMap = ['message': "Items message processed", 'error': "none", 'status': 200, 'cause': []]
		response.status = 200
		render(responseMap as JSON)
	}
}