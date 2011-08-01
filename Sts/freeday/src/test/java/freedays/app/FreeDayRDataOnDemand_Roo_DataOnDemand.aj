// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import freedays.app.FreeDayR;
import freedays.domain.ApprovalStrategy;
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

privileged aspect FreeDayRDataOnDemand_Roo_DataOnDemand {
    
    declare @type: FreeDayRDataOnDemand: @Component;
    
    private Random FreeDayRDataOnDemand.rnd = new SecureRandom();
    
    private List<FreeDayR> FreeDayRDataOnDemand.data;
    
    public FreeDayR FreeDayRDataOnDemand.getNewTransientFreeDayR(int index) {
        FreeDayR obj = new FreeDayR();
        setApproval(obj, index);
        setReason(obj, index);
        setRecoverdate(obj, index);
        return obj;
    }
    
    public void FreeDayRDataOnDemand.setApproval(FreeDayR obj, int index) {
        ApprovalStrategy approval = null;
        obj.setApproval(approval);
    }
    
    public void FreeDayRDataOnDemand.setReason(FreeDayR obj, int index) {
        String reason = "reason_" + index;
        obj.setReason(reason);
    }
    
    public FreeDayR FreeDayRDataOnDemand.getSpecificFreeDayR(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        FreeDayR obj = data.get(index);
        return FreeDayR.findFreeDayR(obj.getId());
    }
    
    public FreeDayR FreeDayRDataOnDemand.getRandomFreeDayR() {
        init();
        FreeDayR obj = data.get(rnd.nextInt(data.size()));
        return FreeDayR.findFreeDayR(obj.getId());
    }
    
    public boolean FreeDayRDataOnDemand.modifyFreeDayR(FreeDayR obj) {
        return false;
    }
    
    public void FreeDayRDataOnDemand.init() {
        data = FreeDayR.findFreeDayREntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'FreeDayR' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<freedays.app.FreeDayR>();
        for (int i = 0; i < 10; i++) {
            FreeDayR obj = getNewTransientFreeDayR(i);
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