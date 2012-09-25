package com.mercadolibre.apps.sim


import com.mercadolibre.apps.sim.data.bo.core.ConversationMessage
import com.mercadolibre.apps.sim.data.bo.core.Question
import com.mercadolibre.apps.sim.data.bo.core.QuestionService

import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONObject

/**
 * Primary listener for the notification callback URI - funnels all messages to their respective handler
 *
 */
class ItemsCallbackController {

  QuestionService questionService


  def index() {
    JSONObject message = request.JSON

    String topic
    String resource
    ConversationMessage cm
    (topic, resource) = storeConversationMessage(message)

    // refactor
    handleResourceAndUpdateCatalog(topic, resource)

    def responseMap = ['message': "${topic} message processed", 'error': "none", 'status': 200, 'cause': []]
    render(responseMap as JSON)
  }


  /**
   * Each blip from the feed we must save
   *
   * @param message
   * @return topic and resource attributes
   */
  def storeConversationMessage(message) {
    Integer attempts = message.getInt("attempts")
    Integer userId = message.getInt("user_id")
    String resource = message.getString("resource")
    String topic = message.getString("topic")

    def cm =
      new ConversationMessage(attempts: attempts, userId: userId, resource: resource, topic: topic)
    cm.save(flush: true, failOnError: true)

    log.info "This message was stored / updated from MELI: ${cm}"
    [topic, resource]
  }


  def handleResourceAndUpdateCatalog(String topic, String resource) {
    if ('questions' == topic) {
      def quest = questionService.storeOrAnswerQuestion(resource.split("/")[2] as Long)
    }
    else if ('orders' == topic) {
      log.info "Looks like a newOrder - "
    }
  }
}