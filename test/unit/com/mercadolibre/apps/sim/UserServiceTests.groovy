package com.mercadolibre.apps.sim

import grails.test.mixin.*
import org.junit.*

import com.mercadolibre.apps.sim.UserService

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(UserService)
class UserServiceTests {

  def userService

  @Before
  void setUp() {
    userService = new UserService()
  }

  void testRootUserValidNickname() {
    assertEquals "PROCESO_AUTOMATICO", userService.getNickname(0)
  }

  void testValidNickname() {
    assertEquals "TEST1682", userService.getNickname(119063128)
  }

  void testUserNotFoundShouldNotHaveNickname() {
    assertNull userService.getNickname(123)
  }
}