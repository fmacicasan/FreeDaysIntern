// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import freedays.app.FreeDay.FreeDayStatus;
import freedays.app.FreeDayL;
import freedays.domain.ApprovalStrategy;
import java.lang.Integer;
import java.lang.String;
import org.springframework.stereotype.Component;

privileged aspect FreeDayDataOnDemand_Roo_DataOnDemand {
    
    declare @type: FreeDayDataOnDemand: @Component;
    
    public void FreeDayDataOnDemand.setApproval(FreeDayL obj, int index) {
        ApprovalStrategy approval = null;
        obj.setApproval(approval);
    }
    
    public void FreeDayDataOnDemand.setNumber(FreeDayL obj, int index) {
        Integer number = new Integer(index);
        obj.setNumber(number);
    }
    
    public void FreeDayDataOnDemand.setReason(FreeDayL obj, int index) {
        String reason = "reason_" + index;
        obj.setReason(reason);
    }
    
    public void FreeDayDataOnDemand.setStatus(FreeDayL obj, int index) {
        FreeDayStatus status = FreeDayStatus.class.getEnumConstants()[0];
        obj.setStatus(status);
    }
    
    public void FreeDayDataOnDemand.setYear(FreeDayL obj, int index) {
        Integer year = new Integer(index);
        obj.setYear(year);
    }
    
    public FreeDayL FreeDayDataOnDemand.getSpecificFreeDayL(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        FreeDayL obj = data.get(index);
        return FreeDayL.findFreeDayL(obj.getId());
    }
    
    public FreeDayL FreeDayDataOnDemand.getRandomFreeDayL() {
        init();
        FreeDayL obj = data.get(rnd.nextInt(data.size()));
        return FreeDayL.findFreeDayL(obj.getId());
    }
    
    public boolean FreeDayDataOnDemand.modifyFreeDayL(FreeDayL obj) {
        return false;
    }
    
}
