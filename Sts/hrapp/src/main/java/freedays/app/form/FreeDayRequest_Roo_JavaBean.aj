// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app.form;

import freedays.app.FreeDaysRCMatch;
import java.lang.String;
import java.util.Calendar;

privileged aspect FreeDayRequest_Roo_JavaBean {
    
    public Calendar FreeDayRequest.getReqdate() {
        return this.reqdate;
    }
    
    public void FreeDayRequest.setReqdate(Calendar reqdate) {
        this.reqdate = reqdate;
    }
    
    public String FreeDayRequest.getReason() {
        return this.reason;
    }
    
    public void FreeDayRequest.setReason(String reason) {
        this.reason = reason;
    }
    
    public RequestType FreeDayRequest.getReqtype() {
        return this.reqtype;
    }
    
    public void FreeDayRequest.setReqtype(RequestType reqtype) {
        this.reqtype = reqtype;
    }
    
    public FreeDaysRCMatch FreeDayRequest.getMatch() {
        return this.match;
    }
    
    public void FreeDayRequest.setMatch(FreeDaysRCMatch match) {
        this.match = match;
    }
    
}
