package freedays.app;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;

import freedays.app.FreeDay.FreeDayStatus;
import freedays.domain.ApplicationRegularUser;
import freedays.domain.Request;

@RooIntegrationTest(entity = FreeDayC.class)
public class FreeDayCIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }
    
	private Request requestC;
	private Request requestR;
	private ApplicationRegularUser leveltop;
	private ApplicationRegularUser level1;
	private ApplicationRegularUser appreguser;
	
	@Before
	public void setUp(){
		Request.DEBUG = true;
		
		RequestStatus status = RequestStatus.values()[0];
		
		FreeDayCDataOnDemand fdcdod = new FreeDayCDataOnDemand();
		FreeDayC requestableC = fdcdod.getRandomFreeDayC();
		while(requestableC.getMatch()!=null){
			requestableC = fdcdod.getRandomFreeDayC();
		}
		requestC = new Request();
		requestC.setStatus(status);
		requestC.setRequestable(requestableC);
		
		FreeDayRDataOnDemand fdrdod = new FreeDayRDataOnDemand();
		FreeDayR requestableR = fdrdod.getRandomFreeDayR();
		while(requestableR.getMatch()!=null){
			requestableR = fdrdod.getRandomFreeDayR();
		}
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
		requestC.setAppreguser(appreguser);
    	requestC.init();
    	requestC.approve();
    	requestC.approve();
    	Assert.assertEquals("normal level - waiting for approval after one step", FreeDayStatus.WAITING, requestC.getRequestable().getStatus());
	}
	
	@Test
	public void testMergeNormalUser(){
		requestC.setAppreguser(appreguser);
		requestC.init();
		requestC.approve();
		requestC.approve();
		Assert.assertEquals("normal level - waiting for approval after one step", RequestStatus.GRANTED, requestC.getStatus());
		Assert.assertEquals("normal level check match - matching status on complete", FreeDayStatus.WAITING,requestC.getRequestable().getStatus());
		
		requestR.setAppreguser(appreguser);
		requestR.init();
		requestR.approve();
		requestR.approve();
		Assert.assertEquals("normal level - waiting for approval after one step", RequestStatus.GRANTED, requestR.getStatus());
		Assert.assertEquals("normal level check match - matching status on complete", FreeDayStatus.WAITING,requestR.getRequestable().getStatus());
		
		
		Assert.assertTrue("normal level check class", requestC.getRequestable() instanceof FreeDayC);
		FreeDayC fdc = (FreeDayC) requestC.getRequestable();
		Assert.assertNull("normal level merge requests - not null merge object",fdc.getMatch());
		
		Assert.assertTrue("normal level check class", requestR.getRequestable() instanceof FreeDayR);
		FreeDayR fdr = (FreeDayR) requestR.getRequestable();
		Assert.assertNull("normal level merge requests - not null merge object",fdr.getMatch());
		
		Assert.assertTrue("normal level merge request - problem at match",fdc.match(fdr));
		fdc.persist();
		fdr.persist();
		Assert.assertNotNull("normal level check match - remained null fdc",fdc.getMatch());
		Assert.assertNotNull("normal level check match - remained null fdr",fdr.getMatch());
		Assert.assertSame("normal level check match - matching failed fdr",  fdc,fdr.getMatch());
		Assert.assertSame("normal level check match - matching failed fdc",  fdr,fdc.getMatch());
		Assert.assertTrue("normal level merge request - duplicate match possible check cond failed",!fdr.match(fdc));
		Assert.assertEquals("normal level check match - matching status on complete fdr", FreeDayStatus.COMPLETED_SUCCESS,fdr.getStatus());
		Assert.assertEquals("normal level check match - matching status on complete fdc", FreeDayStatus.COMPLETED_SUCCESS,fdc.getStatus());
		
		FreeDayR testfdr = FreeDayR.findFreeDayR(fdr.getId());
		Assert.assertNotNull("normal level check find - persist failed for free day", testfdr);
		
		Assert.assertNotNull("normal level check existance - retrieve failed for free day match",testfdr.getMatch());
		
		FreeDayC testfdc = FreeDayC.findFreeDayC(fdc.getId());
		Assert.assertNotNull("normal level check find - persist failed for free day", testfdc);
		
		Assert.assertNotNull("normal level check existance - retrieve failed for free day match",testfdc.getMatch());
		
	}
	

	
	
	
	
}
