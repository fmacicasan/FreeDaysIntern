package freedays.util;

import java.util.Calendar;

import javax.validation.Valid;
import javax.validation.constraints.Future;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import freedays.validation.annotation.BusinessDay;
import freedays.validation.annotation.Weekend;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@Transactional
public class DateUtilsTest {

	@Test
	public void testRandomBusinessDay(){
		for(int i=30;i>0;i--){
			int businessDay = DateUtils.generateRandomBusinessDayOfWeek();
			Assert.assertTrue("invalid business day generation - sunday", businessDay != Calendar.SUNDAY);
			Assert.assertTrue("invalid business day generation - saturday", businessDay != Calendar.SATURDAY);
		}
	}
	
	@Test
	public void testRandomWeekendDay(){
		int weekendDay = DateUtils.generateRandomWeekendDayOfWeek();
		Assert.assertTrue("invalid business day generation - sunday or saturday", weekendDay == Calendar.SUNDAY || weekendDay == Calendar.SATURDAY );
	}
	
	@Test
	public void testBusinessDay(){
		Calendar businessDay = DateUtils.generateFutureBusinessDay();
		Assert.assertTrue("invalid business day generation",ValidationUtils.checkBusinessDay(businessDay));
	}
	
	@Test
	public void testWeekendDay(){
		Calendar weekendDay = DateUtils.generateFutureWeekendDay();
		Assert.assertTrue("invalid weekend day generation",ValidationUtils.checkWeekend(weekendDay));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDateDifferenceInDaysNullStart(){
		DateUtils.dateDifferenceInDays(null, Calendar.getInstance());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDateDifferenceInDaysNullEnd(){
		DateUtils.dateDifferenceInDays(Calendar.getInstance(),null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDateDifferenceInDaysStartEqEnd(){
		Calendar now = Calendar.getInstance();
		DateUtils.dateDifferenceInDays(now,now);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDateDifferenceInDaysStartGtEnd(){
		Calendar now = Calendar.getInstance();
		now.set(Calendar.YEAR, now.get(Calendar.YEAR)-1);
		DateUtils.dateDifferenceInDays(Calendar.getInstance(),now);
	}
	
	@Test
	public void testDateDifferenceInDays(){
		for(int i=0;i<100;i++){
		int span = new java.util.Random().nextInt(365);
		Calendar start = Calendar.getInstance();
		//start.set(Calendar.DAY_OF_YEAR, 1);
		Calendar end = (Calendar) start.clone();
		//System.out.println(DateUtils.printShortDate(end));
		//System.out.println(DateUtils.printShortDate(start));
		end.add(Calendar.DAY_OF_YEAR, span);
		//System.out.println(DateUtils.printShortDate(end));
		//System.out.println(span);
		//System.out.println(DateUtils.dateDifferenceInDays(start, end));
		Assert.assertEquals("invalid date difference implementation", span, DateUtils.dateDifferenceInDays(start, end));
		}
	}
	
	@Test
	public void testDateAddDay(){
		Integer span = new java.util.Random().nextInt(365);
		Calendar start = Calendar.getInstance();
		Calendar end = (Calendar) start.clone();
		end.add(Calendar.DAY_OF_YEAR, span);
		Assert.assertEquals("invalid date add day implementation",end,DateUtils.dateAddDay(start, span.longValue()));
	}
	
}
