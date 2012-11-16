package com.mercadolibre.apps.sim.data.bo.core

import com.mercadolibre.apps.auth.OAuthService
import com.mercadolibre.apps.magento.MagentoService

import org.scribe.model.Token;
import org.springframework.validation.FieldError

/**
 *  Handle new Company / User registrations - require basic info about the User in order to
 *  make SIM easier to use
 *
 */
class SignupController {
	
  OAuthService authService
  MagentoService magentoService

  def create() {
    [shoppeUserInstance: new NewSignupCommand()]
  }

  def save(NewSignupCommand command) {
	def nextActionMap
// compare what's in Session to the previous version
	log.info command.verifierAuthCode
	log.info session.ml_request_token


    Shoppe aShoppe = new Shoppe(name: command.companyName, webAddress: command.magentoStoreURI, apiKey: command.apiKey, sharedSecret: command.sharedSecret, accessToken: "aToken.token", accessTokenSecret: "aToken.secret")
    if (!aShoppe.save(flush: true)) {
      render(view: "create", model: ['shoppeUserInstance': command])
      return
    }
//    println aShoppe

    User aUser = newUserFromCommand(command, aShoppe)

    // must test for User to be saved properly - transfer errors back to Command if not
    if (aUser.validate() && command.validate()) {
      aUser.save(flush: true)
//      log.info aUser
      nextActionMap = [controller: "magentoItemImport", action: "create"]
    }
    else {
      assert aUser.hasErrors() || command.hasErrors()
      aUser.errors.fieldErrors.each() { FieldError anError ->
        command.errors.rejectValue(anError.field, anError.code, anError.defaultMessage)
      }

      flash.message = "Please correct the following errors:"
      render(view: "create", model: ['shoppeUserInstance': command])
      return
    }
    redirect(nextActionMap)
  }

  User newUserFromCommand(NewSignupCommand command, Shoppe aShoppe) {
    User aUser = new User()
    aUser.with {
      callerId = session.ml_caller_id
      firstName = command.firstName
      lastName = command.lastName
      email = command.email
      password = "password123"
      locale = Locale.US
      company = aShoppe
    }
    session.company = aUser.company.name  // this also must be done on existing login / auths

    return aUser
  }
}


class NewSignupCommand {
  String apiKey
  Integer callerId
  String companyName
  String email
  String firstName
  String lastName
  String magentoStoreURI
  String sharedSecret
  String verifierAuthCode
}