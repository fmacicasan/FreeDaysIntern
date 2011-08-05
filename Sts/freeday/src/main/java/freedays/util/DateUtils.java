package freedays.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Utility class used for date generation & manipulation.'
 * NOTE:
 * 	Investigat: problems with Gregorian Calendar conversion of DAY_OF_WEEK => switched to Calendar.
 * @author fmacicasan
 *
 */
public class DateUtils {

	/**
	 * Generates a random day of week between Calendar.MONDAY and Calendar.FRIDAY
	 * @return a value between Calendar.MONDAY and Calendar.FRIDAY
	 */
	public static int generateRandomBusinessDayOfWeek(){
		return new Random().nextInt(Calendar.THURSDAY)+2;
	}
	
	
	public static Calendar generateFutureBusinessDay(){
		//return new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR) + 1, Calendar.getInstance().get(Calendar.MONTH), DateUtils.generateRandomBusinessDayOfWeek());
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.YEAR, instance.get(Calendar.YEAR)+1);
		instance.set(Calendar.DAY_OF_WEEK, DateUtils.generateRandomBusinessDayOfWeek());
		return instance;
	}
	
	public static Calendar generatePastBusinessDay(){
		//return new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR) - 1, Calendar.getInstance().get(Calendar.MONTH), DateUtils.generateRandomBusinessDayOfWeek());
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.YEAR, instance.get(Calendar.YEAR)-1);
		instance.set(Calendar.DAY_OF_WEEK, DateUtils.generateRandomBusinessDayOfWeek());
		return instance;
	}
	
	public static Calendar generateFutureWeekendDay(){
		//return new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR) + 1, Calendar.getInstance().get(Calendar.MONTH), DateUtils.generateRandomWeekendDayOfWeek());
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.YEAR, instance.get(Calendar.YEAR)+1);
		instance.set(Calendar.DAY_OF_WEEK, DateUtils.generateRandomWeekendDayOfWeek());
		return instance;
	}
	
	public static int generateRandomWeekendDayOfWeek() {
		 return Calendar.SATURDAY; //Saturday is the normal recovery day of week
	}
	
	public static String printShortDate(Calendar date){
		return String.format("%1$td.%1$tm", date);
	}
	
	public static long dateDifferenceInDays(Calendar start, Calendar end){
		if(start == null)throw new IllegalArgumentException("The start argument is required");
		if(end == null)throw new IllegalArgumentException("The end argument is required");
		if(!start.before(end)) throw new IllegalArgumentException("start must be before end"); 
		
		long time = end.getTimeInMillis();
		time -= start.getTimeInMillis();
		return TimeUnit.MILLISECONDS.toDays(time);
	}
	
	public static Calendar dateAddDay(Calendar start, Long days){
		Calendar finish = (Calendar) start.clone();
		finish.add(Calendar.DAY_OF_YEAR, days.intValue());
		return finish;
	}
	
	public static Calendar convString2Calendar(String s){
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		try {
			Date d = df.parse(s);
			c.setTime(d);
			return c;
		} catch (ParseException e) {
			throw new IllegalArgumentException("The s argument must respect format mm/dd/yyyy");
		}
		
	}
}
