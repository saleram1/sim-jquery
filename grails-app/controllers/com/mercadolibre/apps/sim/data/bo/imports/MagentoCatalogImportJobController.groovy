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

  def save() {
    def newJob = new MagentoCatalogImportJob(params)
    newJob.errorItemsCount = 0
    def theRealJob

    if (newJob.validate()) {
      theRealJob = newJob.save(flush: true)

      sendQueueJMSMessage("queue.job.kickoff.notification",
          ['importJobId': theRealJob.id, 'accessToken': session.ml_access_token])
    }
    else {
      log.error("Errors in saving catalogImportJob: ${newJob.errors.fieldErrorCount}")
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
