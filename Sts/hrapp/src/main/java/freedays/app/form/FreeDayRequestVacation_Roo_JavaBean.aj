// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app.form;

import freedays.app.FreeDayVacation.ConfidenceLevel;
import java.util.Calendar;

privileged aspect FreeDayRequestVacation_Roo_JavaBean {
    
    public Calendar FreeDayRequestVacation.getFinish() {
        return this.finish;
    }
    
    public void FreeDayRequestVacation.setFinish(Calendar finish) {
        this.finish = finish;
    }
    
    public ConfidenceLevel FreeDayRequestVacation.getConfidence() {
        return this.confidence;
    }
    
    public void FreeDayRequestVacation.setConfidence(ConfidenceLevel confidence) {
        this.confidence = confidence;
    }
    
}