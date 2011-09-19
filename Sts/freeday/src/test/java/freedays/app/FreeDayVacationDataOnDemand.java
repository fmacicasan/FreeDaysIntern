package freedays.app;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.roo.addon.dod.RooDataOnDemand;

import freedays.util.DateUtils;

@RooDataOnDemand(entity = FreeDayVacation.class)
public class FreeDayVacationDataOnDemand {

	private List<FreeDayVacation> data;
	private Random rnd = new SecureRandom();
	public void init() {
//        data = FreeDayVacation.findFreeDayVacationEntries(0, 10);
//        if (data == null) throw new IllegalStateException("Find entries implementation for 'FreeDayVacation' illegally returned null");
//        if (!data.isEmpty()) {
//            return;
//        }
        
        data = new ArrayList<freedays.app.FreeDayVacation>();
        for (int i = 0; i < 10; i++) {
            FreeDayVacation obj = getNewTransientFreeDayVacation(i);
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
	
	public void setDate(FreeDayVacation obj, int index){
		Calendar date = DateUtils.generateBusinessDay();
		obj.setDate(date);
	}

	public FreeDayVacation getNewTransientFreeDayVacation(int index) {
        FreeDayVacation obj = new FreeDayVacation();
        setApproval(obj, index);
        setDate(obj,index);
        setConfidence(obj, index);
        setReason(obj, index);
        setSpan(obj, index);
        setStatus(obj, index);
        return obj;
    }

	public void setSpan(FreeDayVacation obj, int index) {
        Long span = new Integer(rnd.nextInt(10)+1).longValue();
        obj.setSpan(span);
    }
}
