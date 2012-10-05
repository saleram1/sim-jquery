package com.mercadolibre.apps.sim

import org.junit.*
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import grails.test.mixin.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(CsvParserService)
class CsvParserServiceTests implements ApplicationContextAware {

  ApplicationContext applicationContext
  CsvParserService csvParserService

  @Before
  void setUp() {
    // Setup logic here
  }


  @Test
  void testSystemUnderTestGetsInjected() {
    assert applicationContext
    assert csvParserService
  }


  @Test
  void testAllSamplesCanBeParsedToMaps() {
    File csvDirectory = applicationContext.getResource(File.separator + "WEB-INF" + File.separator + "docs").getFile();
    csvDirectory.list().each { csvFile ->
      if (csvFile.endsWith(".csv")) {
        def myFile = new File("${csvDirectory.path}/${csvFile}")

        //assertTrue("Service returns a List of HashMap",
        assert ((csvParserService.parseItemInventoryFile(myFile)) instanceof java.util.List)
        assert ((csvParserService.parseItemInventoryFile(myFile)).get(0) instanceof java.util.Map)

        //assertTrue("List size is greater than zero and throws no exceptions"
        assert (csvParserService.parseItemInventoryFile(myFile).size() >= 1)
      }
    }
  }


  @Test
  void testNullInputFileThrowsException() {
    shouldFail {
      csvParserService.parseItemInventoryFile(null)
    }
  }


  @Test
  void testBogusFileThrowsException() {
    shouldFail {
      csvParserService.parseItemInventoryFile(new File("/tmp/this/cannot/be/aFile/named/bogon.flux"))
    }
  }

  void setApplicationContext(ApplicationContext aContext) throws BeansException {
    this.applicationContext = aContext
  }
}
