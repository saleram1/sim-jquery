package com.mercadolibre.apps.magento

import com.mercadolibre.apps.sim.data.bo.imports.MagentoCatalogImportJob

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

//
//      {accessToken=APP_USR-12815-120316-9aa7c965201328eb629eb8883c7990cd__B_K__-85958951, importJobId=1}
//
  def onMessage(aMessage) {
    log.info aMessage

    MagentoCatalogImportJob importJob

    int currentCount = 0
    MagentoCatalogImportJob.withTransaction {
      importJob = MagentoCatalogImportJob.get(aMessage.importJobId)
      currentCount = importJob.validItemsCount
    }

    // while not done - update the total items by ten per cent
    while (importJob.validItemsCount < importJob.totalItemsCount) {
      Thread.sleep(4800L)

      MagentoCatalogImportJob.withTransaction {
        importJob = MagentoCatalogImportJob.get(aMessage.importJobId)
        currentCount = importJob.validItemsCount
      }

      MagentoCatalogImportJob.withTransaction {
        importJob = MagentoCatalogImportJob.load(aMessage.importJobId)
        importJob.validItemsCount = currentCount + 10
        importJob.save()
      }
    }

    MagentoCatalogImportJob.withTransaction {
      importJob = MagentoCatalogImportJob.get(aMessage.importJobId)
      importJob.validItemsCount = 100
      importJob.totalItemsCount = 100
      importJob.status = 'COMPLETE'
      importJob.description = "really, it's done for the Mage"
      importJob.save(flush: true)
      log.info importJob

    }

  }
}
