package freedays.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Utility class used for date generation & manipulation.
 * @author fmacicasan
 *
 */
public class DateUtils {

	/**
	 * Generates a random day of week between Calendar.MONDAY and Calendar.FRIDAY
	 * @return a value between Calendar.MONDAY and Calendar.FRIDAY
	 */
	public static int generateRandomBusinessDayOfWeek(){
		return new java.util.Random().nextInt(Calendar.THURSDAY)+2;
	}
	
	
	public static Calendar generateFutureBusinessDay(){
		return new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR) + 1, Calendar.getInstance().get(Calendar.MONTH), DateUtils.generateRandomBusinessDayOfWeek());
	}
	
	public static Calendar generatePastBusinessDay(){
		return new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR) - 1, Calendar.getInstance().get(Calendar.MONTH), DateUtils.generateRandomBusinessDayOfWeek());
	}
}
