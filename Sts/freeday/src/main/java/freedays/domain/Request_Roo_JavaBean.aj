// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.domain;

import freedays.app.FreeDay;
import freedays.app.RequestStatus;
import freedays.domain.ApplicationRegularUser;

privileged aspect Request_Roo_JavaBean {
    
    public ApplicationRegularUser Request.getAppreguser() {
        return this.appreguser;
    }
    
    public void Request.setAppreguser(ApplicationRegularUser appreguser) {
        this.appreguser = appreguser;
    }
    
    public FreeDay Request.getRequestable() {
        return this.requestable;
    }
    
    public void Request.setRequestable(FreeDay requestable) {
        this.requestable = requestable;
    }
    
    public RequestStatus Request.getStatus() {
        return this.status;
    }
    
    public void Request.setStatus(RequestStatus status) {
        this.status = status;
    }
    
    public ApplicationRegularUser Request.getApprover() {
        return this.approver;
    }
    
    public void Request.setApprover(ApplicationRegularUser approver) {
        this.approver = approver;
    }
    
}
