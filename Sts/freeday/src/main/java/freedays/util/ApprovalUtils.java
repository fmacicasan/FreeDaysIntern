package freedays.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import freedays.domain.ApprovalStrategy;

public class ApprovalUtils {
	public static ApprovalStrategy getDefaultApprovalStrategy(){
//		ApplicationContext ac = new ClassPathXmlApplicationContext("META-INF/spring/applicationContext-FreeDaysApprovalStrategy.xml");
//		return (ApprovalStrategy) ac.getBean("level1");
		return null;
	}
}
