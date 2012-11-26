package com.mercadolibre.apps.sim.data.bo.imports.meli



class CategoryJob {
  
  def meliCategorySyncService
  
  static triggers = {
    simple repeatInterval: 50000l // execute job once in 5 seconds
  }

  def execute() {
    println "Running Category Job"
    meliCategorySyncService.syncCategoryZipFile()
    
    
  }
}
