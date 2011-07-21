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
//    	ApplicationRegularUser leveltop = FDUserDataOnDemand.generateFDUser(1);
//    	leveltop.getRegularUser().setEmail("parvu_georgian@yahoo.com");
//    	Assert.assertEquals("error @ level top email initial","parvu_georgian@yahoo.com",leveltop.getRegularUser().getEmail());
//    	ApplicationRegularUser level1 = FDUserDataOnDemand.generateFDUser(2,leveltop);
//    	level1.getRegularUser().setEmail("iulia_teglas@yahoo.com");
//    	ApplicationRegularUser appreguser = FDUserDataOnDemand.generateFDUser(3,level1);
//    	appreguser.getRegularUser().setEmail("burtoflex89@yahoo.com");
//    	
//    	Assert.assertNotSame("distinct users expected top & 1", leveltop.getRegularUser(), level1.getRegularUser());
//    	Assert.assertNotSame("distinct users expected top @ reg", leveltop.getRegularUser(), appreguser.getRegularUser());
//    	Assert.assertNotSame("distinct users expected 1 @ reg", level1.getRegularUser(), appreguser.getRegularUser());
//    	
//    	Assert.assertEquals("error @ level top email","parvu_georgian@yahoo.com",leveltop.getRegularUser().getEmail());
//    	Assert.assertEquals("error @ level 1 email", "iulia_teglas@yahoo.com",level1.getRegularUser().getEmail());
//    	Assert.assertEquals("error @ user email", "burtoflex89@yahoo.com",appreguser.getRegularUser().getEmail());
//    	
//    	Request req = new Request();
//    	req.setAppreguser(appreguser);
//    	req.setRequestable(requestable);
//    	req.setStatus(status);
//    	
//    	req.init();
//    	req.approve();
//    	req.approve();
//    	Assert.assertEquals("ana are mere", true, true);
    }
}
