package com.mercadolibre.apps.sim.data.bo.imports

import org.springframework.dao.DataIntegrityViolationException

class MagentoCatalogImportJobController {

  def save() {
    def myJob = new MagentoCatalogImportJob(params).save(flush: true)
    println myJob.id



    redirect(actionName: "show", params: ['id': myJob.id])
  }


  static allowedMethods = [create: ['GET', 'POST'], edit: ['GET', 'POST'], delete: 'POST']

  def index() {
    redirect action: 'list', params: params
  }

  def list() {
    params.max = Math.min(params.max ? params.int('max') : 10, 100)
    [magentoCatalogImportJobInstanceList: MagentoCatalogImportJob.list(params), magentoCatalogImportJobInstanceTotal: MagentoCatalogImportJob.count()]
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