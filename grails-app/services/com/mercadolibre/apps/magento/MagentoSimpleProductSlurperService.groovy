package com.mercadolibre.apps.magento

/**
 * Created with IntelliJ IDEA.
 * User: saleram
 * Date: 11/30/12
 * Time: 8:48 PM
 * To change this template use File | Settings | File Templates.
 */
class MagentoSimpleProductSlurperService {

  MagentoSOAPCatalogService magentoSOAPCatalogService
  MagentoSOAPPingService magentoSOAPPingService


  static transactional = false
  static exposes = ['jms']
  static destination = "queue.job.kickoff.notification"


  def onMessage(aMessage) {
    log.info aMessage
  }
}
