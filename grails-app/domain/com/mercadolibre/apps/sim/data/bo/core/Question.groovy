package com.mercadolibre.apps.sim.data.bo.core

/**
 * Q&A from Buyer Perspective
 *
 */
class Question {

  String id
  String answerText
  Date answerDate
  String itemId
  Long sellerId
  String status
  String text

  static constraints = {
    id()
    answerDate(nullable: true)
    answerText(nullable: true)
    itemId()
    sellerId()
    status(nullable: true, blank: false)
    text()
  }
  static mapping = {
    id generator: "uuid"
  }
}