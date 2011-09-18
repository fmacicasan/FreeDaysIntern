// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.domain;

import freedays.domain.Admin;
import java.security.SecureRandom;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect AdminDataOnDemand_Roo_DataOnDemand {
    
    declare @type: AdminDataOnDemand: @Component;
    
    private Random AdminDataOnDemand.rnd = new SecureRandom();
    
    public Admin AdminDataOnDemand.getNewTransientAdmin(int index) {
        Admin obj = new Admin();
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
    
}