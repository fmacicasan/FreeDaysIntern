package freedays.app;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import freedays.domain.RegularUser;
import freedays.util.RegularUserUtils;
import freedays.util.RegularUserUtils.RegularUserUtilsDiscriminators;

/**
 * 
 * Aspect which helps sorting regular and fd users
 * It wraps around all find* methods that return a list
 * Calls RegularUserUtils to sort the lists
 * 
 * @author osuciu
 *
 */

@Component
@Aspect
public class Sorting_Aspect {
	
	
	/**
	 * For sorting fd users
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(List<FDUser> freedays.app.FDUser.find*(..))")
	public Object sortFDUsers(ProceedingJoinPoint joinPoint) throws Throwable{
		List<FDUser> list = (List<FDUser>)joinPoint.proceed();
		return RegularUserUtils.sortAscendingApplicationRegularUsers(list, RegularUserUtilsDiscriminators.FIRSTNAME);
	}
	
	/**
	 * For sorting regular users
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("execution(List<RegularUser> freedays.domain.RegularUser.find*(..))")
	public Object sortRegularUsers(ProceedingJoinPoint joinPoint) throws Throwable{
		List<RegularUser> list = (List<RegularUser>)joinPoint.proceed();
		return RegularUserUtils.sortAscending(list, RegularUserUtilsDiscriminators.FIRSTNAME);
	}

	
}
