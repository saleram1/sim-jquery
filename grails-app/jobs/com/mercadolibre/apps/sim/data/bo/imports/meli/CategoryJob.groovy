package com.mercadolibre.apps.sim.data.bo.imports.meli

import com.mercadolibre.apps.sim.CategoryService

class CategoryJob {

//  static triggers = {
//    simple repeatInterval: 50000l // execute job once in 5 seconds
//  }

  static triggers = {
    cron cronExpression: '0 30 4 * * ?' 
  }

  CategoryService categoryService
  MeliCategorySyncService meliCategorySyncService


  def execute() {
    log.info "Running Category Job"
    
    if (!meliCategorySyncService.isMeliCategoryInSync()) {
      meliCategorySyncService.syncCategoryZipFile("https://api.mercadolibre.com/sites/MLA/categories/all", "/tmp/mercadoCatFile.gz")
      meliCategorySyncService.unzipCategoryZipFileFromMeli("/tmp/mercadoCatFile.gz", "/tmp/mercadoCatFile.txt")
      meliCategorySyncService.parseMeliCategoryIdsIntoFile("/tmp/mercadoCatFile.txt", "/tmp/mercadoCatFileIds.txt")
      meliCategorySyncService.saveMeliCategoryMD5()
      categoryService.loadMeliCategories()
    }
  }
}