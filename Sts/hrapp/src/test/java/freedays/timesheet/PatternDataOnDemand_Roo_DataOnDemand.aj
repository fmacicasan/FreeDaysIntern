// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.timesheet;

import freedays.timesheet.Pattern;
import java.lang.Integer;
import java.security.SecureRandom;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect PatternDataOnDemand_Roo_DataOnDemand {
    
    declare @type: PatternDataOnDemand: @Component;
    
    private Random PatternDataOnDemand.rnd = new SecureRandom();
    
    public Pattern PatternDataOnDemand.getNewTransientPattern(int index) {
        Pattern obj = new Pattern();
        setDeletable(obj, index);
        setNoOfHours(obj, index);
        return obj;
    }
    
    public void PatternDataOnDemand.setDeletable(Pattern obj, int index) {
        Boolean deletable = true;
        obj.setDeletable(deletable);
    }
    
    public void PatternDataOnDemand.setNoOfHours(Pattern obj, int index) {
        Integer noOfHours = new Integer(index);
        obj.setNoOfHours(noOfHours);
    }
    
    public Pattern PatternDataOnDemand.getSpecificPattern(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Pattern obj = data.get(index);
        return Pattern.findPattern(obj.getId());
    }
    
    public Pattern PatternDataOnDemand.getRandomPattern() {
        init();
        Pattern obj = data.get(rnd.nextInt(data.size()));
        return Pattern.findPattern(obj.getId());
    }
    
    public boolean PatternDataOnDemand.modifyPattern(Pattern obj) {
        return false;
    }
    
}
