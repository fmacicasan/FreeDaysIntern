package freedays.app;

import org.springframework.roo.addon.dod.RooDataOnDemand;

import freedays.domain.ApprovalStrategy;
import freedays.util.DateUtils;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@RooDataOnDemand(entity = FreeDayC.class)
public class FreeDayCDataOnDemand {

    private Random rnd = new SecureRandom();
    
    private List<FreeDayC> data;
    
	public static FreeDayC generateFreeDayC() {
		FreeDayC fdc = new FreeDayC();
		fdc.setApproval(AppStrategL1.getDefaultInitialStrateg());
		fdc.setRequestdate(DateUtils.generateFutureBusinessDay());
		return fdc;
	}

	public void setRequestdate(FreeDayC obj, int index) {
        obj.setRequestdate(DateUtils.generateFutureBusinessDay());
    }

	public void setApproval(FreeDayC obj, int index) {
        obj.setApproval(AppStrategL1.getDefaultInitialStrateg());
    }

	public void setRecover(FreeDayC obj, int index) {
        FreeDayR recover = null;
        obj.setRecover(recover);
    }

	public FreeDayC getRandomFreeDayC() {
        init();
        FreeDayC obj = data.get(rnd.nextInt(data.size()));
        return FreeDayC.findFreeDayC(obj.getId());
    }

	public void init() {
//        data = FreeDayC.findFreeDayCEntries(0, 10);
//        if (data == null) throw new IllegalStateException("Find entries implementation for 'FreeDayC' illegally returned null");
//        if (!data.isEmpty()) {
//            return;
//        }
        
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
