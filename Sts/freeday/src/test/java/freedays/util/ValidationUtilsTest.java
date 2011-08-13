package freedays.util;

import java.util.Calendar;

import javassist.bytecode.Descriptor.Iterator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.mockito.Mockito;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@Transactional
public class ValidationUtilsTest {
	
	@Test
	public void testCheckBusinessDayFail(){
		Calendar date = DateUtils.generateFutureWeekendDay();
		Assert.assertFalse("validation util - business day fail problem", ValidationUtils.checkBusinessDay(date));
	}
	
	@Test
	public void testCheckBusinessDayPass(){
		Calendar date = DateUtils.generateFutureBusinessDay();
		Assert.assertTrue("validation util - business day ok problem", ValidationUtils.checkBusinessDay(date));
	}
	
	@Test
	public void testCheckWeekendFail(){
		Calendar date = DateUtils.generateFutureBusinessDay();
		Assert.assertFalse("validation util - weekend day fail problem", ValidationUtils.checkWeekend(date));
	}
	
	@Test
	public void testCheckWeekendPass(){
		Calendar date = DateUtils.generateFutureWeekendDay();
		Assert.assertTrue("validation util - weekend day pass problem", ValidationUtils.checkWeekend(date));
	}
	
	@Test
	public void testMockitoExample(){
		java.util.Iterator it = Mockito.mock(java.util.Iterator.class);
		
		Mockito.when(it.next()).thenReturn("Hello").thenReturn("World");
		String result = it.next()+" "+it.next();
		Assert.assertEquals("Hello World",result);
		
	}

}
