// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.security;

import freedays.domain.RegularUser;
import java.lang.Boolean;
import java.lang.String;
import java.util.Calendar;

privileged aspect InfoChanger_Roo_JavaBean {
    
    public String InfoChanger.getExpcode() {
        return this.expcode;
    }
    
    public void InfoChanger.setExpcode(String expcode) {
        this.expcode = expcode;
    }
    
    public Calendar InfoChanger.getExpdate() {
        return this.expdate;
    }
    
    public void InfoChanger.setExpdate(Calendar expdate) {
        this.expdate = expdate;
    }
    
    public RegularUser InfoChanger.getRegularUser() {
        return this.regularUser;
    }
    
    public void InfoChanger.setRegularUser(RegularUser regularUser) {
        this.regularUser = regularUser;
    }
    
    public Boolean InfoChanger.getExpired() {
        return this.expired;
    }
    
    public void InfoChanger.setExpired(Boolean expired) {
        this.expired = expired;
    }
    
    public Boolean InfoChanger.getUsed() {
        return this.used;
    }
    
    public void InfoChanger.setUsed(Boolean used) {
        this.used = used;
    }
    
}