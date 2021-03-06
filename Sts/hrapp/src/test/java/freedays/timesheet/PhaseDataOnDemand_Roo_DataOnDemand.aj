// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.timesheet;

import freedays.timesheet.Phase;
import java.lang.String;
import java.security.SecureRandom;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect PhaseDataOnDemand_Roo_DataOnDemand {
    
    declare @type: PhaseDataOnDemand: @Component;
    
    private Random PhaseDataOnDemand.rnd = new SecureRandom();
    
    public Phase PhaseDataOnDemand.getNewTransientPhase(int index) {
        Phase obj = new Phase();
        setCode(obj, index);
        setName(obj, index);
        return obj;
    }
    
    public void PhaseDataOnDemand.setCode(Phase obj, int index) {
        String code = "code_" + index;
        obj.setCode(code);
    }
    
    public void PhaseDataOnDemand.setName(Phase obj, int index) {
        String name = "name_" + index;
        obj.setName(name);
    }
    
    public Phase PhaseDataOnDemand.getSpecificPhase(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Phase obj = data.get(index);
        return Phase.findPhase(obj.getId());
    }
    
    public Phase PhaseDataOnDemand.getRandomPhase() {
        init();
        Phase obj = data.get(rnd.nextInt(data.size()));
        return Phase.findPhase(obj.getId());
    }
    
    public boolean PhaseDataOnDemand.modifyPhase(Phase obj) {
        return false;
    }
    
}
