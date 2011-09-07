package freedays.app;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.roo.addon.dod.RooDataOnDemand;

import freedays.util.DateUtils;

@RooDataOnDemand(entity = FreeDayR.class)
public class FreeDayRDataOnDemand {
	
	private Random rnd = new SecureRandom();
    
    private List<FreeDayR> data;

	public void setRecoverdate(FreeDayR obj, int index) {
//        obj.setRecoverdate(DateUtils.generateFutureWeekendDay());
		obj.setRecoverdate(DateUtils.generateWeekendDay());
    }

	public static FreeDayR getenrateFreeDayR() {
		FreeDayR fdr = new FreeDayR();
		fdr.setApproval(AppStrategL1.getDefaultInitialStrateg());
//		fdr.setRecoverdate(DateUtils.generateFutureWeekendDay());
		fdr.setRecoverdate(DateUtils.generateWeekendDay());
		return fdr;
	}

	public void setApproval(FreeDayR obj, int index) {
        obj.setApproval(AppStrategL1.getDefaultInitialStrateg());
    }

	public void setRequest(FreeDayR obj, int index) {
        FreeDayC request = null;
        obj.setRequest(request);
    }

	public void init() {
//        data = FreeDayR.findFreeDayREntries(0, 10);
//        if (data == null) throw new IllegalStateException("Find entries implementation for 'FreeDayR' illegally returned null");
//        if (!data.isEmpty()) {
//            return;
//        }
        
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
