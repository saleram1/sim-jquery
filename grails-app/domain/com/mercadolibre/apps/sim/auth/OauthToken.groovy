package com.mercadolibre.apps.sim.auth

class OauthToken {

  String accessToken  // want to bcrypt this with the existing Sim password

  static constraints = {
    accessToken(nullable: false)
  }
}
