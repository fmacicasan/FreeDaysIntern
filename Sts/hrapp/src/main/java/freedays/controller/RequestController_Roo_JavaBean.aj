// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.controller;

import freedays.security.UserContextService;

privileged aspect RequestController_Roo_JavaBean {
    
    public UserContextService RequestController.getUserContextService() {
        return this.userContextService;
    }
    
    public void RequestController.setUserContextService(UserContextService userContextService) {
        this.userContextService = userContextService;
    }
    
}
