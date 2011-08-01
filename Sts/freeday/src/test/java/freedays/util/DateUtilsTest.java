package freedays.util;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

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
}
