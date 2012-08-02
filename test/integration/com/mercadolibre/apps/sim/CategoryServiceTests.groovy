package com.mercadolibre.apps.sim

import static org.junit.Assert.*
import org.junit.*
import grails.test.mixin.TestFor

@TestFor(CategoryService)
class CategoryServiceTests {

  def categoryService

  @Before
  void setUp() {
    // Setup logic here
  }

  void testCollaboratorSystemUnderTestGetsInjected() {
    assert categoryService != null
  }

  void testBasicCategoryCheck() {
    assertNotNull categoryService.isValidCategory("MLA9997")
    assertTrue categoryService.isValidCategory("MLA9997")
  }

  void testFunkyFashionCategory() {
    assertNotNull categoryService.isValidCategory("MLA9997")
    assertTrue categoryService.isFunkyFashionFootwearCategory("MLA9997")
    assertTrue !categoryService.isFunkyFashionFootwearCategory("MLA78884")
  }

}
