package com.mercadolibre.apps.sim.data.bo.core

/**
 * Message related to an Item
 *
 * These could be filed under item / order / question / payment / etc
 * and others as needed

 * @author Mike Salera
 */

import groovy.transform.ToString

@ToString
class ConversationMessage {
  Integer attempts
  String resource
  Date sentDate
  String topic
  Integer userId

  Date dateCreated

  static constraints = {
    attempts()
    dateCreated(nullable:  true)
    resource()
    sentDate(nullable:  true)
    userId()
  }
}
