// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import freedays.app.FreeDay.FreeDayStatus;
import freedays.app.FreeDayM;
import freedays.domain.ApprovalStrategy;
import java.lang.String;
import org.springframework.stereotype.Component;

privileged aspect FreeDayMDataOnDemand_Roo_DataOnDemand {
    
    declare @type: FreeDayMDataOnDemand: @Component;
    
    public void FreeDayMDataOnDemand.setApproval(FreeDayM obj, int index) {
        ApprovalStrategy approval = null;
        obj.setApproval(approval);
    }
    
    public void FreeDayMDataOnDemand.setReason(FreeDayM obj, int index) {
        String reason = "reason_" + index;
        obj.setReason(reason);
    }
    
    public void FreeDayMDataOnDemand.setStatus(FreeDayM obj, int index) {
        FreeDayStatus status = FreeDayStatus.class.getEnumConstants()[0];
        obj.setStatus(status);
    }
    
    public FreeDayM FreeDayMDataOnDemand.getSpecificFreeDayM(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        FreeDayM obj = data.get(index);
        return FreeDayM.findFreeDayM(obj.getId());
    }
    
    public FreeDayM FreeDayMDataOnDemand.getRandomFreeDayM() {
        init();
        FreeDayM obj = data.get(rnd.nextInt(data.size()));
        return FreeDayM.findFreeDayM(obj.getId());
    }
    
    public boolean FreeDayMDataOnDemand.modifyFreeDayM(FreeDayM obj) {
        return false;
    }
    
}
