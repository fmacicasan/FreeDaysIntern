package freedays.util;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.context.i18n.LocaleContextHolder;

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
	
	/**
	 * Generates an  calendar instance representing a BusinessDay in the future
	 * @return calendar instance of a future business day
	 */
	public static Calendar generateFutureBusinessDay(){
		//return new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR) + 1, Calendar.getInstance().get(Calendar.MONTH), DateUtils.generateRandomBusinessDayOfWeek());
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.YEAR, instance.get(Calendar.YEAR)+1);
		instance.set(Calendar.DAY_OF_WEEK, DateUtils.generateRandomBusinessDayOfWeek());
		return instance;
	}
	
	/**
	 * Generates an  calendar instance representing a BusinessDay in the past
	 * @return calendar instance of a past business day
	 */
	public static Calendar generatePastBusinessDay(){
		//return new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR) - 1, Calendar.getInstance().get(Calendar.MONTH), DateUtils.generateRandomBusinessDayOfWeek());
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.YEAR, instance.get(Calendar.YEAR)-1);
		instance.set(Calendar.DAY_OF_WEEK, DateUtils.generateRandomBusinessDayOfWeek());
		return instance;
	}
	
	/**
	 * Generates an  calendar instance representing a WeekendDay in the future
	 * @return calendar instance of a future weekend day
	 */
	public static Calendar generateFutureWeekendDay(){
		//return new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR) + 1, Calendar.getInstance().get(Calendar.MONTH), DateUtils.generateRandomWeekendDayOfWeek());
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.YEAR, instance.get(Calendar.YEAR)+1);
		instance.set(Calendar.DAY_OF_WEEK, DateUtils.generateRandomWeekendDayOfWeek());
		return instance;
	}
	
	/**
	 * Generates a random weekend day, either Calendar.SATURDAY or Calendar.SUNDAY
	 * @return either Calendar.SATURDAY or Calendar.SUNDAY
	 */
	public static int generateRandomWeekendDayOfWeek() {
		 return Calendar.SATURDAY; //Saturday is the normal recovery day of week
	}
	
	/**
	 * Transforms a Calendar instance in it's textual representation based on a
	 * default short format : td.tm
	 * <ul>example: <li>04-22-2011 -> 22.04;</li> <li>12-13-2010 -> 13.12</li></ul>
	 * @param date a Calendar instance to be printed
	 * @return the textual representation of date
	 */
	public static String printShortDate(Calendar date){
		return String.format("%1$td.%1$tm", date);// %1$tk %1$tm
	}
	
	/**
	 * Computes the difference in days between the two provided Calendar instances.
	 * @param start the starting day of the period
	 * @param end the ending day of the period
	 * @return days between start and end
	 * @throws IllegalArgumentException if one of the dates is null or start is greater than end
	 */
	public static long dateDifferenceInDays(Calendar start, Calendar end){
		if(start == null)throw new IllegalArgumentException("The start argument is required");
		if(end == null)throw new IllegalArgumentException("The end argument is required");
		if(!start.before(end)) throw new IllegalArgumentException("start must be before end"); 
		
		long time = end.getTimeInMillis();
		time -= start.getTimeInMillis();
		System.out.println(time);
		return TimeUnit.MILLISECONDS.toDays(time);
	}
	
	public static long dateDifferenceInWorkingDays(Calendar start, Calendar end) {
		if(start == null)throw new IllegalArgumentException("The start argument is required");
		if(end == null)throw new IllegalArgumentException("The end argument is required");
		if(start.compareTo(end)<=0) throw new IllegalArgumentException("start must be before end"); 
		Long span = 0L;
		for(Calendar c = (Calendar) start.clone();c.compareTo(end)<=0;c.set(Calendar.DAY_OF_YEAR, 1)){
			if(ValidationUtils.checkBusinessDay(c)){
				span ++;
			}
		}
		return span;
	}
	
	/**
	 * Increase the day count of the Calendar instance with the given amount
	 * @param start the initial Calendar instance
	 * @param days the amount of days to be added
	 * @return a Calendar instance further in the future with the given amount of days
	 */
	public static Calendar dateAddDay(Calendar start, Long days){
		Calendar finish = (Calendar) start.clone();
		finish.add(Calendar.DAY_OF_YEAR, days.intValue());
		return finish;
	}
	
	/**
	 * Converts a string in a Calendar instance based on a predefined format (MM/dd/yyyy)
	 * @param s the string to be converted
	 * @return the corresponding Date instance
	 * @throws IllegalArgumentException if the string does not respect the format
	 */
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

	/**
	 * Provides a sequence of strings representing the short print version of the
	 * days between the provided Calendar instances.
	 * @param start the starting day of the sequence
	 * @param end the ending day of the sequence
	 * @return a list of short date representations
	 * @throws IllegalArgumentException if one of the dates is null or start is greater than end
	 */
	public static List<String> getShortDateList(Calendar start, Calendar end) {
		if(start == null)throw new IllegalArgumentException("The start argument is required");
		if(end == null)throw new IllegalArgumentException("The end argument is required");
		if(!start.before(end)) throw new IllegalArgumentException("start must be before end"); 
		List<String> ls = new ArrayList<String>();
		end.add(Calendar.DAY_OF_YEAR, 1);
		for(Calendar c = (Calendar) start.clone();c.before(end);c.add(Calendar.DAY_OF_YEAR, 1)){
			ls.add(DateUtils.printShortDate(c));
		}
		return ls;
	}
	
	/**
	 * Minimal string representation of the all displayed dates in a month.
	 * @param month 
	 * @return
	 */
	public static List<String> getShortDateList(int month) {
		if(Calendar.JANUARY > month || month > Calendar.DECEMBER)throw new IllegalArgumentException("month must be between Jan and December"); 
		List<String> ls = new ArrayList<String>();
		Calendar now =Calendar.getInstance();
		now.set(Calendar.MONTH, month);
		now.set(Calendar.DAY_OF_MONTH, 1);
		for(Calendar c = (Calendar) now.clone();c.get(Calendar.MONTH)==now.get(Calendar.MONTH);c.add(Calendar.DAY_OF_YEAR, 1)){
			ls.add(DateUtils.printShortDate(c));
		}
		return ls;
	}
	

	public static int getDaysInMonth(int month) {
		if(Calendar.JANUARY > month || month > Calendar.DECEMBER)throw new IllegalArgumentException("month must be between Jan and December");
		Calendar now = Calendar.getInstance();
		now.set(Calendar.MONTH, month);
		int days = now.getActualMaximum(Calendar.DAY_OF_MONTH);
		//System.out.println(days);
		return days;
	}
