// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import freedays.app.FreeDayL;
import freedays.domain.ApprovalStrategy;
import java.lang.String;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

privileged aspect FreeDayLDataOnDemand_Roo_DataOnDemand {
    
    declare @type: FreeDayLDataOnDemand: @Component;
    
    private List<FreeDayL> FreeDayLDataOnDemand.data;
    
    public FreeDayL FreeDayLDataOnDemand.getNewTransientFreeDayL(int index) {
        FreeDayL obj = new FreeDayL();
        setApproval(obj, index);
        setLegalday(obj, index);
        setReason(obj, index);
        return obj;
    }
    
    public void FreeDayLDataOnDemand.setApproval(FreeDayL obj, int index) {
        ApprovalStrategy approval = null;
        obj.setApproval(approval);
    }
    
    public void FreeDayLDataOnDemand.setLegalday(FreeDayL obj, int index) {
        Calendar legalday = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 1);
        obj.setLegalday(legalday);
    }
    
    public void FreeDayLDataOnDemand.setReason(FreeDayL obj, int index) {
        String reason = "reason_" + index;
        obj.setReason(reason);
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
    
    public void FreeDayLDataOnDemand.init() {
        data = FreeDayL.findFreeDayLEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'FreeDayL' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<freedays.app.FreeDayL>();
        for (int i = 0; i < 10; i++) {
            FreeDayL obj = getNewTransientFreeDayL(i);
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