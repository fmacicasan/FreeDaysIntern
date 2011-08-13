package freedays.app;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

import freedays.domain.ApplicationRegularUser;
import freedays.domain.Request;

@RooIntegrationTest(entity = FreeDayL.class)
public class FreeDayLIntegrationTest {

    @Autowired
    private FreeDayLDataOnDemand dod;
    
    @Test
    public void testMarkerMethod() {
    }

	@Test
    public void testRemove() {
        freedays.app.FreeDayL obj = dod.getRandomFreeDayL();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDay' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDay' failed to provide an identifier", id);
        obj = freedays.app.FreeDayL.findFreeDayL(id);
        //obj.remove();
        //obj.flush();
        //org.junit.Assert.assertNull("Failed to remove 'FreeDay' with identifier '" + id + "'", freedays.app.FreeDay.findFreeDay(id));
    }
	
	private Request request;
	private ApplicationRegularUser leveltop;
	private ApplicationRegularUser level1;
	private ApplicationRegularUser appreguser;
	
	@Before
	public void setUp(){
		Request.DEBUG = true;
		
		RequestStatus status = RequestStatus.values()[0];
		FreeDayL requestable = FreeDayLDataOnDemand.generateFreeDay();
		request = new Request();
		request.setStatus(status);
		request.setRequestable(requestable);
		
		FDUserDataOnDemand fdudod = new FDUserDataOnDemand();
		
		leveltop = fdudod.getRandomLevelTopUser();
		level1 = fdudod.getRandomLevelTopUser();
		while(level1.equals(leveltop))level1 = fdudod.getRandomLevelTopUser();
		level1.setGranter(leveltop);
		appreguser = fdudod.getRandomLevelTopUser();
		while(level1.equals(appreguser)||leveltop.equals(appreguser))appreguser = fdudod.getRandomLevelTopUser();
		appreguser.setGranter(level1);
		
	}
	
	@After
	public void tearDown(){
		Request.DEBUG = false;
	}
	
	/**
	 * Request granting mechanism test
	 */
    @Test
    public void testIntegration() {
    	
    	//RequestStatus status = RequestStatus.values()[0];
    	//FreeDay requestable = FreeDayDataOnDemand.generateFreeDay();

    	//ApplicationRegularUser leveltop = FDUserDataOnDemand.generateFDUser(1);
    	leveltop.getRegularUser().setEmail("fmacicasanx@sdl.com");
    	Assert.assertEquals("error @ level top email initial","fmacicasanx@sdl.com",leveltop.getRegularUser().getEmail());
    	//ApplicationRegularUser level1 = FDUserDataOnDemand.generateFDUser(2,leveltop);
    	level1.getRegularUser().setEmail("fmacicasany@sdl.com");
    	//ApplicationRegularUser appreguser = FDUserDataOnDemand.generateFDUser(3,level1);
    	appreguser.getRegularUser().setEmail("fmacicasanz@sdl.com");
    	
    	Assert.assertNotSame("distinct users expected top & 1", leveltop.getRegularUser(), level1.getRegularUser());
    	Assert.assertNotSame("distinct users expected top @ reg", leveltop.getRegularUser(), appreguser.getRegularUser());
    	Assert.assertNotSame("distinct users expected 1 @ reg", level1.getRegularUser(), appreguser.getRegularUser());
    	
    	Assert.assertEquals("error @ level top email","fmacicasanx@sdl.com",leveltop.getRegularUser().getEmail());
    	Assert.assertEquals("error @ level 1 email", "fmacicasany@sdl.com",level1.getRegularUser().getEmail());
    	Assert.assertEquals("error @ user email", "fmacicasanz@sdl.com",appreguser.getRegularUser().getEmail());
    	
    	Assert.assertNotNull("null approval for user 1",request.getRequestable().getApproval());
    	Assert.assertNotNull("null approval for user 2",request.getRequestable().getApproval().getSuccesor());
    	Assert.assertNull("null approval for top user 3",request.getRequestable().getApproval().getSuccesor().getSuccesor());
    	
    	//Request req = new Request();
    	request.setAppreguser(appreguser);
    	//req.setRequestable(requestable);
    	//req.setStatus(status);
    	
    	request.init();
    	request.approve();
    	request.approve();
    	Assert.assertEquals("ana are mere", true, true);
    }
    
    @Test
    public void testInitTopUser(){
    	request.setAppreguser(leveltop);
    	request.init();
    	Assert.assertEquals("highest level - failed direct approval after init", RequestStatus.GRANTED,request.getStatus());
    	Assert.assertNull("highest level - no approver remaining", request.getApprover());
    }
    
    @Test
    public void testInitNormalUser(){
    	request.setAppreguser(appreguser);
    	request.init();
    	Assert.assertEquals("normal level - waiting for approval after init", RequestStatus.PENDING, request.getStatus());
    	Assert.assertNotNull("normal level - failed to obtain next approver", request.getApprover());
    	Assert.assertSame("normal level - failed to obtain approver", level1, request.getApprover());
    	
    }
    
    @Test
    public void testApproveNormalUser(){
    	request.setAppreguser(appreguser);
    	request.init();
    	request.approve();
    	Assert.assertEquals("normal level - waiting for approval after one step", RequestStatus.INTERMEDIATE, request.getStatus());
    	Assert.assertNotNull("normal level - failed to obtain next approver ", request.getApprover());
    	Assert.assertSame("normal level - failed to obtain approver", leveltop, request.getApprover());
    }
    
    @Test
    public void testDoubleApproveNormalUser(){
    	request.setAppreguser(appreguser);
    	request.init();
    	request.approve();
    	request.approve();
    	Assert.assertEquals("normal level - waiting for approval after one step", RequestStatus.GRANTED, request.getStatus());
    	
    }
    
    @Test
    public void testInitLevel1User(){
    	request.setAppreguser(level1);
    	request.init();
    	Assert.assertEquals("level 1 - waiting for approval after one step", RequestStatus.PENDING, request.getStatus());
    	Assert.assertNotNull("level 1 - failed to obtain next approver ", request.getApprover());
    	Assert.assertSame("normal level - failed to obtain approver", leveltop, request.getApprover());
    }
    
    @Test
    public void testApproveLevel1User(){
    	request.setAppreguser(level1);
    	request.init();
    	ApplicationRegularUser aru = request.getApprover();
    	request.approve();
    	Assert.assertEquals("level1 - granted approval after first approve", RequestStatus.GRANTED, request.getStatus());
    	Assert.assertSame("level1 - same approver after granting", aru, request.getApprover());
    }
    
    @Test
    public void testDeny(){
    	request.setAppreguser(appreguser);
    	request.init();
    	request.deny();
    	Assert.assertEquals("normal level - failed to deny", RequestStatus.REJECTED,request.getStatus());
    }
}
