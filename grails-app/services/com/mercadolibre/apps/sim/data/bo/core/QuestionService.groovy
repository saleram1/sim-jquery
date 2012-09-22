package com.mercadolibre.apps.sim.data.bo.core

import com.mercadolibre.apps.sim.data.bo.core.Question
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType


class QuestionService {


  Question storeOrAnswerQuestion(Long id) {
    if (Question.get(id)) {
      // saveOrUpdate
    }
    else {
      // save
      def quest = getQuestion(id)
      if (quest.validate())
        quest.save(flush:  true)
    }
  }

  Question getQuestion(Long id) {
    def builder = new HTTPBuilder("https://api.mercadolibre.com")

    try {
      builder.contentType = ContentType.JSON
      builder.get(path: "/questions/${id}") { resp, json ->
        return new Question(id: json['id'], itemId: json['item_id'] as Long, status: json['status'], text: json['text'], sellerId: json['seller_id'] as Long)
      }
    }
    catch (Exception ex) {
      log.error ex.message, ex
    }
  }
}
