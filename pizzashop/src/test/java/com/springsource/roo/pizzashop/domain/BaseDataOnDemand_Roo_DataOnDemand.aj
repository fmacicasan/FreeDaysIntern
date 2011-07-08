// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.springsource.roo.pizzashop.domain;

import com.springsource.roo.pizzashop.domain.Base;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect BaseDataOnDemand_Roo_DataOnDemand {
    
    declare @type: BaseDataOnDemand: @Component;
    
    private Random BaseDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<Base> BaseDataOnDemand.data;
    
    public Base BaseDataOnDemand.getNewTransientBase(int index) {
        com.springsource.roo.pizzashop.domain.Base obj = new com.springsource.roo.pizzashop.domain.Base();
        setName(obj, index);
        setOrigin(obj, index);
        return obj;
    }
    
    public void BaseDataOnDemand.setName(Base obj, int index) {
        java.lang.String name = "name_" + index;
        obj.setName(name);
    }
    
    public void BaseDataOnDemand.setOrigin(Base obj, int index) {
        java.lang.String origin = "origin_" + index;
        obj.setOrigin(origin);
    }
    
    public Base BaseDataOnDemand.getSpecificBase(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Base obj = data.get(index);
        return Base.findBase(obj.getId());
    }
    
    public Base BaseDataOnDemand.getRandomBase() {
        init();
        Base obj = data.get(rnd.nextInt(data.size()));
        return Base.findBase(obj.getId());
    }
    
    public boolean BaseDataOnDemand.modifyBase(Base obj) {
        return false;
    }
    
    public void BaseDataOnDemand.init() {
        data = com.springsource.roo.pizzashop.domain.Base.findBaseEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Base' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<com.springsource.roo.pizzashop.domain.Base>();
        for (int i = 0; i < 10; i++) {
            com.springsource.roo.pizzashop.domain.Base obj = getNewTransientBase(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
    
}
