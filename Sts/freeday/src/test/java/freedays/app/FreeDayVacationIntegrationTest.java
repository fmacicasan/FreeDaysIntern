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

//	@Test
//    public void testCountFreeDayVacations() {
//        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayVacation' failed to initialize correctly", dod.getRandomFreeDayVacation());
//        long count = freedays.app.FreeDayVacation.countFreeDayVacations();
//        org.junit.Assert.assertTrue("Counter for 'FreeDayVacation' incorrectly reported there were no entries", count > 0);
//    }
//
//	@Test
//    public void testFindFreeDayVacation() {
//        freedays.app.FreeDayVacation obj = dod.getRandomFreeDayVacation();
//        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayVacation' failed to initialize correctly", obj);
//        java.lang.Long id = obj.getId();
//        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayVacation' failed to provide an identifier", id);
//        obj = freedays.app.FreeDayVacation.findFreeDayVacation(id);
//        org.junit.Assert.assertNotNull("Find method for 'FreeDayVacation' illegally returned null for id '" + id + "'", obj);
//        org.junit.Assert.assertEquals("Find method for 'FreeDayVacation' returned the incorrect identifier", id, obj.getId());
//    }
//
//	@Test
//    public void testFindAllFreeDayVacations() {
//        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayVacation' failed to initialize correctly", dod.getRandomFreeDayVacation());
//        long count = freedays.app.FreeDayVacation.countFreeDayVacations();
//        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'FreeDayVacation', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
//        java.util.List<freedays.app.FreeDayVacation> result = freedays.app.FreeDayVacation.findAllFreeDayVacations();
//        org.junit.Assert.assertNotNull("Find all method for 'FreeDayVacation' illegally returned null", result);
//        org.junit.Assert.assertTrue("Find all method for 'FreeDayVacation' failed to return any data", result.size() > 0);
//    }
//
//	@Test
//    public void testFindFreeDayVacationEntries() {
//        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayVacation' failed to initialize correctly", dod.getRandomFreeDayVacation());
//        long count = freedays.app.FreeDayVacation.countFreeDayVacations();
//        if (count > 20) count = 20;
//        java.util.List<freedays.app.FreeDayVacation> result = freedays.app.FreeDayVacation.findFreeDayVacationEntries(0, (int) count);
//        org.junit.Assert.assertNotNull("Find entries method for 'FreeDayVacation' illegally returned null", result);
//        org.junit.Assert.assertEquals("Find entries method for 'FreeDayVacation' returned an incorrect number of entries", count, result.size());
//    }
//
//	@Test
//    public void testFlush() {
//        freedays.app.FreeDayVacation obj = dod.getRandomFreeDayVacation();
//        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayVacation' failed to initialize correctly", obj);
//        java.lang.Long id = obj.getId();
//        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayVacation' failed to provide an identifier", id);
//        obj = freedays.app.FreeDayVacation.findFreeDayVacation(id);
//        org.junit.Assert.assertNotNull("Find method for 'FreeDayVacation' illegally returned null for id '" + id + "'", obj);
//        boolean modified =  dod.modifyFreeDayVacation(obj);
//        java.lang.Integer currentVersion = obj.getVersion();
//        obj.flush();
//        org.junit.Assert.assertTrue("Version for 'FreeDayVacation' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
//    }
//
//	@Test
//    public void testMerge() {
//        freedays.app.FreeDayVacation obj = dod.getRandomFreeDayVacation();
//        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayVacation' failed to initialize correctly", obj);
//        java.lang.Long id = obj.getId();
//        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayVacation' failed to provide an identifier", id);
//        obj = freedays.app.FreeDayVacation.findFreeDayVacation(id);
//        boolean modified =  dod.modifyFreeDayVacation(obj);
//        java.lang.Integer currentVersion = obj.getVersion();
//        freedays.app.FreeDayVacation merged = (freedays.app.FreeDayVacation) obj.merge();
//        obj.flush();
//        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
//        org.junit.Assert.assertTrue("Version for 'FreeDayVacation' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
//    }
//
//	@Test
//    public void testPersist() {
//        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayVacation' failed to initialize correctly", dod.getRandomFreeDayVacation());
//        freedays.app.FreeDayVacation obj = dod.getNewTransientFreeDayVacation(Integer.MAX_VALUE);
//        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayVacation' failed to provide a new transient entity", obj);
//        org.junit.Assert.assertNull("Expected 'FreeDayVacation' identifier to be null", obj.getId());
//        obj.persist();
//        obj.flush();
//        org.junit.Assert.assertNotNull("Expected 'FreeDayVacation' identifier to no longer be null", obj.getId());
//    }
//
//	@Test
//    public void testRemove() {
//        freedays.app.FreeDayVacation obj = dod.getRandomFreeDayVacation();
//        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayVacation' failed to initialize correctly", obj);
//        java.lang.Long id = obj.getId();
//        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayVacation' failed to provide an identifier", id);
//        obj = freedays.app.FreeDayVacation.findFreeDayVacation(id);
//        obj.remove();
//        obj.flush();
//        org.junit.Assert.assertNull("Failed to remove 'FreeDayVacation' with identifier '" + id + "'", freedays.app.FreeDayVacation.findFreeDayVacation(id));
//    }
}
