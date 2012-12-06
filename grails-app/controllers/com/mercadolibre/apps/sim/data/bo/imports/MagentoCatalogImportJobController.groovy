package com.mercadolibre.apps.sim.data.bo.imports

import grails.converters.JSON
import com.mercadolibre.apps.sim.CategoryService

class MagentoCatalogImportJobController {
  CategoryService categoryService

  static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], save: 'POST']

  def index() {
    redirect action: 'list', params: params
  }

  def list() {
    params.max = Math.min(params.max ? params.int('max') : 10, 100)
    [magentoCatalogImportJobInstanceList: MagentoCatalogImportJob.list(params), magentoCatalogImportJobInstanceTotal: MagentoCatalogImportJob.count()]
  }

  def save(StartMagentoExportCommand command) {
    def newJob = new MagentoCatalogImportJob(command.properties)
    newJob.errorItemsCount = 0
    def theRealJob

    if (newJob.validate()) {
      theRealJob = newJob.save(flush: true)

      sendQueueJMSMessage("queue.job.kickoff.notification",
          ['importJobId': theRealJob.id, 'accessToken': session.ml_access_token, 'callerId': session.ml_caller_id])
    }
    else {
      log.error("Errors in saving catalogImportJob: ${newJob.errors.fieldErrorCount}")
      newJob.errors.each()  {
        log.error(it)
      }
    }
    redirect(controller: "magentoCatalogImportJob", action: "show", params: ['id': theRealJob.id])
  }

  def status() {
    if (params.id && params.long('id') > 0) {
      render MagentoCatalogImportJob.get(params.id) as JSON
    }
    else {
      render "ERROR"
    }
  }

  def create() {
    switch (request.method) {
      case 'GET':
        [magentoCatalogImportJobInstance: new MagentoCatalogImportJob(params)]
        break
      case 'POST':
        def magentoCatalogImportJobInstance = new MagentoCatalogImportJob(params)
        if (!magentoCatalogImportJobInstance.save(flush: true)) {
          render view: 'create', model: [magentoCatalogImportJobInstance: magentoCatalogImportJobInstance]
          return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'magentoCatalogImportJob.label', default: 'MagentoCatalogImportJob'), magentoCatalogImportJobInstance.id])
        redirect action: 'show', id: magentoCatalogImportJobInstance.id
        break
    }
  }

  def show() {
    def magentoCatalogImportJobInstance = MagentoCatalogImportJob.get(params.id)
    if (!magentoCatalogImportJobInstance) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'magentoCatalogImportJob.label', default: 'MagentoCatalogImportJob'), params.id])
      redirect action: 'list'
      return
    }

    [magentoCatalogImportJobInstance: magentoCatalogImportJobInstance]
  }
}


/*
  Each of these will trigger an export of Stock Items from Magento and create new listings on ML

    productSelection=Products in Selected Categories

    sizeAttributeName=size
    sizeAppendedToSKU=on
    colorAttributeName=color
    storeCategory=3
    meliCategory=MLA9997
    buyingMode=buy_it_now

 */
class StartMagentoExportCommand {
  String   buyingMode
  Boolean  colorAppendedToSKU
  String   colorAttributeName
  String   htmlDescription
  String   listingType
  String   meliCategory
  Double   priceMarkup
  String   productSelection
  Boolean  sizeAppendedToSKU
  String   sizeAttributeName
  Double   stockPercentage
  Integer  storeCategory
}
