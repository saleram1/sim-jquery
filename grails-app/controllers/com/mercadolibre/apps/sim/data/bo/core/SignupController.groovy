package com.mercadolibre.apps.sim.data.bo.core

import com.mercadolibre.apps.sim.data.bo.core.Shoppe
import com.mercadolibre.apps.sim.data.bo.core.User

class SignupController {

	def create( ) {
		[shoppeUserInstance: new NewSignupCommand()]
	}

	def save(NewSignupCommand command) {
		User aUser = new User()
		aUser.callerId = session.ml_caller_id 
		aUser.firstName = command.firstName
		aUser.lastName = command.lastName
		aUser.email = command.email
		aUser.password = command.password
		aUser.locale = Locale.US	

		Shoppe aShoppe = new Shoppe(name: command.companyName)
		aShoppe.save(failOnError: true)
		
		if (aUser.save(failOnError: true)) {
			log.warn 'saved...'
		}

		redirect(controller: "itemImportFileSource", action: "create")
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