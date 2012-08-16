package com.mercadolibre.apps.sim.data.bo.core

import org.springframework.validation.FieldError

import com.mercadolibre.apps.sim.data.bo.core.Shoppe
import com.mercadolibre.apps.sim.data.bo.core.User

class SignupController {

	def create( ) {
		[shoppeUserInstance: new NewSignupCommand()]
	}

	def save(NewSignupCommand command) {
		def nextActionMap
		
		User aUser = new User()
		aUser.callerId = session.ml_caller_id 
		aUser.firstName = command.firstName
		aUser.lastName = command.lastName
		aUser.email = command.email
		aUser.password = command.password
		aUser.locale = Locale.US	

		Shoppe aShoppe = new Shoppe(name: command.companyName)
		aShoppe.save(failOnError: true)
		
		// must test for User to be saved properly - 
		if (aUser.validate()) {
			aUser.save(flush: true)
			log.warn 'saved...'
			log.warn aUser
			nextActionMap = [controller: "itemImportFileSource", action: "create"]		
		}
		else {
			//nextActionMap = [action: "create"]
			
			aUser.errors.fieldErrors.each() { FieldError it -> 
				command.errors.rejectValue(it.field, it.code, it.defaultMessage)
			}
			flash.message = "Please correct the following errors"
			render(view: "create", model: ['shoppeUserInstance': command])
			return 
		}
		redirect(nextActionMap)
	}
}


class NewSignupCommand {
  String  companyName
  String firstName = ""
  String lastName = ""
  String email = ""
  String password = ""

  static constraints = {
	companyName(nullable: false, blank: false)
	firstName(nullable: false, blank: false)
	lastName(nullable: false, blank: false)
	email(nullable: false, blank: false)
	password(nullable: false, blank: false)
  }
}