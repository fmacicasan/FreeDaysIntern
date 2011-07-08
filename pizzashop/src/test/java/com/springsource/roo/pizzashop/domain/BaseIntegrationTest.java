package com.springsource.roo.pizzashop.domain;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;

@RooIntegrationTest(entity = Base.class)
public class BaseIntegrationTest {

    @Test
    public void testMarkerMethod() {
    	Pizza pizza = new Pizza();
    	Assert.assertEquals(0, Pizza.countPizzas());
    	pizza.setName("Margarita");
    	pizza.persist();
    	Assert.assertEquals(1, Pizza.countPizzas());
    	Assert.assertEquals("Margarita", Pizza.findPizza(pizza.getId()).getName());
    }
}
