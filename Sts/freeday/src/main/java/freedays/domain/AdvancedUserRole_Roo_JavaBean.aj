// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.domain;

import freedays.domain.ApplicationRegularUser;
import java.util.Set;

privileged aspect AdvancedUserRole_Roo_JavaBean {
    
    public Set<ApplicationRegularUser> AdvancedUserRole.getAppRegUsers() {
        return this.appRegUsers;
    }
    
    public void AdvancedUserRole.setAppRegUsers(Set<ApplicationRegularUser> appRegUsers) {
        this.appRegUsers = appRegUsers;
    }
    
}
