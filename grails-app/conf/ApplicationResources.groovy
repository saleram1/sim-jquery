modules = {
    application {
        resource url:'js/application.js'
    }

    chico {
        resource url:'/js/mercadolibre-1.0.2.js', disposition:'head'
    }

	parseuri {
		resource url:'/js/parseuri.js', disposition:'head'
	}
}