// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import freedays.app.FreeDay.FreeDayStatus;
import freedays.app.FreeDayC;
import freedays.app.FreeDayRDataOnDemand;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect FreeDayCDataOnDemand_Roo_DataOnDemand {
    
    declare @type: FreeDayCDataOnDemand: @Component;
    
    private Random FreeDayCDataOnDemand.rnd = new SecureRandom();
    
    private List<FreeDayC> FreeDayCDataOnDemand.data;
    
    @Autowired
    private FreeDayRDataOnDemand FreeDayCDataOnDemand.freeDayRDataOnDemand;
    
    public FreeDayC FreeDayCDataOnDemand.getNewTransientFreeDayC(int index) {
        FreeDayC obj = new FreeDayC();
        setApproval(obj, index);
        setReason(obj, index);
        setRecover(obj, index);
        setRequestdate(obj, index);
        setStatus(obj, index);
        return obj;
    }
    
    public void FreeDayCDataOnDemand.setReason(FreeDayC obj, int index) {
        String reason = "reason_" + index;
        obj.setReason(reason);
    }
    
    public void FreeDayCDataOnDemand.setStatus(FreeDayC obj, int index) {
        FreeDayStatus status = FreeDayStatus.class.getEnumConstants()[0];
        obj.setStatus(status);
    }
    
    public FreeDayC FreeDayCDataOnDemand.getSpecificFreeDayC(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        FreeDayC obj = data.get(index);
        return FreeDayC.findFreeDayC(obj.getId());
    }
    
    public FreeDayC FreeDayCDataOnDemand.getRandomFreeDayC() {
        init();
        FreeDayC obj = data.get(rnd.nextInt(data.size()));
        return FreeDayC.findFreeDayC(obj.getId());
    }
    
    public boolean FreeDayCDataOnDemand.modifyFreeDayC(FreeDayC obj) {
        return false;
    }
    
    public void FreeDayCDataOnDemand.init() {
        data = FreeDayC.findFreeDayCEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'FreeDayC' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<freedays.app.FreeDayC>();
        for (int i = 0; i < 10; i++) {
            FreeDayC obj = getNewTransientFreeDayC(i);
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
