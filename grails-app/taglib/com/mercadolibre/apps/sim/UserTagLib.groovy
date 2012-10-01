package com.mercadolibre.apps.sim

class UserTagLib {

  def userService

  def nickNameAndCompany = { attrs, body ->
    if (session.nickname != null && session.company != null) {
      def nickNameAndCompany = session.nickname + " @ " + session.company
      out << "Hello, " + nickNameAndCompany
      out << "&nbsp;"
      out << "(<a href=\"${createLink(controller: 'signout')}\">Sign out</a>)"
    }
  }

  def logoutLink = { attrs, body ->
    out << "&nbsp;"
    out << "(<a href=\"${createLink(controller: 'signout')}\">Sign out</a>)"
  }
}
