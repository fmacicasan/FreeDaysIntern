// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.controller;

import freedays.security.UserContextService;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

privileged aspect RegisterController_Roo_JavaBean {
    
    public UserContextService RegisterController.getUserContextService() {
        return this.userContextService;
    }
    
    public void RegisterController.setUserContextService(UserContextService userContextService) {
        this.userContextService = userContextService;
    }
    
    public MessageDigestPasswordEncoder RegisterController.getMessageDigestPasswordEncoder() {
        return this.messageDigestPasswordEncoder;
    }
    
    public void RegisterController.setMessageDigestPasswordEncoder(MessageDigestPasswordEncoder messageDigestPasswordEncoder) {
        this.messageDigestPasswordEncoder = messageDigestPasswordEncoder;
    }
    
}