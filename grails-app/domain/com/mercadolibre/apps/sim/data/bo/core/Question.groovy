package com.mercadolibre.apps.sim.data.bo.core

import groovy.transform.ToString

/**
 * Q&A from Buyer Perspective - question may contain 0..1 answers
 *
 */
@ToString
class Question {
  String id
  String answerText
  Date answerDate
  String itemId
  Long questionId  //  MELI "id": 2473983524
  Long sellerId
  String status
  String text

  static constraints = {
    id()
    answerDate(nullable: true)
    answerText(nullable: true)
    itemId()
    sellerId()
    questionId()
    status(nullable: true, blank: false)
    text()
  }
  static mapping = {
    id generator: "uuid"
  }
}