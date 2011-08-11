package freedays.app;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.roo.addon.dod.RooDataOnDemand;

import freedays.domain.ApprovalStrategy;
import freedays.util.DateUtils;

@RooDataOnDemand(entity = FreeDayL.class)
public class FreeDayDataOnDemand {
	
	private List<FreeDayL> data;
	private Random rnd = new SecureRandom();
	
	public static FreeDay generateFreeDay(){
		FreeDayL fd = new FreeDayL();
		
		Calendar requestdate = DateUtils.generateFutureBusinessDay();
		fd.setLegalday(requestdate);
		
//		ApplicationContext ac = new ClassPathXmlApplicationContext("META-INF/spring/applicationContext-FreeDaysApprovalStrategy.xml");
//		ApprovalStrategy app = (ApprovalStrategy) ac.getBean("level1");
//		fd.setApproval(app);
		fd.setApproval(AppStrategL1.getDefaultInitialStrateg());
//		ApprovalStrategy app2 = new AppStrategLTop();
//		app2.setSuccesor(null);
//		ApprovalStrategy app1 = new  AppStrategL1();
//		app1.setSuccesor(app2);
//		fd.setApproval(app1);
			
		return fd;
		
	}

	public void setRequestdate(FreeDayL obj, int index) {
        obj.setLegalday(DateUtils.generateFutureBusinessDay());
    }


	public void setLegalday(FreeDayL obj, int index) {
        //Calendar legalday = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 1);
        obj.setLegalday(DateUtils.generateFutureBusinessDay());
    }

	public void init() {
//        data = FreeDayL.findFreeDayLEntries(0, 10);
//        if (data == null) throw new IllegalStateException("Find entries implementation for 'FreeDayL' illegally returned null");
//        if (!data.isEmpty()) {
//            return;
//        }
        
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
