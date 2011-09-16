package freedays.app;

import org.springframework.roo.addon.dod.RooDataOnDemand;

import freedays.util.DateUtils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@RooDataOnDemand(entity = FreeDayM.class)
public class FreeDayMDataOnDemand {

	public FreeDayM getNewTransientFreeDayM(int index) {
        FreeDayM obj = new FreeDayM();
        setDate(obj, index);
        setApproval(obj, index);
        setReason(obj, index);
        setStatus(obj, index);
        return obj;
    }
	
	public void setDate(FreeDayM obj, int index) {
		obj.setDate(DateUtils.generateBusinessDay());
  }

    private Random rnd = new SecureRandom();
    
    private List<FreeDayM> data;
    
	public void init() {
//        data = FreeDayM.findFreeDayMEntries(0, 10);
//        if (data == null) throw new IllegalStateException("Find entries implementation for 'FreeDayM' illegally returned null");
//        if (!data.isEmpty()) {
//            return;
//        }
        
        data = new ArrayList<freedays.app.FreeDayM>();
        for (int i = 0; i < 10; i++) {
            FreeDayM obj = getNewTransientFreeDayM(i);
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
