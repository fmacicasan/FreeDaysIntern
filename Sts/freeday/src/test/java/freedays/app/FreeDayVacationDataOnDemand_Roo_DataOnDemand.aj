// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import freedays.app.FreeDay.FreeDayStatus;
import freedays.app.FreeDayVacation;
import freedays.app.FreeDayVacation.ConfidenceLevel;
import freedays.domain.ApprovalStrategy;
import java.lang.Integer;
import java.lang.String;
import org.springframework.stereotype.Component;

privileged aspect FreeDayVacationDataOnDemand_Roo_DataOnDemand {
    
    declare @type: FreeDayVacationDataOnDemand: @Component;
    
    public void FreeDayVacationDataOnDemand.setApproval(FreeDayVacation obj, int index) {
        ApprovalStrategy approval = null;
        obj.setApproval(approval);
    }
    
    public void FreeDayVacationDataOnDemand.setConfidence(FreeDayVacation obj, int index) {
        ConfidenceLevel confidence = null;
        obj.setConfidence(confidence);
    }
    
    public void FreeDayVacationDataOnDemand.setNumber(FreeDayVacation obj, int index) {
        Integer number = new Integer(index);
        obj.setNumber(number);
    }
    
    public void FreeDayVacationDataOnDemand.setReason(FreeDayVacation obj, int index) {
        String reason = "reason_" + index;
        obj.setReason(reason);
    }
    
    public void FreeDayVacationDataOnDemand.setStatus(FreeDayVacation obj, int index) {
        FreeDayStatus status = FreeDayStatus.class.getEnumConstants()[0];
        obj.setStatus(status);
    }
    
    public void FreeDayVacationDataOnDemand.setYear(FreeDayVacation obj, int index) {
        Integer year = new Integer(index);
        obj.setYear(year);
    }
    
    public FreeDayVacation FreeDayVacationDataOnDemand.getSpecificFreeDayVacation(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        FreeDayVacation obj = data.get(index);
        return FreeDayVacation.findFreeDayVacation(obj.getId());
    }
    
    public FreeDayVacation FreeDayVacationDataOnDemand.getRandomFreeDayVacation() {
        init();
        FreeDayVacation obj = data.get(rnd.nextInt(data.size()));
        return FreeDayVacation.findFreeDayVacation(obj.getId());
    }
    
    public boolean FreeDayVacationDataOnDemand.modifyFreeDayVacation(FreeDayVacation obj) {
        return false;
    }
    
}
