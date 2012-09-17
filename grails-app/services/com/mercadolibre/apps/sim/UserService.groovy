package com.mercadolibre.apps.sim

import groovyx.net.http.*
import static groovyx.net.http.ContentType.JSON
import static groovyx.net.http.Method.GET

class UserService {

  /**
   * Given a valid /users/:id checks profile and returns the nickname field
   *
   * @param userId
   * @return User's nickname or screen name - null if not found
   * @see com.mercadolibre.apps.sim.data.bo.core.User
   */
  String getNickname(int userId) {
    String nickName = null

    def builder = new HTTPBuilder("https://api.mercadolibre.com")

    try {
      builder.contentType = ContentType.JSON
      builder.get(path: "/users/${userId}") { resp, json ->
        nickName = json['nickname']
      }
    }
    catch (Exception ex) {
      if (ex?.message == 'Not Found') {
        log.warn "User ${userId} not found !!"
      }
      else {
        log.error ex.message, ex
      }
    }
    nickName
  }
}