package freedays.app;

import java.util.Calendar;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;

import freedays.app.FreeDay.FreeDayStatus;
import freedays.app.FreeDayVacation.ConfidenceLevel;
import freedays.app.form.FreeDayRequest.RequestType;
import freedays.app.form.FreeDayRequestVacation;
import freedays.domain.Request;
import freedays.util.DateUtils;

@RooIntegrationTest(entity = FreeDayVacation.class)
public class FreeDayVacationIntegrationTest {

	FreeDayRequestVacation fdrv;
	String username;
	
	@Before
	public void setUp(){
		Request.DEBUG = true;
		
		
		FDUser fdu = new FDUserDataOnDemand().getRandomNormalUser();
		if(fdu!=null){
			username =  fdu.getRegularUser().getUsername();
		} else {
			username = null;
		}
		
    	fdrv = new FreeDayRequestVacation();
    	Calendar c = DateUtils.generateFutureBusinessDay();
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
}
