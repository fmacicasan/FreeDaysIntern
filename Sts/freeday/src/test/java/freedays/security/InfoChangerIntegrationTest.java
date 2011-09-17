package freedays.security;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.annotation.Repeat;

import freedays.domain.RegularUser;
import freedays.domain.RegularUserDataOnDemand;

@RooIntegrationTest(entity = InfoChanger.class)
public class InfoChangerIntegrationTest {

	private InfoChangerDataOnDemand icdod;
	private RegularUserDataOnDemand rudod;
	
	@Before
	public void setUp(){
		icdod = new InfoChangerDataOnDemand();
		rudod = new RegularUserDataOnDemand();
		InfoChangerDataOnDemand.setFromDb();
	}
    
    @Test
    @Repeat(10)
    public void testTokenValidity(){
    	InfoChanger ic = icdod.getRandomInfoChanger();
    	Assert.assertEquals(ic.getExpcode(),ic.genToken());
    }
    
    @Test
    @Repeat(10)
    public void testNewTokenValidity(){
    	InfoChangerDataOnDemand.resetFromDb();
    	RegularUser ru = rudod.getRandomRegularUser();
    	String token = InfoChanger.generateToken(ru);
    	InfoChanger ic = InfoChanger.findByHash(token).getSingleResult();
    	Assert.assertEquals(ic.getExpcode(),ic.genToken());
    	Assert.assertEquals(ic.getExpcode(),token);
    }
    
    @Test
    public void testNewTokenCycle(){
    	InfoChangerDataOnDemand.resetFromDb();
    	RegularUser ru = rudod.getRandomRegularUser();
    	String token = InfoChanger.generateToken(ru);
    	Assert.assertTrue(InfoChanger.verifyToken(token));
    }
    
    @Test
    public void testNewTokenPostVerifyState(){
    	InfoChangerDataOnDemand.resetFromDb();
    	RegularUser ru = rudod.getRandomRegularUser();
    	String token = InfoChanger.generateToken(ru);
    	Assert.assertTrue(InfoChanger.verifyToken(token));
    	InfoChanger.finalizePassReset(token);
    	InfoChanger ic = InfoChanger.findByHash(token).getSingleResult();
    	Assert.assertTrue(ic.isUsed());
    }
    
    @Test
    public void testJustCreatedTokenState(){
    	InfoChangerDataOnDemand.resetFromDb();
    	InfoChanger ic = icdod.getRandomTransientJustCreatedInfoChanger();
    	Assert.assertFalse(ic.isExpired());
    	System.out.println(ic);
    	Assert.assertFalse(ic.toString(),ic.isJustExpired());
    	Assert.assertFalse(ic.isUsed());
    }
    
    @Test
    public void testJustExpiredTokenState(){
    	InfoChangerDataOnDemand.resetFromDb();
    	InfoChanger ic = icdod.getRandomTransientJustExpiredInfoChanger();
    	Assert.assertFalse(ic.isExpired());
    	Assert.assertTrue(ic.toString(),ic.isJustExpired());
    	Assert.assertFalse(ic.isUsed());
    }
}
