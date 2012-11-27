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

    log.info command.magentoStoreURI
    log.info command.username

/*
    Token freshAccessToken =
      authService.setAuthorizationCode(command.verifierAuthCode, session.ml_request_token)
*/

    Shoppe aShoppe =
      new Shoppe(name: command.companyName, webAddress: command.magentoStoreURI, apiUser: command.username, apiKey: command.apiKey)

    aShoppe.validate()

    if (aShoppe.hasErrors()) {
      aShoppe.errors.fieldErrors.each() { err ->
        println err
      }
    }
    if (!aShoppe.save(flush: true)) {
      render(view: "create", model: ['shoppeUserInstance': command])
      return
    }

    User aUser = newUserFromCommand(command, aShoppe)

    // must test for User to be saved properly - transfer errors back to Command if not
    if (aUser.validate() && command.validate()) {
      aUser.save(flush: true)

      log.info "Saved new Shoppe: ${aShoppe}"
      log.info "and a new User: ${aUser}"
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

/**
 * Register new Shoppe and user on the M2M app
 *
 */
class NewSignupCommand {
  String apiKey
  String companyName
  String email
  String firstName
  String lastName
  String magentoStoreURI
  String username
}