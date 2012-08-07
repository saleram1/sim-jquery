modules = {
    application {
        resource url:'js/application.js'
		resource url:'/js/parseuri.js'
    }

    chico {
        resource url:'/js/mercadolibre-1.0.1.js'
    }

	parseuri {
		resource url:'/js/parseuri.js', disposition:'head'
	}
}