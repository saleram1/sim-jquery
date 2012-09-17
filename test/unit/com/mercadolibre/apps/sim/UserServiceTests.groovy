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

    void testValidateNickname() {
        userService = new UserService()
        assert userService.getNickname(119063128) == "TEST1682"
    }
}