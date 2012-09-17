package com.mercadolibre.apps.sim

class UserTagLib {

    def userService

    def nickNameAndCompany = { attrs, body ->
        if (session.nickname != null && session.company != null) {
            def nickNameAndCompany = session.nickname + "@" + session.company
            out << "Hola " + nickNameAndCompany
        }
    }

}