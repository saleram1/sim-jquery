package com.mercadolibre.apps.sim

import static org.junit.Assert.*
import org.junit.*
import grails.test.mixin.TestFor


@TestFor(ImportService)
class ImportServiceTests {

  def importService

  @Before
  void setUp() {
    // Setup logic here
  }

  void testCollaboratorSystemUnderTestGetsInjected() {
    assert importService != null
  }

  // deliberately cause an error in the marketplace insert and ensure itemImport rec is correct
  void testNewItemToMarketplace() {
	assertTrue true
  }
}