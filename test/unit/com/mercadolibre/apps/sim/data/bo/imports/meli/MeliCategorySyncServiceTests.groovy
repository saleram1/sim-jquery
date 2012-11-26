package com.mercadolibre.apps.sim.data.bo.imports.meli



import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(MeliCategorySyncService)
class MeliCategorySyncServiceTests {

  def meliCategorySyncService
    @Before
    void setUp() {
      meliCategorySyncService = new MeliCategorySyncService()
    }
    
    void testSyncMeliCategoryZipFile() {
      meliCategorySyncService.syncCategoryZipFile()
    }
}
