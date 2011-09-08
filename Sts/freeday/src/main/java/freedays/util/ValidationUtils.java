package freedays.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

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
	
	public static final Calendar findHolyDay(int year) {
	    if (year <= 1582) {
	      throw new IllegalArgumentException(
	          "Algorithm invalid before April 1583");
	    }
	    //compute western easter
//	    int golden, century, x, z, d, epact, n;
//
//	    golden = (year % 19) + 1; /* E1: metonic cycle */
//	    century = (year / 100) + 1; /* E2: e.g. 1984 was in 20th C */
//	    x = (3 * century / 4) - 12; /* E3: leap year correction */
//	    z = ((8 * century + 5) / 25) - 5; /* E3: sync with moon's orbit */
//	    d = (5 * year / 4) - x - 10;
//	    epact = (11 * golden + 20 + z - x) % 30; /* E5: epact */
//	    if ((epact == 25 && golden > 11) || epact == 24)
//	      epact++;
//	    n = 44 - epact;
//	    n += 30 * (n < 21 ? 1 : 0); /* E6: */
//	    n += 7 - ((d + n) % 7);
//	    if (n > 31) /* E7: */
//	      return new GregorianCalendar(year, 4 - 1, n - 31); /* April */
//	    else
//	      return new GregorianCalendar(year, 3 - 1, n); /* March */
	    
	    //compute eastern easter algorithm wikipedia http://ro.wikipedia.org/wiki/Calculul_datei_de_Pa%C8%99te
	    int a = year % 4;
	    int b = year % 7;
	    int c = year % 19;
	    int d = (19*c+15)%30;
	    int e = (2*a+4*b-d+34)%7;
	    int month = (d+e+114)/31;
	    int day = (d+e+114)%31+1;
	    return new GregorianCalendar(year,month-1,day+13); 
	  }
//	
//	public static void main(String[] args){
//		int start = 1980;
//		int end = 2020;
//		for(int i=start;i<end;i++){
//			System.out.println(String.format("year:%1$d-easter:%2$tY-%2$tm-%2$td",i,findHolyDay(i)));
//		}
//	}
}
