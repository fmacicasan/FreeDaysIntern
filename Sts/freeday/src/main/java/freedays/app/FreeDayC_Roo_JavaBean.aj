// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import freedays.app.FreeDayR;
import java.util.Calendar;

privileged aspect FreeDayC_Roo_JavaBean {
    
    public Calendar FreeDayC.getRequestdate() {
        return this.requestdate;
    }
    
    public void FreeDayC.setRequestdate(Calendar requestdate) {
        this.requestdate = requestdate;
    }
    
    public FreeDayR FreeDayC.getRecover() {
        return this.recover;
    }
    
    public void FreeDayC.setRecover(FreeDayR recover) {
        this.recover = recover;
    }
    
}
