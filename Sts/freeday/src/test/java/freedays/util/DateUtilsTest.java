package freedays.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@Transactional
public class DateUtilsTest {
	
	private static Logger log = Logger.getLogger(DateUtilsTest.class);

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
		//Calendar businessDay = DateUtils.generateFutureBusinessDay();
		Calendar businessDay = DateUtils.generateBusinessDay();
		Assert.assertTrue("invalid business day generation",ValidationUtils.checkBusinessDay(businessDay));
	}
	
	@Test
	public void testWeekendDay(){
		//Calendar weekendDay = DateUtils.generateFutureWeekendDay();
		Calendar weekendDay = DateUtils.generateWeekendDay();
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
	//@Repeat(100)
	public void testDateDifferenceInDays(){
//		for(int i=100;i>0;i--){
		int span = new java.util.Random().nextInt(30)+1;
		Calendar start = Calendar.getInstance();
		//start.set(Calendar.DAY_OF_YEAR, 1);
		Calendar end = (Calendar) start.clone();
		log.debug("end "+DateUtils.printShortDate(end));
		log.debug("start "+DateUtils.printShortDate(start));
		end.add(Calendar.DAY_OF_YEAR, span);
		log.debug("end "+DateUtils.printShortDate(end));
		log.debug("span "+span);
		log.debug("start - end date diff"+DateUtils.dateDifferenceInDays(start, end));
		Assert.assertEquals("invalid date difference implementation", span, DateUtils.dateDifferenceInDays(start, end));
//		}
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
			
			//Integer limit = new java.util.Random().nextInt(100)+7;
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
		Calendar date = DateUtils.convString2Calendar("12/12/1989");
		Integer i = new java.util.Random().nextInt(10)+1;
		Calendar then = DateUtils.dateAddRomanianBusinessDay(date,i.longValue());
		Assert.assertEquals(i.longValue(), DateUtils.dateDifferenceInWorkingDays(date, then).longValue());
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
		Assert.assertEquals(String.format("error for the month %d", month),DateUtils.getDaysInMonth(month), DateUtils.getShortDateList(month).size());
	}
	
	@Test
	public void testGetDay(){
		Calendar c = Calendar.getInstance();
		Assert.assertEquals(c.get(Calendar.DAY_OF_MONTH), DateUtils.getDay(c));
	}
	
	@Test
	public void testGetWeekdayInitList(){
		int month = new java.util.Random().nextInt(Calendar.DECEMBER)+Calendar.JANUARY;
		Assert.assertEquals(String.format("error for the month %d", month),DateUtils.getDaysInMonth(month), DateUtils.getWeekdayInitialsList(month).size());
	}
	
	@Test
	public void testIsSameMonth(){
		Calendar c = Calendar.getInstance();
		Integer cm = DateUtils.getCurrentMonth();
		Assert.assertTrue(DateUtils.isValidMonth(cm));
		Assert.assertFalse(DateUtils.isSameMonth(c, cm));
		Assert.assertTrue(DateUtils.isSameMonth(c, DateUtils.transformMonth(cm)));
	}
	
	@Test
	public void testRomanianBusinessDayDifference(){
		Calendar c = new GregorianCalendar(2011,Calendar.AUGUST,1);
		Calendar cc = new GregorianCalendar(2011,Calendar.AUGUST,DateUtils.getDaysInMonth(Calendar.AUGUST));
		//15 august is a holiday
		Assert.assertEquals("not ok romanian date diference"+DateUtils.printShortDate(c)+" "+DateUtils.printShortDate(cc),DateUtils.dateDifferenceInWorkingDays(c, cc).longValue()+1, DateUtils.dateDifferenceInBusinessDays(c, cc).longValue());	
	}
	
	@Test
	public void testRomanianBusinessDayAdd(){
		int year = 2011;
		Calendar c = new GregorianCalendar(year,Calendar.AUGUST,1);
		Calendar cc = new GregorianCalendar(year,Calendar.AUGUST,DateUtils.getDaysInMonth(Calendar.AUGUST));
		long dd = DateUtils.dateDifferenceInWorkingDays(c, cc);
		Assert.assertEquals("not ok romanian date add",cc,DateUtils.dateAddRomanianBusinessDay(c,dd) );
	}
	
	@Test
	@Repeat(10)
	public void testRomanianBusinessDayAddRandom(){
		int year = 2011;//restrict test for year 2011, when 15 august was wendesday without other adjacent romanian legal days
		Calendar start = generateRandomRomanianLegal(15,Calendar.AUGUST,year,10,true);
		Calendar end = generateRandomRomanianLegal(15,Calendar.AUGUST,year,10,false);
		Assert.assertEquals("not ok romanian date diference"+DateUtils.printShortDate(start)+" "+DateUtils.printShortDate(end),
		        DateUtils.dateDifferenceInWorkingDays(start, end).longValue()+1, 
		        DateUtils.dateDifferenceInBusinessDays(start, end).longValue());
		long dd = DateUtils.dateDifferenceInWorkingDays(start, end);
		Assert.assertEquals("not ok romanian date add"+DateUtils.printShortDate(start)+" "+DateUtils.printShortDate(end),end,DateUtils.dateAddRomanianBusinessDay(start,dd) );
	}
	
	private Calendar generateRandomRomanianLegal(int day, int month, int year,int ss, boolean down){
		int nr = new java.util.Random().nextInt(ss)+1;
		if(down)nr=nr*(-1);
		Calendar c = new GregorianCalendar(year,month,day+nr);
		while(!ValidationUtils.checkBusinessDay(c)){
			nr = new java.util.Random().nextInt(ss)+1;
			if(down)nr=nr*(-1);
			c = new GregorianCalendar(year,month,day+nr);
		}
		return c;
	}
	
	@Test
	public void testCrazyAdd(){
		Calendar start = new GregorianCalendar(2011,Calendar.AUGUST,8);
		Calendar end = new GregorianCalendar(2011, Calendar.AUGUST,19);
		Assert.assertEquals(8, DateUtils.dateDifferenceInWorkingDays(start, end).longValue());
		Assert.assertEquals(end, DateUtils.dateAddRomanianBusinessDay(start, 8l));
	}
	
	@Test
	public void testCrazyAdd2(){
		Calendar start = new GregorianCalendar(2011,Calendar.AUGUST,8);
		Calendar end = new GregorianCalendar(2011, Calendar.AUGUST,18);
		Assert.assertEquals(7, DateUtils.dateDifferenceInWorkingDays(start, end).longValue());
		Assert.assertEquals(end, DateUtils.dateAddRomanianBusinessDay(start, 7l));
	}
	
	@Test
	public void testDayEquality(){
	    Calendar start = Calendar.getInstance();
	    Calendar end = Calendar.getInstance();
	    Assert.assertTrue(DateUtils.isDayEqual(start, end));
	}
	
	@Test
	public void testDayNotEqualYear(){
	    Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.YEAR, 1);
        Assert.assertFalse(DateUtils.isDayEqual(start, end));
	}
	
	@Test
    public void testDayNotEqualMonth(){
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.MONTH, 1);
        Assert.assertFalse(DateUtils.isDayEqual(start, end));
    }
	
	@Test
    public void testDayNotEqualDay(){
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        end.add(Calendar.DAY_OF_MONTH, 1);
        Assert.assertFalse(DateUtils.isDayEqual(start, end));
    }
	
	@Test
	public void testDateDifferenceInWokringDaysSameDay(){
	    String day = "12/12/2012";
        Calendar c = DateUtils.convString2Calendar(day);
        Assert.assertEquals(0, DateUtils.dateDifferenceInWorkingDays(c, c).intValue());
	}
	
	@Test
    public void testDateDifferenceInWokringDaysConsecutive(){
        String day = "12/12/2012";
        Calendar c1 = DateUtils.convString2Calendar(day);
        String day2 = "12/13/2012";
        Calendar c2 = DateUtils.convString2Calendar(day2);
        Assert.assertEquals(1, DateUtils.dateDifferenceInWorkingDays(c1, c2).intValue());
    }
	
	@Test
    public void testDateDifferenceInWokringDaysWeekendInBetween(){
        String day = "12/12/2012";
        Calendar c1 = DateUtils.convString2Calendar(day);
        String day2 = "12/19/2012";
        Calendar c2 = DateUtils.convString2Calendar(day2);
        Assert.assertEquals(5, DateUtils.dateDifferenceInWorkingDays(c1, c2).intValue());
    }
	
	@Test
    public void testDateDifferenceInWokringDaysRomanianLegalDayInBetween(){
        String day = "11/29/2012";
        Calendar c1 = DateUtils.convString2Calendar(day);
        String day2 = "12/03/2012";
        Calendar c2 = DateUtils.convString2Calendar(day2);
        Assert.assertEquals(1, DateUtils.dateDifferenceInWorkingDays(c1, c2).intValue());
    }
	
	@Test
    public void testDateDifferenceInWokringDaysRomanianLegalDayStart(){
        String day = "11/30/2012";
        Calendar c1 = DateUtils.convString2Calendar(day);
        String day2 = "12/03/2012";
        Calendar c2 = DateUtils.convString2Calendar(day2);
        Assert.assertEquals(1, DateUtils.dateDifferenceInWorkingDays(c1, c2).intValue());
    }
	
	@Test
    public void testDateDifferenceInWokringDaysRomanianWeekendStart(){
        String day = "12/01/2012";
        Calendar c1 = DateUtils.convString2Calendar(day);
        String day2 = "12/03/2012";
        Calendar c2 = DateUtils.convString2Calendar(day2);
        Assert.assertEquals(1, DateUtils.dateDifferenceInWorkingDays(c1, c2).intValue());
    }
	
	
	@Test
    public void testDateDifferenceInWokringDaysIncludingEndsSameDay(){
        String day = "12/12/2012";
        Calendar c = DateUtils.convString2Calendar(day);
        Assert.assertEquals(1, DateUtils.dateDifferenceInWorkingDaysIncludingEnds(c, c).intValue());
    }
    
    @Test
    public void testDateDifferenceInWokringDaysIncludingEndsConsecutive(){
        String day = "12/12/2012";
        Calendar c1 = DateUtils.convString2Calendar(day);
        String day2 = "12/13/2012";
        Calendar c2 = DateUtils.convString2Calendar(day2);
        Assert.assertEquals(2, DateUtils.dateDifferenceInWorkingDaysIncludingEnds(c1, c2).intValue());
    }
    
    @Test
    public void testDateDifferenceInWokringDaysWeekendIncludingEndsInBetween(){
        String day = "12/12/2012";
        Calendar c1 = DateUtils.convString2Calendar(day);
        String day2 = "12/19/2012";
        Calendar c2 = DateUtils.convString2Calendar(day2);
        Assert.assertEquals(6, DateUtils.dateDifferenceInWorkingDaysIncludingEnds(c1, c2).intValue());
    }
    
    @Test
    public void testDateDifferenceInWokringDaysRomanianLegalIncludingEndsDayInBetween(){
        String day = "11/29/2012";
        Calendar c1 = DateUtils.convString2Calendar(day);
        String day2 = "12/03/2012";
        Calendar c2 = DateUtils.convString2Calendar(day2);
        Assert.assertEquals(2, DateUtils.dateDifferenceInWorkingDaysIncludingEnds(c1, c2).intValue());
    }
    
    @Test
    public void testDateDifferenceInWokringDaysRomanianIncludingEndsLegalDayStart(){
        String day = "11/30/2012";
        Calendar c1 = DateUtils.convString2Calendar(day);
        String day2 = "12/03/2012";
        Calendar c2 = DateUtils.convString2Calendar(day2);
        Assert.assertEquals(1, DateUtils.dateDifferenceInWorkingDaysIncludingEnds(c1, c2).intValue());
    }
    
    @Test
    public void testDateDifferenceInWokringDaysRomanianIncludingEndsWeekendStart(){
        String day = "12/01/2012";
        Calendar c1 = DateUtils.convString2Calendar(day);
        String day2 = "12/03/2012";
        Calendar c2 = DateUtils.convString2Calendar(day2);
        Assert.assertEquals(1, DateUtils.dateDifferenceInWorkingDaysIncludingEnds(c1, c2).intValue());
    }
    
    @Test
    public void testDateDifferenceInWokringDaysRomanianIncludingEndsNewYear(){
        String day = "01/01/2012";
        Calendar c1 = DateUtils.convString2Calendar(day);
        String day2 = "01/04/2012";
        Calendar c2 = DateUtils.convString2Calendar(day2);
        Assert.assertEquals(2, DateUtils.dateDifferenceInWorkingDaysIncludingEnds(c1, c2).intValue());
    }
    
    private static final Integer MOCK_YEAR = 1989;
    private static final Integer MOCK_MONTH = 12;
    @Test
    public void testCreateLastDayOfMonthNormalMonth(){
        Calendar lastDay = DateUtils.convString2Calendar(MOCK_MONTH+"/31/"+MOCK_YEAR);
        
        Calendar supposedLastDay = DateUtils.createLastDayOfMonth(MOCK_MONTH, MOCK_YEAR);
        
        Assert.assertTrue(DateUtils.isDayEqual(lastDay, supposedLastDay));
    }
    
    @Test
    public void testCreateFirstDayOfMonthNormalMonth(){
        Calendar lastDay = DateUtils.convString2Calendar(MOCK_MONTH+"/1/"+MOCK_YEAR);
        
        Calendar supposedLastDay = DateUtils.createFirstDayOfMonth(MOCK_MONTH, MOCK_YEAR);
        
        Assert.assertTrue(DateUtils.isDayEqual(lastDay, supposedLastDay));
    }
    
    @Test
    public void testIsDateBetweenInside(){
        Calendar dateInside = DateUtils.convString2Calendar("12/13/1989");
        Calendar dateLeft = DateUtils.convString2Calendar("12/11/1989");
        Calendar dateRight = DateUtils.convString2Calendar("12/14/1989");
        
        boolean result = DateUtils.isDateBetween(dateInside, dateLeft, dateRight);
        
        Assert.assertTrue(result);
    }
    
    @Test
    public void testIsDateBetweenOutside(){
        Calendar dateInside = DateUtils.convString2Calendar("12/10/1989");
        Calendar dateLeft = DateUtils.convString2Calendar("12/11/1989");
        Calendar dateRight = DateUtils.convString2Calendar("12/14/1989");
        
        boolean result = DateUtils.isDateBetween(dateInside, dateLeft, dateRight);
        
        Assert.assertFalse(result);
    }
    
    @Test
    public void testIsDateBetweenEdge(){
        Calendar dateInside = DateUtils.convString2Calendar("12/11/1989");
        Calendar dateLeft = DateUtils.convString2Calendar("12/11/1989");
        Calendar dateRight = DateUtils.convString2Calendar("12/14/1989");
        
        boolean result = DateUtils.isDateBetween(dateInside, dateLeft, dateRight);
        
        Assert.assertTrue(result);
    }
    
    @Test
    public void testIsIntervalOverlapFilterFullInside() {
        Calendar start = DateUtils.convString2Calendar("12/10/1989");
        Calendar end = DateUtils.convString2Calendar("12/12/1989");
        Calendar filterleft = DateUtils.convString2Calendar("12/09/1989");
        Calendar filterRight = DateUtils.convString2Calendar("12/13/1989");
        
        boolean result = DateUtils.isIntervalOverlapFilter(start, end, filterleft, filterRight);
        
        Assert.assertTrue(result);
    }
    
    @Test
    public void testIsIntervalOverlapFilterOverlapLeft() {
        Calendar start = DateUtils.convString2Calendar("12/07/1989");
        Calendar end = DateUtils.convString2Calendar("12/12/1989");
        Calendar filterleft = DateUtils.convString2Calendar("12/09/1989");
        Calendar filterRight = DateUtils.convString2Calendar("12/13/1989");
        
        boolean result = DateUtils.isIntervalOverlapFilter(start, end, filterleft, filterRight);
        
        Assert.assertTrue(result);
    }
    
    @Test
    public void testIsIntervalOverlapFilterOverlapRight() {
        Calendar start = DateUtils.convString2Calendar("12/10/1989");
        Calendar end = DateUtils.convString2Calendar("12/15/1989");
        Calendar filterleft = DateUtils.convString2Calendar("12/09/1989");
        Calendar filterRight = DateUtils.convString2Calendar("12/13/1989");
        
        boolean result = DateUtils.isIntervalOverlapFilter(start, end, filterleft, filterRight);
        
        Assert.assertTrue(result);
    }
    
    @Test
    public void testIsIntervalOverlapFilterFullOutside() {
        Calendar start = DateUtils.convString2Calendar("12/08/1989");
        Calendar end = DateUtils.convString2Calendar("12/15/1989");
        Calendar filterleft = DateUtils.convString2Calendar("12/09/1989");
        Calendar filterRight = DateUtils.convString2Calendar("12/13/1989");
        
        boolean result = DateUtils.isIntervalOverlapFilter(start, end, filterleft, filterRight);
        
        Assert.assertTrue(result);
    }
    
    @Test
    public void testIsIntervalOverlapFilterDisjointLeft() {
        Calendar start = DateUtils.convString2Calendar("12/06/1989");
        Calendar end = DateUtils.convString2Calendar("12/07/1989");
        Calendar filterleft = DateUtils.convString2Calendar("12/09/1989");
        Calendar filterRight = DateUtils.convString2Calendar("12/13/1989");
        
        boolean result = DateUtils.isIntervalOverlapFilter(start, end, filterleft, filterRight);
        
        Assert.assertFalse(result);
    }
    
    @Test
    public void testIsIntervalOverlapFilterDisjointRight() {
        Calendar start = DateUtils.convString2Calendar("12/15/1989");
        Calendar end = DateUtils.convString2Calendar("12/16/1989");
        Calendar filterleft = DateUtils.convString2Calendar("12/09/1989");
        Calendar filterRight = DateUtils.convString2Calendar("12/13/1989");
        
        boolean result = DateUtils.isIntervalOverlapFilter(start, end, filterleft, filterRight);
        
        Assert.assertFalse(result);
    }
    
    @Test
    public void testIsIntervalOverlapFilterSame() {
        Calendar filterleft = DateUtils.convString2Calendar("12/09/1989");
        Calendar filterRight = DateUtils.convString2Calendar("12/13/1989");
        
        boolean result = DateUtils.isIntervalOverlapFilter(filterleft, filterRight, filterleft, filterRight);
        
        Assert.assertTrue(result);
    }
    
    @Test
    public void testIsIntervalOverlapFilterOverlap1Day() {
        Calendar start = DateUtils.convString2Calendar("12/08/1989");
        Calendar end = DateUtils.convString2Calendar("12/15/1989");
        Calendar filterleft = DateUtils.convString2Calendar("12/15/1989");
        Calendar filterRight = DateUtils.convString2Calendar("12/16/1989");
        
        boolean result = DateUtils.isIntervalOverlapFilter(start, end, filterleft, filterRight);
        
        Assert.assertTrue(result);
    }
    
    
    
    
	
}
