// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.domain;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.stereotype.Component;

privileged aspect RegularUserDataOnDemand_Roo_DataOnDemand {
    
    declare @type: RegularUserDataOnDemand: @Component;
    
    private Random RegularUserDataOnDemand.rnd = new SecureRandom();
    
    private List<RegularUser> RegularUserDataOnDemand.data;
    
    public RegularUser RegularUserDataOnDemand.getNewTransientRegularUser(int index) {
        RegularUser obj = new RegularUser();
        setActiv(obj, index);
        setCreationdate(obj, index);
        setDeleted(obj, index);
        setEmail(obj, index);
        setFirstname(obj, index);
        setLastmodified(obj, index);
        setPassword(obj, index);
        setSurename(obj, index);
        setUsermodifier(obj, index);
        setUsername(obj, index);
        return obj;
    }
    
    public void RegularUserDataOnDemand.setActiv(RegularUser obj, int index) {
        Boolean activ = Boolean.TRUE;
        obj.setActiv(activ);
    }
    
    public void RegularUserDataOnDemand.setCreationdate(RegularUser obj, int index) {
        Calendar creationdate = Calendar.getInstance();
        obj.setCreationdate(creationdate);
    }
    
    public void RegularUserDataOnDemand.setDeleted(RegularUser obj, int index) {
        Boolean deleted = Boolean.TRUE;
        obj.setDeleted(deleted);
    }
    
    public void RegularUserDataOnDemand.setFirstname(RegularUser obj, int index) {
        String firstname = "firstname_" + index;
        obj.setFirstname(firstname);
    }
    
    public void RegularUserDataOnDemand.setLastmodified(RegularUser obj, int index) {
        Calendar lastmodified = Calendar.getInstance();
        obj.setLastmodified(lastmodified);
    }
    
    public void RegularUserDataOnDemand.setPassword(RegularUser obj, int index) {
        String password = "password_" + index;
        obj.setPassword(password);
    }
    
    public void RegularUserDataOnDemand.setSurename(RegularUser obj, int index) {
        String surename = "surename_" + index;
        obj.setSurename(surename);
    }
    
    public void RegularUserDataOnDemand.setUsermodifier(RegularUser obj, int index) {
        String usermodifier = "usermodifier_" + index;
        obj.setUsermodifier(usermodifier);
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
        data = RegularUser.findRegularUserEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'RegularUser' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<freedays.domain.RegularUser>();
        for (int i = 0; i < 10; i++) {
            RegularUser obj = getNewTransientRegularUser(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> it = e.getConstraintViolations().iterator(); it.hasNext();) {
                    ConstraintViolation<?> cv = it.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}
