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


  void testCsvParseStrippedFromBootStrap() {
    assert true
    /*
        // appropriate for the BootStrap
        ApplicationContext applicationContext = servletContext.getAttribute(GrailsApplicationAttributes.APPLICATION_CONTEXT)
        CsvParserService csvParser = (CsvParserService) applicationContext.getBean("csvParserService")
        println csvParser
      */
  }

  void testSystemUnderTestGetsInjected() {
    assert applicationContext
    assert csvParserService
  }

  void testAllSamplesCanBeParsedToMaps() {
    File csvDirectory = applicationContext.getResource(File.separator + "WEB-INF" + File.separator + "docs").getFile();
    csvDirectory.list().each { csvFile ->
      if (csvFile.endsWith(".csv")) {
        def myFile = new File("${csvDirectory.path}/${csvFile}")
        println myFile.path
        assert myFile.exists()
        assert (csvParserService.parseItemInventoryFile(myFile).size() > 0)
      }
    }
  }

  void setApplicationContext(ApplicationContext aContext) throws BeansException {
    this.applicationContext = aContext
  }
}
