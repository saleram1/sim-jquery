package com.mercadolibre.apps.sim.data.bo..imports.meli

import static org.junit.Assert.*

import grails.test.mixin.*
import grails.test.mixin.support.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.support.GrailsUnitTestMixin} for usage instructions
 */
@TestMixin(GrailsUnitTestMixin)
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
