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
		for(int i=100;i>0;i--){
		int span = new java.util.Random().nextInt(364)+1;
		Calendar start = Calendar.getInstance();
		//start.set(Calendar.DAY_OF_YEAR, 1);
		Calendar end = (Calendar) start.clone();
		System.out.println(DateUtils.printShortDate(end));
		System.out.println(DateUtils.printShortDate(start));
		end.add(Calendar.DAY_OF_YEAR, span);
		System.out.println(DateUtils.printShortDate(end));
		System.out.println(span);
		System.out.println(DateUtils.dateDifferenceInDays(start, end));
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
	
	@Test
	public void testAddBusinessDay(){
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		//date.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY+5);
		//Assert.assertEquals(String.format("i=%d j=%d",2,0),4, DateUtils.dateAddBusinessDay(date, 0L).get(Calendar.DAY_OF_WEEK));
		
		for(Integer i=0;i<7;i++){
			
			Integer limit = new java.util.Random().nextInt(100)+7;
			Integer test=Calendar.MONDAY + i;
			for(Integer j=0;j<2;j++){
				 if(test >= Calendar.SATURDAY)test = Calendar.MONDAY;
				 Assert.assertEquals(String.format("i=%d j=%d day=%d",i,j,date.get(Calendar.DAY_OF_WEEK)),test.longValue(), DateUtils.dateAddBusinessDay(date, j.longValue()).get(Calendar.DAY_OF_WEEK));
				 test++;
			}
			date.add(Calendar.DAY_OF_YEAR, 1);
		}			
	}
	
	@Test
	public void testDateDifferenceInWorkingDays(){
		Calendar date = Calendar.getInstance();
		Integer i = new java.util.Random().nextInt(100);
		Calendar then = DateUtils.dateAddBusinessDay(date,i.longValue());
		Assert.assertEquals(i.longValue(), DateUtils.dateDifferenceInWorkingDays(date, then));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConvString2CalendarFail(){
		DateUtils.convString2Calendar("12\124\233");
	}
	@Test
	public void testConvString2CalendarSuccess(){
		String day = "12/12/1989";
		Calendar c = DateUtils.convString2Calendar(day);
		Assert.assertEquals(12, c.get(Calendar.DAY_OF_MONTH));
		Assert.assertEquals(Calendar.DECEMBER, c.get(Calendar.MONTH));
		Assert.assertEquals(1989, c.get(Calendar.YEAR));
		
	}
	
	@Test
	public void testGetShortDaysList(){
		int month = new java.util.Random().nextInt(Calendar.DECEMBER)+Calendar.JANUARY;
		Assert.assertEquals(DateUtils.getDaysInMonth(month), DateUtils.getShortDateList(month).size());
	}
	
	@Test
	public void testGetDay(){
		Calendar c = Calendar.getInstance();
		Assert.assertEquals(c.get(Calendar.DAY_OF_MONTH), DateUtils.getDay(c));
	}
	
	@Test
	public void testGetWeekdayInitList(){
		int month = new java.util.Random().nextInt(Calendar.DECEMBER)+Calendar.JANUARY;
		Assert.assertEquals(DateUtils.getDaysInMonth(month), DateUtils.getWeekdayInitialsList(month).size());
	}
	
	@Test
	public void testIsSameMonth(){
		Calendar c = Calendar.getInstance();
		Integer cm = DateUtils.getCurrentMonth();
		Assert.assertTrue(DateUtils.isValidMonth(cm));
		Assert.assertFalse(DateUtils.isSameMonth(c, cm));
		Assert.assertTrue(DateUtils.isSameMonth(c, DateUtils.transformMonth(cm)));
	}
	
}
