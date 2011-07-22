package freedays.app;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.roo.addon.dod.RooDataOnDemand;

import freedays.domain.ApprovalStrategy;

@RooDataOnDemand(entity = FreeDay.class)
public class FreeDayDataOnDemand {
	
	public static FreeDay generateFreeDay(){
		FreeDay fd = new FreeDay();
		
		Calendar instance = Calendar.getInstance();
		Calendar requestdate = new GregorianCalendar(instance.get(Calendar.YEAR), instance.get(Calendar.MONTH), instance.get(Calendar.DAY_OF_MONTH) + 1);
		fd.setRequestdate(requestdate);
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("META-INF/spring/applicationContext-FreeDaysApprovalStrategy.xml");
		ApprovalStrategy app = (ApprovalStrategy) ac.getBean("level1");
		fd.setApproval(app);
//		ApprovalStrategy app2 = new AppStrategLTop();
//		app2.setSuccesor(null);
//		ApprovalStrategy app1 = new  AppStrategL1();
//		app1.setSuccesor(app2);
//		fd.setApproval(app1);
			
		return fd;
		
	}
}
