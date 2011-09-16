// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import freedays.app.FreeDay.FreeDayStatus;
import freedays.app.FreeDayL;
import java.lang.String;
import org.springframework.stereotype.Component;

privileged aspect FreeDayLDataOnDemand_Roo_DataOnDemand {
    
    declare @type: FreeDayLDataOnDemand: @Component;
    
    public void FreeDayLDataOnDemand.setReason(FreeDayL obj, int index) {
        String reason = "reason_" + index;
        obj.setReason(reason);
    }
    
    public void FreeDayLDataOnDemand.setStatus(FreeDayL obj, int index) {
        FreeDayStatus status = FreeDayStatus.class.getEnumConstants()[0];
        obj.setStatus(status);
    }
    
    public FreeDayL FreeDayLDataOnDemand.getSpecificFreeDayL(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        FreeDayL obj = data.get(index);
        return FreeDayL.findFreeDayL(obj.getId());
    }
    
    public FreeDayL FreeDayLDataOnDemand.getRandomFreeDayL() {
        init();
        FreeDayL obj = data.get(rnd.nextInt(data.size()));
        return FreeDayL.findFreeDayL(obj.getId());
    }
    
    public boolean FreeDayLDataOnDemand.modifyFreeDayL(FreeDayL obj) {
        return false;
    }
    
}
