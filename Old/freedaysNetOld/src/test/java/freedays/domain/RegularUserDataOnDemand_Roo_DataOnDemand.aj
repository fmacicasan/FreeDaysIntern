// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.domain;

import freedays.domain.RegularUser;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect RegularUserDataOnDemand_Roo_DataOnDemand {
    
    declare @type: RegularUserDataOnDemand: @Component;
    
    private Random RegularUserDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<RegularUser> RegularUserDataOnDemand.data;
    
    public RegularUser RegularUserDataOnDemand.getNewTransientRegularUser(int index) {
        freedays.domain.RegularUser obj = new freedays.domain.RegularUser();
        setUsername(obj, index);
        setPassword(obj, index);
        setEmail(obj, index);
        return obj;
    }
    
    public void RegularUserDataOnDemand.setUsername(RegularUser obj, int index) {
        java.lang.String username = "username_" + index;
        obj.setUsername(username);
    }
    
    public void RegularUserDataOnDemand.setPassword(RegularUser obj, int index) {
        java.lang.String password = "password_" + index;
        obj.setPassword(password);
    }
    
    public void RegularUserDataOnDemand.setEmail(RegularUser obj, int index) {
        java.lang.String email = "email_" + index;
        obj.setEmail(email);
    }
    
    public RegularUser RegularUserDataOnDemand.getSpecificRegularUser(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        RegularUser obj = data.get(index);
        return RegularUser.findRegularUser(obj.getId());
    }
    
    public RegularUser RegularUserDataOnDemand.getRandomRegularUser() {
        init();
        RegularUser obj = data.get(rnd.nextInt(data.size()));
        return RegularUser.findRegularUser(obj.getId());
    }
    
    public boolean RegularUserDataOnDemand.modifyRegularUser(RegularUser obj) {
        return false;
    }
    
    public void RegularUserDataOnDemand.init() {
        data = freedays.domain.RegularUser.findRegularUserEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'RegularUser' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<freedays.domain.RegularUser>();
        for (int i = 0; i < 10; i++) {
            freedays.domain.RegularUser obj = getNewTransientRegularUser(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
    
}
