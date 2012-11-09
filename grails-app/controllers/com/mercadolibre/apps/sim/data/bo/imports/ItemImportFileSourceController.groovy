package com.mercadolibre.apps.sim.data.bo.imports

import com.mercadolibre.apps.sim.data.bo.core.User
import com.mercadolibre.apps.sim.util.FileHelper

import org.springframework.web.multipart.MultipartHttpServletRequest
import org.springframework.web.multipart.MultipartFile

import grails.converters.JSON
import com.mercadolibre.apps.sim.data.bo.imports.ItemImportFileSource
import com.mercadolibre.apps.sim.UserService

class ItemImportFileSourceController {

  UserService userService


  def authorize(AuthoriseCommand command) {
    def nextAction = [controller: "magentoItemImport", action: "create"]

    // Use MELI.login to test if the client has a valid access_token

    if (command.validate()) {
      session.ml_access_token = command.access_token
      session.ml_caller_id = command.user_id
      session.setMaxInactiveInterval(command.expires_in - 30)
      session.nickname = userService.getNickname(command.user_id)
      log.info "Storing session for User ${session.ml_caller_id} nicknamed ${session.nickname} and token - ${session.ml_access_token}"

      User existingUser = User.findByCallerId(session.ml_caller_id as Integer)
      if (!existingUser) {
        nextAction = [controller: "signup", action: "create"]
      }
      else {
        session.company = existingUser.company.name  // this also must be done on signup controller
      }
    }
    redirect(nextAction)
  }


  def create() {
    def formDataModelMap = ['bsfuUUID': "${System.currentTimeMillis()}"]
    //[category: "Various2", description: "Shoes and Botas", ...]
    render(view: "create", model: [formDataModelMap: formDataModelMap])
  }


  def save() {
    switch (request.method) {
      case 'POST':
        def results = []
        if (request instanceof MultipartHttpServletRequest) {
          for (filename in request.getFileNames()) {
            MultipartFile file = request.getFile(filename)

            // create a directory for the caller - example /tmp/docs/9885
            File callerTemp = new File("/tmp/docs/${session.ml_caller_id}")
            if (!callerTemp.exists()) {
              callerTemp.mkdirs()
            }
            File tempFile = File.createTempFile("items", ".csv", callerTemp);

            // Spring3 is so darned civilized
            file.transferTo(tempFile)

            // compute MD5 and notify client to reject later
            String md5Digest = FileHelper.computeMD5(new FileInputStream(tempFile))

            def newFileSource = new ItemImportFileSource('callerId': session.ml_caller_id, 'path': tempFile.absolutePath, 'originalFilename': file.originalFilename, 'digest': md5Digest, 'bsfuUUID': params.bsfuUUID)  // bsfu Id is the timestamp so file uploads may be grouped together

            newFileSource.category = "Various"
            newFileSource.description = "Zapatos e Botas"
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


/*
	domains: bit.ly
	user_id: 110657543
	expires_in: 10800
	access_token: APP_USR-10751-080622-a0b8b9ab3516e0cfde84b006762962ab-110657543
*/
class AuthoriseCommand {
  String access_token
  String domains
  Integer expires_in
  Integer user_id

  static constraints = {
    access_token(blank: false, nullable: false)
    expires_in(min: 900)
    user_id(min: 1)
  }
}