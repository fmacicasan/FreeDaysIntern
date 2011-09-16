package freedays.app;

import java.util.Calendar;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import freedays.domain.Request;
import freedays.util.DateUtils;
import freedays.util.ValidationUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@Transactional
public class FreeDayGeneralIntegrationTest {
	
	//testing the cancelation mechanism for any type of request
	private Request request;
	private RequestStatus currReqStat;
	private FreeDay fd;
	@Before
	public void setUp(){
		request = new Request();
//		request.setAppreguser(appreguser);
//		request.setApprover(approver);
//		request.setStatus(status);
//		request.setRequestable(requestable);
		fd = new FreeDayLDataOnDemand().getRandomFreeDayL();
		
		
		List<RequestStatus> lrs = RequestStatus.getPossibleActiveStatusList();
		lrs.add(RequestStatus.GRANTED);
		
		currReqStat = lrs.get(new java.util.Random().nextInt(lrs.size()));
	}
	
	@Test
	public void testCancelOnRequestRejected(){
		request.setRequestable(fd);
		request.setStatus(RequestStatus.REJECTED);
		Assert.assertFalse("cancelation if rejected", request.isCancelable());
	}
	
	@Test
	public void testCancelOnRequestCanceled(){
		request.setRequestable(fd);
		request.setStatus(RequestStatus.CANCELED);
		Assert.assertFalse("cancelation if canceled", request.isCancelable());
	}
	
	@Test
	public void testCancelOnRequestableFuture(){
		Calendar date = DateUtils.generateFutureBusinessDay();
		fd.setDate(date);
		Assert.assertTrue("free day cancelation if future", fd.isCancelable());
	}
	
	@Test
	public void testCancelOnRequestFuture(){
		Calendar date = DateUtils.generateFutureBusinessDay();
		fd.setDate(date);
		request.setRequestable(fd);
		request.setStatus(currReqStat);
		Assert.assertTrue("request cancelation if future",request.isCancelable());
	}
	
	@Test
	public void testCancelOnRequestablePast(){
		Calendar date = DateUtils.generatePastBusinessDay();
		fd.setDate(date);
		Assert.assertFalse("free day cancelation if past",fd.isCancelable());
	}
	
	@Test
	public void testCancelOnRequestPast(){
		Calendar date = DateUtils.generatePastBusinessDay();
		fd.setDate(date);
		request.setRequestable(fd);
		request.setStatus(currReqStat);
		Assert.assertFalse("request cancelation if past",request.isCancelable());
	}
	
	@Test
	public void testCancelOnRequestableNowBeforeDeadline(){
		Calendar date = Calendar.getInstance();
		if(ValidationUtils.checkBusinessDay(date) && date.get(Calendar.HOUR_OF_DAY) < FreeDay.DEFAULT_MAXIMUM_CANCELATION_HOUR){
			fd.setDate(date);
			Assert.assertTrue("free day cancelation if now before deadline",fd.isCancelable());
		}
	}
	
	@Test
	public void testCancelOnRequestNowBeforeDeadline(){
		Calendar date = Calendar.getInstance();
		if(ValidationUtils.checkBusinessDay(date) && date.get(Calendar.HOUR_OF_DAY) < FreeDay.DEFAULT_MAXIMUM_CANCELATION_HOUR){
			fd.setDate(date);
			request.setRequestable(fd);
			request.setStatus(currReqStat);
			Assert.assertTrue("request cancelation if now and before deadline",request.isCancelable());
		}
	}
	
	@Test
	public void testCancelOnRequestableNowAfterDeadline(){
		Calendar date = Calendar.getInstance();
		if(ValidationUtils.checkBusinessDay(date) && date.get(Calendar.HOUR_OF_DAY) > FreeDay.DEFAULT_MAXIMUM_CANCELATION_HOUR){
			fd.setDate(date);
			Assert.assertFalse("free day cancelation if now after deadline", fd.isCancelable());
		}
	}
	
	@Test
	public void testCancelOnRequestNowAfterDeadline(){
		Calendar date = Calendar.getInstance();
		if(ValidationUtils.checkBusinessDay(date) && date.get(Calendar.HOUR_OF_DAY) > FreeDay.DEFAULT_MAXIMUM_CANCELATION_HOUR){
			fd.setDate(date);
			request.setRequestable(fd);
			request.setStatus(currReqStat);
			Assert.assertFalse("requst cancelation if now after deadline",request.isCancelable());
		}
	}
	
	
	

}
