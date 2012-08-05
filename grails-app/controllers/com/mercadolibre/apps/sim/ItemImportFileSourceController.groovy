package com.mercadolibre.apps.sim

import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.MultipartFile

import grails.converters.JSON

class ItemImportFileSourceController {

	static scaffold = ItemImportFileSource

    def save() {
		switch (request.method) {
		case 'POST':
			params.each() { param ->
				println param
			}
			
			def results = []
            if (request instanceof MultipartHttpServletRequest) {
				for(filename in request.getFileNames()) { 
					MultipartFile file = request.getFile(filename)
					println 'Got a file from client: ' + file.originalFilename
				}
				///println 'Got a file' + request.getFileNames()
			}
            render results as JSON
		}
    }
}