//	public static void main(String[] args){
//		for(int i=Calendar.JANUARY;i<=Calendar.DECEMBER;i++){
//			getDaysInMonth(i);
//		}
//	}

	public static boolean isSameMonth(Calendar date, int month) {
		return date.get(Calendar.MONTH) == month;
	}

	public static int getDay(Calendar date) {
		return date.get(Calendar.DAY_OF_MONTH);
	}

	public static List<String> getWeekdayInitialsList(int month) {
		if(Calendar.JANUARY > month || month > Calendar.DECEMBER)throw new IllegalArgumentException("month must be between Jan and December");
		List<String> ls = new ArrayList<String>();
		Calendar now =Calendar.getInstance();
		now.set(Calendar.MONTH, month);
		now.set(Calendar.DAY_OF_MONTH, 1);
		for(Calendar c = (Calendar) now.clone();c.get(Calendar.MONTH)==now.get(Calendar.MONTH);c.add(Calendar.DAY_OF_YEAR, 1)){
			ls.add(DateUtils.printWeekdayInitial(c));
		}
		return ls;
	}

	private static String printWeekdayInitial(Calendar c) {
		return String.format("%1$tA", c).substring(0,1);
	}

	public static List<String> getMonthNames() {
		Locale local =  LocaleContextHolder.getLocale();
		String[] months = new DateFormatSymbols(local).getMonths();
		return Arrays.asList(months);
	}

	/**
	 * returns current month between 1 and 12
	 * @return
	 */
	public static Integer getCurrentMonth() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MONTH) + 1;
	}

	public static boolean isValidMonth(Integer m) {
		if(m==null) return false;
		m = DateUtils.transformMonth(m);//bring it between Calendar.January and Calendar.December
		return Calendar.JANUARY <= m &&  m <= Calendar.DECEMBER;
	}

	/**
	 * Transforms application month (1-12) to Calendar month(0-11)
	 * @param m
	 * @return
	 */
	public static int transformMonth(Integer m) {
		return --m;
	}

	/**
	 * Adds to the provided date the number of business days represented by span.
	 * @param start initial date
	 * @param span number of business days
	 * @return
	 */
	public static Calendar dateAddBusinessDay(Calendar start, Long span) {
		Calendar date = (Calendar) start.clone();
		if(ValidationUtils.checkWeekend(date)){
			if(date.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY){
				date.add(Calendar.DAY_OF_YEAR, 1);
			}
			date.add(Calendar.DAY_OF_YEAR, 1);
		}
		
		Long days = span % 5;
		Long full = (span / 5) * 7;
		if((Calendar.FRIDAY - date.get(Calendar.DAY_OF_WEEK))<days){
			days+=2;
		}
		date.add(Calendar.DAY_OF_MONTH, days.intValue()+full.intValue());
		return date;
	}

	
}
