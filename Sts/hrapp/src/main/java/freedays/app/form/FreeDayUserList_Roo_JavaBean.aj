// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app.form;

import freedays.app.form.FreeDayReportWrapper;
import freedays.security.UserContextService;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.List;

privileged aspect FreeDayUserList_Roo_JavaBean {
    
    public String FreeDayUserList.getUser() {
        return this.user;
    }
    
    public void FreeDayUserList.setUser(String user) {
        this.user = user;
    }
    
    public String FreeDayUserList.getJobrole() {
        return this.jobrole;
    }
    
    public void FreeDayUserList.setJobrole(String jobrole) {
        this.jobrole = jobrole;
    }
    
    public Long FreeDayUserList.getRemainingdays() {
        return this.remainingdays;
    }
    
    public void FreeDayUserList.setRemainingdays(Long remainingdays) {
        this.remainingdays = remainingdays;
    }
    
    public Long FreeDayUserList.getTotaldaysleft() {
        return this.totaldaysleft;
    }
    
    public void FreeDayUserList.setTotaldaysleft(Long totaldaysleft) {
        this.totaldaysleft = totaldaysleft;
    }
    
    public List<String> FreeDayUserList.getFreedays() {
        return this.freedays;
    }
    
    public void FreeDayUserList.setFreedays(List<String> freedays) {
        this.freedays = freedays;
    }
    
    public List<Integer> FreeDayUserList.getVacations() {
        return this.vacations;
    }
    
    public void FreeDayUserList.setVacations(List<Integer> vacations) {
        this.vacations = vacations;
    }
    
    public List<FreeDayReportWrapper> FreeDayUserList.getCombined() {
        return this.combined;
    }
    
    public void FreeDayUserList.setCombined(List<FreeDayReportWrapper> combined) {
        this.combined = combined;
    }
    
    public UserContextService FreeDayUserList.getUserContextService() {
        return this.userContextService;
    }
    
    public void FreeDayUserList.setUserContextService(UserContextService userContextService) {
        this.userContextService = userContextService;
    }
    
}
