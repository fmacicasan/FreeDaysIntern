package freedays.app;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;

import freedays.app.FreeDay.FreeDayStatus;
import freedays.domain.ApplicationRegularUser;
import freedays.domain.Request;

@RooIntegrationTest(entity = FreeDayR.class)
public class FreeDayRIntegrationTest {

	private Request requestR;
	private ApplicationRegularUser leveltop;
	private ApplicationRegularUser level1;
	private ApplicationRegularUser appreguser;
	
	@Before
	public void setUp(){
		Request.DEBUG = true;
		
		RequestStatus status = RequestStatus.values()[0];
		
		FreeDayR requestableR = FreeDayRDataOnDemand.getenrateFreeDayR();
		requestR = new Request();
		requestR.setStatus(status);
		requestR.setRequestable(requestableR);
		
		
		leveltop = FDUserDataOnDemand.generateFDUser(1);
		level1 = FDUserDataOnDemand.generateFDUser(2,leveltop);
		appreguser = FDUserDataOnDemand.generateFDUser(3,level1);
		
	}
	
	@After
	public void tearDown(){
		Request.DEBUG = false;
	}
	
	@Test
	public void testApproveNormalUser(){
		requestR.setAppreguser(appreguser);
		requestR.init();
		requestR.approve();
		requestR.approve();
    	Assert.assertEquals("normal level - waiting for approval after one step", FreeDayStatus.WAITING, requestR.getRequestable().getStatus());
	}
}
