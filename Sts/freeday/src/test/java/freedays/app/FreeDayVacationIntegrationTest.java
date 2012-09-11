package freedays.app;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

import freedays.app.FreeDay.FreeDayStatus;
import freedays.app.FreeDayInterval.ConfidenceLevel;
import freedays.app.form.FreeDayRequest.RequestType;
import freedays.app.form.FreeDayRequestInterval;
import freedays.domain.Request;
import freedays.util.DateUtils;

@RooIntegrationTest(entity = FreeDayVacation.class)
public class FreeDayVacationIntegrationTest {

	FreeDayRequestInterval fdrv;
	String username;
	
    @Autowired
    private FreeDayVacationDataOnDemand dod;
	
	@Before
	public void setUp(){
		Request.DEBUG = true;
		
		
		FDUser fdu = new FDUserDataOnDemand().getRandomNormalUser();
		if(fdu!=null){
			username =  fdu.getRegularUser().getUsername();
		} else {
			username = null;
		}
		
    	fdrv = new FreeDayRequestInterval();
//    	Calendar c = DateUtils.generateFutureBusinessDay();
    	Calendar c = DateUtils.generateBusinessDay();
    	fdrv.setReqdate(c);
    	Calendar end = DateUtils.dateAddBusinessDay(c, 9L);
    	fdrv.setFinish(end);
    	fdrv.setReqtype(RequestType.V);
    	fdrv.setMatch(null);
    	fdrv.setReason(null);
    	fdrv.setConfidence(ConfidenceLevel.MEDIUM);
	}
	@After
	public void tearDown(){
		Request.DEBUG = false;
	}
	
    @Test
    public void testMarkerMethod() {
    }
    
    @Test
    public void testFDVacationPersist(){
    	if(username != null){
	    	long beforeC = Request.countActiveRequests(username);
			Request.createPersistentReq(fdrv,username);
			long afterC = Request.countActiveRequests(username);
			Assert.assertEquals("request day approve - not persist",beforeC+1, afterC);
    	}
    }
    
    @Test
    public void testFDVacationApprove(){
    	if(username != null){
	    	Request reqC = Request.createPersistentReq(fdrv,username);
			
			Assert.assertEquals("request day approve - fail @ req status before approve 1",RequestStatus.PENDING, reqC.getStatus());
			Assert.assertEquals("request day approve - fail @ free day status before approve 1",FreeDayStatus.IN_PROGRESS, reqC.getRequestable().getStatus());
			reqC.approve();
			Assert.assertEquals("request day approve - fail @ req status before approve 2",RequestStatus.INTERMEDIATE, reqC.getStatus());
			Assert.assertEquals("request day approve - fail @ free day status before approve 2",FreeDayStatus.IN_PROGRESS, reqC.getRequestable().getStatus());
			reqC.approve();
			Assert.assertEquals("request day approve - fail @ req status after full approve",RequestStatus.GRANTED, reqC.getStatus());
			Assert.assertEquals("request day approve - fail @ free day status after full approve",FreeDayStatus.FINALIZE_SUCCESS, reqC.getRequestable().getStatus());
    	}
    }
    
    @Test
    public void testFDVacationDeny(){
    	if(username != null){
	    	Request reqC = Request.createPersistentReq(fdrv,username);
			
			Assert.assertEquals("request day approve - fail @ req status before approve 1",RequestStatus.PENDING, reqC.getStatus());
			Assert.assertEquals("request day approve - fail @ free day status before approve 1",FreeDayStatus.IN_PROGRESS, reqC.getRequestable().getStatus());
			reqC.approve();
			Assert.assertEquals("request day approve - fail @ req status before approve 2",RequestStatus.INTERMEDIATE, reqC.getStatus());
			Assert.assertEquals("request day approve - fail @ free day status before approve 2",FreeDayStatus.IN_PROGRESS, reqC.getRequestable().getStatus());
			reqC.deny();
			Assert.assertEquals("request day approve - fail @ req status after deny",RequestStatus.REJECTED, reqC.getStatus());
			Assert.assertEquals("request day approve - fail @ free day status after deny",FreeDayStatus.COMPLETED_FAILURE, reqC.getRequestable().getStatus());
    	}
    }
    
    @Test
    public void testFDVacationCancel(){
    	if(username != null){
	    	Request reqC = Request.createPersistentReq(fdrv,username);
			
			Assert.assertEquals("request day approve - fail @ req status before approve 1",RequestStatus.PENDING, reqC.getStatus());
			Assert.assertEquals("request day approve - fail @ free day status before approve 1",FreeDayStatus.IN_PROGRESS, reqC.getRequestable().getStatus());
			reqC.approve();
			Assert.assertEquals("request day approve - fail @ req status before approve 2",RequestStatus.INTERMEDIATE, reqC.getStatus());
			Assert.assertEquals("request day approve - fail @ free day status before approve 2",FreeDayStatus.IN_PROGRESS, reqC.getRequestable().getStatus());
			reqC.cancel();
			Assert.assertEquals("request day approve - fail @ req status after cancel",RequestStatus.CANCELED, reqC.getStatus());
			Assert.assertEquals("request day approve - fail @ free day status after cancel",FreeDayStatus.COMPLETED_FAILURE, reqC.getRequestable().getStatus());
    	}
    }



	@Test
    public void testFindAllFreeDayVacations() {
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayVacation' failed to initialize correctly", dod.getRandomFreeDayVacation());
        long count = freedays.app.FreeDayVacation.countFreeDayVacations();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'FreeDayVacation', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 10000);
        java.util.List<freedays.app.FreeDayVacation> result = freedays.app.FreeDayVacation.findAllFreeDayVacations();
        org.junit.Assert.assertNotNull("Find all method for 'FreeDayVacation' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'FreeDayVacation' failed to return any data", result.size() > 0);
    }
}
