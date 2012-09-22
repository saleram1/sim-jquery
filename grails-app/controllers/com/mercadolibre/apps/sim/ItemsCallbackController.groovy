package com.mercadolibre.apps.sim

import grails.converters.JSON
import com.mercadolibre.apps.sim.data.bo.core.ConversationMessage
import org.codehaus.groovy.grails.web.json.JSONObject
import com.mercadolibre.apps.sim.data.bo.core.QuestionService

/**
 * Primary listener for the notification callback URI
 *
 */
class ItemsCallbackController {

  QuestionService questionService


  def index() {
    JSONObject message = request.JSON

    Integer attempts = message.getInt("attempts")
    Integer userId = message.getInt("user_id")
    String resource  = message.getString("resource")
    String topic     = message.getString("topic")

    def cm =
      new ConversationMessage(attempts: attempts, userId: userId, resource: resource, topic:  topic)
    cm.save(flush: true)

    log.info 'This message was stored / updated from the MELI web:'
    log.info cm

    // need to change for Item resp ??
    def responseMap = ['message': "Items message processed", 'error': "none", 'status': 200, 'cause': []]
    response.status = 200
    render(responseMap as JSON)
  }
}