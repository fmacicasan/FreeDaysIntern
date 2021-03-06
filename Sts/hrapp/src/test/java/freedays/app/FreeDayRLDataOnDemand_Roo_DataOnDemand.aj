// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import freedays.app.FreeDayRL;
import java.lang.String;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

privileged aspect FreeDayRLDataOnDemand_Roo_DataOnDemand {
    
    declare @type: FreeDayRLDataOnDemand: @Component;
    
    private Random FreeDayRLDataOnDemand.rnd = new SecureRandom();
    
    private List<FreeDayRL> FreeDayRLDataOnDemand.data;
    
    public FreeDayRL FreeDayRLDataOnDemand.getNewTransientFreeDayRL(int index) {
        FreeDayRL obj = new FreeDayRL();
        setDescription(obj, index);
        setRomanianHoliday(obj, index);
        return obj;
    }
    
    public void FreeDayRLDataOnDemand.setDescription(FreeDayRL obj, int index) {
        String description = "description_" + index;
        obj.setDescription(description);
    }
    
    public void FreeDayRLDataOnDemand.setRomanianHoliday(FreeDayRL obj, int index) {
        Calendar romanianHoliday = Calendar.getInstance();
        obj.setRomanianHoliday(romanianHoliday);
    }
    
    public FreeDayRL FreeDayRLDataOnDemand.getSpecificFreeDayRL(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        FreeDayRL obj = data.get(index);
        return FreeDayRL.findFreeDayRL(obj.getId());
    }
    
    public FreeDayRL FreeDayRLDataOnDemand.getRandomFreeDayRL() {
        init();
        FreeDayRL obj = data.get(rnd.nextInt(data.size()));
        return FreeDayRL.findFreeDayRL(obj.getId());
    }
    
    public boolean FreeDayRLDataOnDemand.modifyFreeDayRL(FreeDayRL obj) {
        return false;
    }
    
    public void FreeDayRLDataOnDemand.init() {
        data = FreeDayRL.findFreeDayRLEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'FreeDayRL' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<freedays.app.FreeDayRL>();
        for (int i = 0; i < 10; i++) {
            FreeDayRL obj = getNewTransientFreeDayRL(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> it = e.getConstraintViolations().iterator(); it.hasNext();) {
                    ConstraintViolation<?> cv = it.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}
