// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.domain;

import freedays.domain.Admin;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect AdminDataOnDemand_Roo_DataOnDemand {
    
    declare @type: AdminDataOnDemand: @Component;
    
    private Random AdminDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<Admin> AdminDataOnDemand.data;
    
    public Admin AdminDataOnDemand.getNewTransientAdmin(int index) {
        freedays.domain.Admin obj = new freedays.domain.Admin();
        return obj;
    }
    
    public Admin AdminDataOnDemand.getSpecificAdmin(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Admin obj = data.get(index);
        return Admin.findAdmin(obj.getId());
    }
    
    public Admin AdminDataOnDemand.getRandomAdmin() {
        init();
        Admin obj = data.get(rnd.nextInt(data.size()));
        return Admin.findAdmin(obj.getId());
    }
    
    public boolean AdminDataOnDemand.modifyAdmin(Admin obj) {
        return false;
    }
    
    public void AdminDataOnDemand.init() {
        data = freedays.domain.Admin.findAdminEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Admin' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<freedays.domain.Admin>();
        for (int i = 0; i < 10; i++) {
            freedays.domain.Admin obj = getNewTransientAdmin(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
    
}