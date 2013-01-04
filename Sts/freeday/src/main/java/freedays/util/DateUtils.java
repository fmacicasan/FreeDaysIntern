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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * Utility class used for date generation & manipulation.'
 * NOTE:
 * 	Investigat: problems with Gregorian Calendar conversion of DAY_OF_WEEK => switched to Calendar.
 * @author fmacicasan
 *
 */
public class DateUtils {
	
	private static final Logger logger  = LoggerFactory.getLogger(DateUtils.class);

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
	 * z@deprecated As of release 1.3, replaced by {@link DateUtils#generateBusinessDay()}
	 */
	//@Deprecated
	public static Calendar generateFutureBusinessDay(){
		//return new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR) + 1, Calendar.getInstance().get(Calendar.MONTH), DateUtils.generateRandomBusinessDayOfWeek());
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.YEAR, instance.get(Calendar.YEAR)+1);
		instance.set(Calendar.DAY_OF_WEEK, DateUtils.generateRandomBusinessDayOfWeek());
		return instance;
	}
	
	/**
	 * Generates a calendar instance representing a BusinessDay
	 * @return calendar instance of a business day
	 */
	public static Calendar generateBusinessDay(){
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.DAY_OF_WEEK, DateUtils.generateRandomBusinessDayOfWeek());
		return instance;
	}
	
	/**
	 * Generates an  calendar instance representing a BusinessDay in the past
	 * @return calendar instance of a past business day
	 * z@deprecated As of release 1.3, replaced by {@link DateUtils#generateBusinessDay()}
	 */
	//@Deprecated
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
	 * @deprecated As of release 1.3, replaced by {@link DateUtils#generateWeekendDay()}
	 */
	@Deprecated
	public static Calendar generateFutureWeekendDay(){
		//return new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR) + 1, Calendar.getInstance().get(Calendar.MONTH), DateUtils.generateRandomWeekendDayOfWeek());
		Calendar instance = Calendar.getInstance();
		instance.set(Calendar.YEAR, instance.get(Calendar.YEAR)+1);
		instance.set(Calendar.DAY_OF_WEEK, DateUtils.generateRandomWeekendDayOfWeek());
		return instance;
	}
	
	/**
	 * Generates an calendar instance representing a WeekendDay in the future
	 * @return calendar instance of a weekend day
	 */
	public static Calendar generateWeekendDay(){
		Calendar instance = Calendar.getInstance();
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
	
	public static String printShortDateYear(Calendar date){
		return String.format("%1$td.%1$tm.%1$ty", date);// %1$tk %1$tm
	}
	
	public static String printLongDate(Calendar date){
		return String.format("%1$tA, %1$te %1$tB %1$tY", date);
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
		//System.out.println(time);
		return TimeUnit.MILLISECONDS.toDays(time);
	}
	
	/**
	 * Computes the difference in working days between the provided dates 
	 * considering the Romanian legislation legal days for the current year.
	 * A work day is represented by a normal business day except the ones
	 * that are free based on the romanian legislation.
	 * @param start the start of the period
	 * @param end the end of the period
	 * @return the number of working days between (start, end] (does not consider the 1st day)
	 */
	public static Long dateDifferenceInWorkingDays(Calendar start, Calendar end) {
	    if(start.compareTo(end) == 0){
	        return 0l;
	    }
		Calendar clone = (Calendar) start.clone();
        clone.add(Calendar.DAY_OF_YEAR, 1);
        return dateDifferenceInWorkingDaysIncludingEnds(clone, end);
	}
	
	public static Long dateDifferenceInWorkingDaysIncludingEnds(Calendar start, Calendar end){
	    if(start == null)throw new IllegalArgumentException("The start argument is required");
        if(end == null)throw new IllegalArgumentException("The end argument is required");
        if(start.compareTo(end)>0) throw new IllegalArgumentException("start must be before end");
        Long span = 0L;
        for(Calendar c = (Calendar) start.clone();c.compareTo(end)<=0;c.add(Calendar.DAY_OF_YEAR, 1)){
            if(ValidationUtils.checkBusinessDay(c) && !ValidationUtils.checkRomanianLegalHoliday(c)){
                span ++;
            }
        }       
        return span;
	}
	
	public static Long dateDifferenceInBusinessDays(Calendar start, Calendar end) {
		if(start == null)throw new IllegalArgumentException("The start argument is required");
		if(end == null)throw new IllegalArgumentException("The end argument is required");
		if(start.compareTo(end)>0) throw new IllegalArgumentException("start must be before end");		
		Long span = 0L;
		Calendar clone = (Calendar) start.clone();
		clone.add(Calendar.DAY_OF_YEAR, 1);
		for(Calendar c = clone;c.compareTo(end)<=0;c.add(Calendar.DAY_OF_YEAR, 1)){
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
		int yearAdjuster = preprocessYear(month);
		month = preprocessMonth(month);
		
		if(Calendar.JANUARY > month || month > Calendar.DECEMBER)throw new IllegalArgumentException("month must be between Jan and December"); 
		List<String> ls = new ArrayList<String>();
		Calendar now =Calendar.getInstance();
		now.set(Calendar.MONTH, month);
		now.set(Calendar.DAY_OF_MONTH, 1);
		now.set(Calendar.YEAR,now.get(Calendar.YEAR)+yearAdjuster);
		for(Calendar c = (Calendar) now.clone();c.get(Calendar.MONTH)==now.get(Calendar.MONTH);c.add(Calendar.DAY_OF_YEAR, 1)){
			ls.add(DateUtils.printShortDate(c));
		}
		return ls;
	}
	
	public static int preprocessMonth(int month){
		return (month+12)%12;
	}
	
	private static int preprocessYear(int month){
		int yearAdjuster = 0;
		switch(month){
		case(-1):
			yearAdjuster = -1;
			break;
		case(12):
			yearAdjuster = 1;
			break;
		default:
			yearAdjuster = 0;
		break;
		}
		return yearAdjuster;
	}
	
	public static int getDaysInMonth112(int month){
	    return getDaysInMonth(transformMonth(preprocessMonth(month)));
	}
	/**
	 * Returns the number of days in a given month.
	 * MOnth -1 is december and 12 is january
	 * @param month between Calendar.JANUARY and Calendar.DECEMBER
	 * @return
	 */
	public static int getDaysInMonth(int month) {
		int yearAdjuster = preprocessYear(month);
		month = preprocessMonth(month);
		//logger.info("computing days in month!!!!!!!!!"+month);
		
		if(Calendar.JANUARY > month || month > Calendar.DECEMBER)throw new IllegalArgumentException("month must be between Jan and December");
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_MONTH, 1);//needed for instances with DAY_OF_MONTH 30/31 that will increment months with 28/29/30 days
		now.set(Calendar.MONTH, month);
		now.set(Calendar.YEAR, now.get(Calendar.YEAR)+yearAdjuster);
		int days = now.getActualMaximum(Calendar.DAY_OF_MONTH);
		//System.out.println(days+" "+month);
		return days;
	}
//	public static void main(String[] args){
//		for(int i=Calendar.JANUARY;i<=Calendar.DECEMBER;i++){
//			getDaysInMonth(i);
//		}
//	}

	/**
	 * Verifies weather or not the calendar instance is in the given month
	 * @param date
	 * @param month between Calendar.JANUARY and Calendar.DECEMBER
	 * @return
	 */
	public static boolean isSameMonth(Calendar date, int month) {
		month = preprocessMonth(month);
		return date.get(Calendar.MONTH) == month;
	}

	/**
	 * Returs the month day of a given date
	 * @param date
	 * @return
	 */
	public static int getDay(Calendar date) {
		return date.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Retrieves all the initials of the days in the corresponding month
	 * @param month valid between Calendar.JANUARY and Calendar.DECEMBER
	 * @return
	 */
	public static List<String> getWeekdayInitialsList(int month) {
		int yearAdjuster = preprocessYear(month);
		month = preprocessMonth(month);
		if(Calendar.JANUARY > month || month > Calendar.DECEMBER)throw new IllegalArgumentException("month must be between Jan and December");
		List<String> ls = new ArrayList<String>();
		Calendar now =Calendar.getInstance();
		now.set(Calendar.MONTH, month);
		now.set(Calendar.DAY_OF_MONTH, 1);
		now.set(Calendar.YEAR,now.get(Calendar.YEAR)+yearAdjuster);
		for(Calendar c = (Calendar) now.clone();c.get(Calendar.MONTH)==now.get(Calendar.MONTH);c.add(Calendar.DAY_OF_YEAR, 1)){
			ls.add(DateUtils.printWeekdayInitial(c));
		}
		return ls;
	}

	/**
	 * Retrieves the initial of the weekday represented by the calendar instance.
	 * @param c
	 * @return
	 */
	private static String printWeekdayInitial(Calendar c) {
		return String.format("%1$tA", c).substring(0,1);
	}

	
	/**
	 * Retrieves the localized month names
	 */
	public static List<String> getMonthNames() {
		Locale local =  LocaleContextHolder.getLocale();
		String[] months = new DateFormatSymbols(local).getMonths();
		return Arrays.asList(months);
	}
	
	public static List<String> getMonthNamesExtended(){
		List<String> names = getMonthNames();
		List<String> extendednames = new ArrayList<String>();
		//list from 0 to size-1
		extendednames.add(names.get(11));
		//names will be corrupted with some extra values rather
		for(int i=0;i<12;i++){
			extendednames.add(names.get(i));
		}
		
		extendednames.add(names.get(0));
		return extendednames;
	}

	/**
	 * returns current month between 1 and 12
	 * @return
	 */
	public static Integer getCurrentMonth() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.MONTH) + 1;
	}

	/**
	 * Verifies weather or not a month is valid (between 1 and 12)
	 * @param m
	 * @return
	 */
	public static boolean isValidMonth(Integer m) {
		if(m==null) return false;
		m = DateUtils.transformMonth(m);//bring it between Calendar.January and Calendar.December
		return Calendar.JANUARY -1 <= m &&  m <= Calendar.DECEMBER + 1;
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
//		System.out.println(DateUtils.printShortDate(date));
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
//		System.out.println(DateUtils.printShortDate(date));
		return date;
	}

	/**
	 * Adds to the privided date the number of working days offered by span considering
	 * both the weekends and the romanian legislation days
	 * @param start
	 * @param span
	 * @return
	 */
	public static Calendar dateAddRomanianBusinessDay(Calendar start, Long span){
		//start day wont be holiday
		//end date wont be holiday
		//=> holiday will be in between
		//store the start date + the rest of working days untill the end
		//if
		//no solution like this because there can be any number of remaining stuff
		//
		Integer init = 0;
		if(ValidationUtils.checkRomanianLegalHoliday(start)){
			init++;
		}
		Calendar date = (Calendar)start.clone();
		while(span > 0){
			date.add(Calendar.DAY_OF_YEAR, 1);
//			System.out.println(DateUtils.printShortDate(date));
			if(!ValidationUtils.checkWeekend(date)){
				if(!ValidationUtils.checkRomanianLegalHoliday(date)){
					span--;
				} 
				init++;
			}
			
		}
		return DateUtils.dateAddBusinessDay(start, init.longValue());
	}
	
	public static boolean isCurrentYear(Calendar date) {
		Calendar c = Calendar.getInstance();
		return date.get(Calendar.YEAR)==c.get(Calendar.YEAR);
	}
	
	public static boolean isPreviousYear(Calendar date){
		Calendar c = Calendar.getInstance();
		return date.get(Calendar.YEAR) == (c.get(Calendar.YEAR) - 1);
	}
	
	public static boolean isNextYear(Calendar date){
		Calendar c = Calendar.getInstance();
		return date.get(Calendar.YEAR) == (c.get(Calendar.YEAR) + 1);
		
	}
	
//	private static boolean isSameYear(Integer year, Calendar date){
//		return date.get(Calendar.YEAR) == year;
//	}
	
	public static boolean isReportableDate(Integer month, Calendar date){
		boolean extra = false;
		
		switch(month){
		case -1:
			extra = DateUtils.isPreviousYear(date);
			
			break;
		case 12:
			extra =  DateUtils.isNextYear(date);
			break;
		default:
			extra = DateUtils.isCurrentYear(date);
			
		}
		return DateUtils.isSameMonth(date, month) && extra;
	}
	
	public static String printLong(Calendar c){
		return String.format("%1$tA, %1$te %1$tB %1$tY %tT", c);
	}
	
	public static boolean isDayEqual(Calendar first, Calendar second){
	    if(first.get(Calendar.DAY_OF_MONTH) == second.get(Calendar.DAY_OF_MONTH)
	            && first.get(Calendar.MONTH) == second.get(Calendar.MONTH)
	            && first.get(Calendar.YEAR) == second.get(Calendar.YEAR)){
	        return true;
	    }
	    return false;
	}
	
	public static boolean isIntervalOverlapFilter(Calendar start, Calendar end, Calendar filterLeft, Calendar filterRight){
	    return !(end.before(filterLeft) || start.after(filterRight));
	}
	
	public static boolean isDateBetween(Calendar date, Calendar left, Calendar right){
	    return date.compareTo(left) >= 0 && date.compareTo(right) <= 0;
	}
	
	public static Calendar createFirstDayOfMonth(Integer month, Integer year){
	    if(!isValidMonth(month)){
	        month = preprocessMonth(month);
	    }
	    Calendar c = DateUtils.convString2Calendar(month+"/1/"+year);
	    return c;
	}
	
	public static Calendar createLastDayOfMonth(Integer month, Integer year){
        if(!isValidMonth(month)){
            month = preprocessMonth(month);
        }
        Calendar c = DateUtils.convString2Calendar(month+"/"+getDaysInMonth(transformMonth(month))+"/"+year);
        return c;
    }
	

	
}
