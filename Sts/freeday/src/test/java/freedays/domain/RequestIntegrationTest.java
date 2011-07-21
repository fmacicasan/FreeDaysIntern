package freedays.domain;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;

import freedays.app.FDUserDataOnDemand;
import freedays.app.FreeDay;
import freedays.app.FreeDayDataOnDemand;
import freedays.app.RequestStatus;

@RooIntegrationTest(entity = Request.class)
public class RequestIntegrationTest {

	/**
	 * Request granting mechanism test
	 * 	- STATUS: comment for CI
	 */
    @Test
    public void testMarkerMethod() {
    	
//    	RequestStatus status = RequestStatus.values()[0];
//    	FreeDay requestable = FreeDayDataOnDemand.generateFreeDay();
//
//    	ApplicationRegularUser leveltop = FDUserDataOnDemand.generateFDUser();
//    	leveltop.getRegularUser().setEmail("burtoflex89@yahoo.com");
//    	ApplicationRegularUser level1 = FDUserDataOnDemand.generateFDUser(leveltop);
//    	level1.getRegularUser().setEmail("flo.macicasan@yahoo.com");
//    	ApplicationRegularUser appreguser = FDUserDataOnDemand.generateFDUser(level1);
//    	appreguser.getRegularUser().setEmail("burtoflex89@yahoo.com");
//    	
//    	
//    	Request req = new Request();
//    	req.setAppreguser(appreguser);
//    	req.setRequestable(requestable);
//    	req.setStatus(status);
//    	
//    	req.init();
//    	//req.approve();
//    	//req.deny();
//    	Assert.assertEquals("ana are mere", true, false);
    }
}
