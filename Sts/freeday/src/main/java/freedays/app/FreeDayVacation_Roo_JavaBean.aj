// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import java.util.Calendar;

privileged aspect FreeDayVacation_Roo_JavaBean {
    
    public Calendar FreeDayVacation.getBeginning() {
        return this.beginning;
    }
    
    public void FreeDayVacation.setBeginning(Calendar beginning) {
        this.beginning = beginning;
    }
    
    public long FreeDayVacation.getSpan() {
        return this.span;
    }
    
    public void FreeDayVacation.setSpan(long span) {
        this.span = span;
    }
    
    public ConfidenceLevel FreeDayVacation.getConfidence() {
        return this.confidence;
    }
    
    public void FreeDayVacation.setConfidence(ConfidenceLevel confidence) {
        this.confidence = confidence;
    }
    
}