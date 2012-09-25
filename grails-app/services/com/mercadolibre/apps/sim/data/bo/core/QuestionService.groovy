package com.mercadolibre.apps.sim.data.bo.core

import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import net.sf.json.JSONNull

class QuestionService {

  /**
   * Saves the new question from Buyer - or updates existing one with the answer . text
   * from Seller
   *
   * @param id
   * @return   - domain object which was saved
   */
  Question storeOrAnswerQuestion(Long id) {
    def aQuestion = Question.findByQuestionId(id)
    def meliQuestion = getMeliQuestion(id)

    if (aQuestion) {
      if (meliQuestion.answerText) {
        aQuestion.answerDate = meliQuestion.answerDate
        aQuestion.answerText = meliQuestion.answerText
        aQuestion.status     = meliQuestion.status

        log.info "Updated his answerText at ${aQuestion.answerDate}"
      }
      return aQuestion.save(flush: true)
    }
    else {
      if (meliQuestion?.validate()) {
        log.info "${meliQuestion} ready to save!"
        return meliQuestion.save(flush:  true)
      }
    }
    return null
  }


  /**
   * Return contents of Questions api as friendlier domain object
   *
   * @param id - /question/:id from ML
   * @return
   */
  Question getMeliQuestion(Long id) {
    def builder = new HTTPBuilder("https://api.mercadolibre.com")

    try {
      builder.contentType = ContentType.JSON
      builder.get(path: "/questions/${id}") { resp, json ->
        // success 200 response
        def newQuestion =
          new Question(questionId: json['id'], itemId: json['item_id'], status: json['status'], text: json['text'], sellerId: json['seller_id'] as Long)

        def answer = json['answer']
        if (!(answer instanceof JSONNull)) {
          newQuestion.answerDate = new Date()
          newQuestion.answerText = answer.text
        }
        return newQuestion
      }
    }
    catch (Exception ex) {
      log.error("Question ${id} not found.")
      log.error(ex.message)
    }
  }
}
