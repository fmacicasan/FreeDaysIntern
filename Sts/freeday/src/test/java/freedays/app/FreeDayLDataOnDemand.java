package freedays.app;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.roo.addon.dod.RooDataOnDemand;

import freedays.domain.ApprovalStrategy;
import freedays.util.DateUtils;

@RooDataOnDemand(entity = FreeDayL.class)
public class FreeDayLDataOnDemand {
	
	private Random rnd = new SecureRandom();
	
	public static FreeDayL generateFreeDay(){
		FreeDayL fd = new FreeDayL();
		
		Calendar instance = Calendar.getInstance();
		Calendar requestdate = new GregorianCalendar(instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DAY_OF_MONTH) + 1);
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
        Calendar requestdate = DateUtils.generateFutureBusinessDay();
        obj.setLegalday(requestdate);
    }

}
