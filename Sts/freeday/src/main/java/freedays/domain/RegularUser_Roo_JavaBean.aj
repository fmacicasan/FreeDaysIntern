// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.domain;

import java.lang.Boolean;
import java.lang.String;
import java.util.Calendar;

privileged aspect RegularUser_Roo_JavaBean {
    
    public void RegularUser.setUsername(String username) {
        this.username = username;
    }
    
    public String RegularUser.getPassword() {
        return this.password;
    }
    
    public void RegularUser.setPassword(String password) {
        this.password = password;
    }
    
    public void RegularUser.setSurename(String surename) {
        this.surename = surename;
    }
    
    public void RegularUser.setFirstname(String firstname) {
        this.firstname = firstname;
    }
    
    public Boolean RegularUser.getDeleted() {
        return this.deleted;
    }
    
    public void RegularUser.setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    
    public Boolean RegularUser.getActiv() {
        return this.activ;
    }
    
    public void RegularUser.setActiv(Boolean activ) {
        this.activ = activ;
    }
    
    public Calendar RegularUser.getLastmodified() {
        return this.lastmodified;
    }
    
    public void RegularUser.setLastmodified(Calendar lastmodified) {
        this.lastmodified = lastmodified;
    }
    
    public Calendar RegularUser.getCreationdate() {
        return this.creationdate;
    }
    
    public void RegularUser.setCreationdate(Calendar creationdate) {
        this.creationdate = creationdate;
    }
    
    public void RegularUser.setUsermodifier(String usermodifier) {
        this.usermodifier = usermodifier;
    }
    
}
