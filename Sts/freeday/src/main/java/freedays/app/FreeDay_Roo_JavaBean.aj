// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import freedays.domain.ApprovalStrategy;
import java.lang.Integer;
import java.lang.String;

privileged aspect FreeDay_Roo_JavaBean {
    
    public ApprovalStrategy FreeDay.getApproval() {
        return this.approval;
    }
    
    public void FreeDay.setApproval(ApprovalStrategy approval) {
        this.approval = approval;
    }
    
    public String FreeDay.getReason() {
        return this.reason;
    }
    
    public void FreeDay.setReason(String reason) {
        this.reason = reason;
    }
    
    public Integer FreeDay.getYear() {
        return this.year;
    }
    
    public void FreeDay.setYear(Integer year) {
        this.year = year;
    }
    
    public Integer FreeDay.getNumber() {
        return this.number;
    }
    
    public void FreeDay.setNumber(Integer number) {
        this.number = number;
    }
    
    public FreeDayStatus FreeDay.getStatus() {
        return this.status;
    }
    
    public void FreeDay.setStatus(FreeDayStatus status) {
        this.status = status;
    }
    
}
