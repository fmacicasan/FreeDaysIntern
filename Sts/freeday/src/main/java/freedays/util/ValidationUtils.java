package freedays.util;

import java.util.Calendar;

public class ValidationUtils {

	public static boolean checkBusinessDay(Calendar day){
		int dayOfWeek = day.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek != Calendar.SATURDAY
				&& dayOfWeek != Calendar.SUNDAY;
	}
	
	public static boolean checkWeekend(Calendar day){
		int dayOfWeek = day.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
		//TODO: change to !checkBusinessDay
	}
}
