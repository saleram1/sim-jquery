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

    Shoppe aShoppe = new Shoppe(name: command.companyName)
    if (!aShoppe.save(flush: true)) {
      render(view: "create", model: ['shoppeUserInstance': command])
      return
    }

    User aUser = newUserFromCommand(command, aShoppe)

    if (aUser.validate()) {
      aUser.save(flush: true)
      log.warn aUser
      nextActionMap = [controller: "itemImportFileSource", action: "create"]
    }
    else {
      // must test for User to be saved properly - transfer errors back to Command if not
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
    with aUser {
      callerId = session.ml_caller_id
      firstName = command.firstName
      lastName = command.lastName
      email = command.email
      password = command.password
      locale = Locale.US
    }
    aUser.company = aShoppe
    aUser
  }
}


class NewSignupCommand {
  Integer callerId
  String companyName
  String firstName
  String lastName
  String email
  String password

  static constraints = {
    callerId()
    companyName(nullable: false, blank: false)
    firstName(nullable: false, blank: false)
    lastName(nullable: false, blank: false)
    email(nullable: false, blank: false)
    password(nullable: false, blank: false, minSize: 6, maxSize: 25)
  }
}