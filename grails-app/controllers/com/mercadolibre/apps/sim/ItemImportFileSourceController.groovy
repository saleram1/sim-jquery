package com.mercadolibre.apps.sim

import com.mercadolibre.apps.sim.util.FileHelper

import javax.servlet.http.Cookie
import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.MultipartFile

import grails.converters.JSON

class ItemImportFileSourceController {
	
	def authorize() {
		// Use MELI.login to test if the client has a valid access_token
		redirect(action: "create")
	}

	def create() {
		def formDataModelMap = ['category': 'MLA78884', 'description': 'Perfecto', 'access_token': 'APP-USR_123465678123467890', 'bsfuUUID': "${System.currentTimeMillis()}"]
		
		render(view: "create", model: ['formDataModelMap': formDataModelMap])
	}

    def save() {
		switch (request.method) {
		case 'POST':
			def results = []
            if (request instanceof MultipartHttpServletRequest) {
				for(filename in request.getFileNames()) { 
					MultipartFile file = request.getFile(filename)

					// create a directory for the caller - example /tmp/docs/9885
					Integer callerId = 9885  	// TODO bring this back from our JSSDK
					File callerTemp = new File("/tmp/docs/${callerId}")
					File tempFile = File.createTempFile("items", ".csv", callerTemp);
					
					// Spring3 is so darned civilized
					file.transferTo(tempFile)

					// compute MD5 and notify client to reject later
					String md5Digest = FileHelper.computeMD5(new FileInputStream(tempFile))
					
					def newFileSource = new ItemImportFileSource('callerId': callerId, 'path': tempFile.absolutePath, 'originalFilename': file.originalFilename, 'digest': md5Digest, 'category': params.category, 'description': params.description,  'bsfuUUID': params.bsfuUUID)
					
					if (newFileSource.validate()) {
						newFileSource.save(flush: true)
						
	                    results << [
	                            name: file.originalFilename,
	                            size: file.size,
	                            url: null,
	                            thumbnail_url: null
	                    ]
					} else {
						newFileSource.errors.each() {
							log.error it
						}
					}
				}
			}
			
			session.transaktions = params.bsfuUUID
			render results as JSON
		}
    }
}