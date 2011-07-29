package freedays.app;

import java.security.SecureRandom;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.roo.addon.dod.RooDataOnDemand;

import freedays.domain.ApprovalStrategy;

@RooDataOnDemand(entity = FreeDayL.class)
public class FreeDayLDataOnDemand {
	
	private Random rnd = new SecureRandom();
	
	public static FreeDayL generateFreeDay(){
		FreeDayL fd = new FreeDayL();
		
		Calendar instance = Calendar.getInstance();
		Calendar requestdate = new GregorianCalendar(instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DAY_OF_MONTH) + 1);
		fd.setRequestdate(requestdate);
		
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
        Calendar requestdate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR) + 1, Calendar.getInstance().get(Calendar.MONTH), rnd.nextInt(Calendar.THURSDAY)+2);
        obj.setRequestdate(requestdate);
    }

}
