package com.mercadolibre.apps.sim.data.bo.core

import grails.test.mixin.*
import org.junit.*

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(ItemService)
class ItemServiceTests {

    def itemService

    @Before
    void setUp() {
        itemService = new ItemService()
    }

    void testValidItem() {
        assert itemService.getItemCommand("MLB233759102")
        println itemService.getItemCommand("MLB233759102")
    }
}
