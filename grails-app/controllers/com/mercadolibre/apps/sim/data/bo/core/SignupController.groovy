package com.mercadolibre.apps.sim.data.bo.core

import org.springframework.validation.FieldError

/**
 *  Handle new Company / User registrations - require basic info about the User in order to
 *  make SIM easier to use
 *
 */
class SignupController {

  def create() {
    [shoppeUserInstance: new NewSignupCommand()]
  }

  def save(NewSignupCommand command) {
    def nextActionMap

    Shoppe aShoppe = new Shoppe(name: params.companyName, webAddress: params.magentoStoreURI, apiKey: params.apiKey, sharedSecret: params.password)
    if (!aShoppe.save(flush: true)) {
      render(view: "create", model: ['shoppeUserInstance': command])
      return
    }
    println aShoppe

    User aUser = newUserFromCommand(command, aShoppe)

    // must test for User to be saved properly - transfer errors back to Command if not
    if (aUser.validate() && command.validate()) {
      aUser.save(flush: true)
      log.info aUser
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
      password = command.password
      locale = Locale.US
      company = aShoppe
    }
    session.company = aUser.company.name  // this also must be done on existing login / auths

    return aUser
  }
}


class NewSignupCommand {
  String apiKey
  String magentoStoreURI
  Integer callerId
  String companyName
  String firstName
  String lastName
  String email
  String password

  static constraints = {
    callerId()
    apiKey(nullable:  true)
    companyName(nullable: false, blank: false)
    email(nullable: false, blank: false)
    firstName(nullable: false, blank: false)
    lastName(nullable: false, blank: false)
    magentoStoreURI(nullable:  true)
    password(nullable: false, blank: false, minSize: 8, maxSize: 100)
  }
}