// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.springsource.roo.pizzashop.domain;

import com.springsource.roo.pizzashop.domain.BaseDataOnDemand;
import com.springsource.roo.pizzashop.domain.Pizza;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect PizzaDataOnDemand_Roo_DataOnDemand {
    
    declare @type: PizzaDataOnDemand: @Component;
    
    private Random PizzaDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<Pizza> PizzaDataOnDemand.data;
    
    @Autowired
    private BaseDataOnDemand PizzaDataOnDemand.baseDataOnDemand;
    
    public Pizza PizzaDataOnDemand.getNewTransientPizza(int index) {
        com.springsource.roo.pizzashop.domain.Pizza obj = new com.springsource.roo.pizzashop.domain.Pizza();
        setName(obj, index);
        setPrice(obj, index);
        setBase(obj, index);
        return obj;
    }
    
    public void PizzaDataOnDemand.setName(Pizza obj, int index) {
        java.lang.String name = "name_" + index;
        obj.setName(name);
    }
    
    public void PizzaDataOnDemand.setPrice(Pizza obj, int index) {
        java.lang.Float price = new Integer(index).floatValue();
        obj.setPrice(price);
    }
    
    public void PizzaDataOnDemand.setBase(Pizza obj, int index) {
        com.springsource.roo.pizzashop.domain.Base base = baseDataOnDemand.getRandomBase();
        obj.setBase(base);
    }
    
    public Pizza PizzaDataOnDemand.getSpecificPizza(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Pizza obj = data.get(index);
        return Pizza.findPizza(obj.getId());
    }
    
    public Pizza PizzaDataOnDemand.getRandomPizza() {
        init();
        Pizza obj = data.get(rnd.nextInt(data.size()));
        return Pizza.findPizza(obj.getId());
    }
    
    public boolean PizzaDataOnDemand.modifyPizza(Pizza obj) {
        return false;
    }
    
    public void PizzaDataOnDemand.init() {
        data = com.springsource.roo.pizzashop.domain.Pizza.findPizzaEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Pizza' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<com.springsource.roo.pizzashop.domain.Pizza>();
        for (int i = 0; i < 10; i++) {
            com.springsource.roo.pizzashop.domain.Pizza obj = getNewTransientPizza(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
    
}
