package freedays.util;

import java.util.Calendar;

/**
 * Utility class used by the validators to check the consistency of 
 * various classes.
 * @author fmacicasan
 *
 */
public class ValidationUtils {

	/**
	 * Checks weather or not a Calendar instance represents a business day
	 * @param day the Calendar instance to be verified
	 * @return false if the instance represents a weekend day
	 */
	public static boolean checkBusinessDay(Calendar day){
		int dayOfWeek = day.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek != Calendar.SATURDAY
				&& dayOfWeek != Calendar.SUNDAY;
	}
	
	/**
	 * Checks weather or not a Calendar instance represents a weekend day
	 * @param day the Calendar instance to be verified
	 * @return true if the instance represents a weekend day
	 */
	public static boolean checkWeekend(Calendar day){
		int dayOfWeek = day.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
		//TODO: change to !checkBusinessDay
	}
	
//	public static <T> T getSingleResult(TypedQuery<T> q) {
//	List<T> results = q.getResultList();
//	if (!results.isEmpty()) {
//		return results.get(0);
//	}
//	return null;
//}
}
