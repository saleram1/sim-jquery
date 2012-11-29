package com.mercadolibre.apps.sim.data.bo.imports.meli

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
      meliCategorySyncService.syncCategoryZipFile("https://api.mercadolibre.com/sites/MLA/categories/all", "/tmp/mercadoCatFile.gz")
    }

	void testGetMeliCategoryMD5() {
		meliCategorySyncService.getMeliCategoryMD5("https://api.mercadolibre.com/sites/MLA/categories/")
	}
	
/*	void testSaveMeliCategoryMD5() {
      meliCategorySyncService.saveMeliCategoryMD5()
	}*/
	
	void testUnzipCategoryZipFileFromMeli() {
	  meliCategorySyncService.unzipCategoryZipFileFromMeli("/tmp/mercadoCatFile.gz", "/tmp/mercadoCatFile.txt")
	}
	
	void testParseMeliCategoryIdsIntoFile() {
		meliCategorySyncService.parseMeliCategoryIdsIntoFile("/tmp/mercadoCatFile.txt", "/tmp/mercadoCatFileIds.txt")
	}
}
