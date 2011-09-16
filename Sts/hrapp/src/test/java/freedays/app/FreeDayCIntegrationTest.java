package freedays.app;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;

import freedays.app.FreeDay.FreeDayStatus;
import freedays.app.form.FreeDayRequest;
import freedays.app.form.FreeDayRequest.RequestType;
import freedays.domain.ApplicationRegularUser;
import freedays.domain.Request;
import freedays.util.DateUtils;

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
	public void testMergeNormalUserMechanimsLowLevel(){
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
		fdc.setMatch(fdr);
		Assert.assertTrue("normal level merge request - problem at match",fdc.match(fdr));
		fdc.setStatus(FreeDayStatus.FINALIZE_SUCCESS);
		fdc.persist();
		fdr.persist();
		Assert.assertNotNull("normal level check match - remained null fdc",fdc.getMatch());
		Assert.assertNotNull("normal level check match - remained null fdr",fdr.getMatch());
		Assert.assertSame("normal level check match - matching failed fdr",  fdc,fdr.getMatch());
		Assert.assertSame("normal level check match - matching failed fdc",  fdr,fdc.getMatch());
		Assert.assertTrue("normal level merge request - duplicate match possible check cond failed",!fdr.match(fdc));
		Assert.assertEquals("normal level check match - matching status on complete fdr", FreeDayStatus.FINALIZE_SUCCESS,fdr.getStatus());
		Assert.assertEquals("normal level check match - matching status on complete fdc", FreeDayStatus.FINALIZE_SUCCESS,fdc.getStatus());
		
		FreeDayR testfdr = FreeDayR.findFreeDayR(fdr.getId());
		Assert.assertNotNull("normal level check find - persist failed for free day", testfdr);
		
		Assert.assertNotNull("normal level check existance - retrieve failed for free day match",testfdr.getMatch());
		
		FreeDayC testfdc = FreeDayC.findFreeDayC(fdc.getId());
		Assert.assertNotNull("normal level check find - persist failed for free day", testfdc);
		
		Assert.assertNotNull("normal level check existance - retrieve failed for free day match",testfdc.getMatch());
		
	}
	
	
	@Test
	public void testRequestDayMechanismHighLevel(){
		
		FDUser fdu = new FDUserDataOnDemand().getRandomNormalUser();
		if(fdu != null){
			String username =  fdu.getRegularUser().getUsername();
			
			
			FreeDayRequest fdc = new FreeDayRequest();
			fdc.setReqtype(RequestType.C);
			fdc.setMatch(null);
			fdc.setReason(null);
//			fdc.setReqdate(DateUtils.generateFutureBusinessDay());
			fdc.setReqdate(DateUtils.generateBusinessDay());
			
			long beforeC = Request.countActiveRequests(username);
			Request reqC = Request.createPersistentReq(fdc,username);
			long afterC = Request.countActiveRequests(username);
			Assert.assertEquals("request day approve - not persist",beforeC+1, afterC);
			
			Assert.assertEquals("request day approve - fail @ req status before approve 1",RequestStatus.PENDING, reqC.getStatus());
			Assert.assertEquals("request day approve - fail @ free day status before approve 1",FreeDayStatus.IN_PROGRESS, reqC.getRequestable().getStatus());
			reqC.approve();
			Assert.assertEquals("request day approve - fail @ req status before approve 2",RequestStatus.INTERMEDIATE, reqC.getStatus());
			Assert.assertEquals("request day approve - fail @ free day status before approve 2",FreeDayStatus.IN_PROGRESS, reqC.getRequestable().getStatus());
			reqC.approve();
			Assert.assertEquals("request day approve - fail @ req status after full approve",RequestStatus.GRANTED, reqC.getStatus());
			Assert.assertEquals("request day approve - fail @ free day status after full approve",FreeDayStatus.WAITING, reqC.getRequestable().getStatus());
			
			Assert.assertTrue("request day - check free day generated", reqC.getRequestable() instanceof FreeDayC);
			FreeDayC freedc = (FreeDayC)reqC.getRequestable();
			
			FreeDayRequest fdr = new FreeDayRequest();
			fdr.setReqtype(RequestType.R);
			fdr.setMatch(freedc);
			fdr.setReason(null);
//			fdr.setReqdate(DateUtils.generateFutureWeekendDay());
			fdr.setReqdate(DateUtils.generateWeekendDay());
			
			Assert.assertEquals("request day approve - fail @ free day status after full approval",FreeDayStatus.WAITING, freedc.getStatus());
			
			beforeC = Request.countActiveRequests(username);
			Request reqR = Request.createPersistentReq(fdr, username);
			afterC = Request.countActiveRequests(username);
			Assert.assertEquals("request day - not persist",beforeC+1, afterC);
			
			Assert.assertEquals("request day approve - fail @ free day status before approve 1",FreeDayStatus.IN_PROGRESS, freedc.getStatus());
			Assert.assertEquals("request day approve - fail @ req status before approve 1",RequestStatus.PENDING, reqR.getStatus());
			Assert.assertEquals("request day approve - fail @ free day match status before approve 1",FreeDayStatus.IN_PROGRESS, reqR.getRequestable().getStatus());
			
			reqR.approve();
			Assert.assertEquals("request day approve - fail @ free day status after approve 1",FreeDayStatus.IN_PROGRESS, freedc.getStatus());
			Assert.assertEquals("request day approve - fail @ req status after approve 1",RequestStatus.INTERMEDIATE, reqR.getStatus());
			Assert.assertEquals("request day approve - fail @ free day match status after approve 1",FreeDayStatus.IN_PROGRESS, reqR.getRequestable().getStatus());
			
			reqR.deny();
			Assert.assertEquals("request day approve - fail @ free day status after deny",FreeDayStatus.WAITING, freedc.getStatus());
			Assert.assertEquals("request day approve - fail @ req status after deny",RequestStatus.REJECTED, reqR.getStatus());
			Assert.assertEquals("request day approve - fail @ free day status after deny",FreeDayStatus.COMPLETED_FAILURE, reqR.getRequestable().getStatus());
			
			Request reqR2 = Request.createPersistentReq(fdr, username);
			reqR2.cancel();
			Assert.assertEquals("request day approve - fail @ free day status after cancel",FreeDayStatus.WAITING, freedc.getStatus());
			Assert.assertEquals("request day approve - fail @ req status after cancel",RequestStatus.CANCELED, reqR2.getStatus());
			Assert.assertEquals("request day approve - fail @ free day status after cancel",FreeDayStatus.COMPLETED_FAILURE, reqR2.getRequestable().getStatus());
			
			Request reqR3 = Request.createPersistentReq(fdr, username);
			reqR3.approve();
			reqR3.approve();
			Assert.assertEquals("request day approve - fail @ free day status after full approve",FreeDayStatus.FINALIZE_SUCCESS, freedc.getStatus());
			Assert.assertEquals("request day approve - fail @ req status after full approve",RequestStatus.GRANTED, reqR3.getStatus());
			Assert.assertEquals("request day approve - fail @ free day status after full approve",FreeDayStatus.FINALIZE_SUCCESS, reqR3.getRequestable().getStatus());
		}
	}
	

	
	
	
	
}
