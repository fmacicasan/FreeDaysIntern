package freedays.domain;

import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@Transactional
public class ApplicationRegularUserIntegrationTest {
	
	private String superbossusername;
	private ApplicationRegularUser boss;
	
	@Before
	public void setUp(){
		RegularUserDataOnDemand rudod = new RegularUserDataOnDemand();
		RegularUser ru = rudod.getRandomRegularUser();
		do {
		    ru = rudod.getRandomRegularUser();
		}while(JobRole.OBS.equals(ApplicationRegularUser.findByUsername(ru.getUsername()).getJobrole()));
		
		this.superbossusername = ru.getUsername();
		boss = ApplicationRegularUser.findByUsername(superbossusername);
		while(!boss.isSuperUser()){
			ru = boss.getGranter().getRegularUser();
			this.superbossusername = ru.getUsername();
			boss = ApplicationRegularUser.findByUsername(superbossusername);
		}
	}
	
	@Test
	public void testFindAllSubordinatesRequestGranters(){
		Set<ApplicationRegularUser> saru = ApplicationRegularUser.findAllSubordinatesRequestGranters(boss);
		for(ApplicationRegularUser appru : saru){
			Assert.assertTrue("subordinate not request granter", appru.isRequestGranter());
			Assert.assertTrue("subordinate not superapproved by boss",appru.isSuperApprovedBy(boss));
			Assert.assertTrue("boss not superapprover of subordinate",boss.isSuperApproverOf(appru));
		}
	}
	
	@Test
	public void testFindAllSubordinatesTree(){
		Set<ApplicationRegularUser> saru = ApplicationRegularUser.findAllSubordinatesTree(boss);
		for(ApplicationRegularUser appru : saru){
			Assert.assertTrue("subordinate not request granter", appru.isRequestGranter());
			Assert.assertTrue("subordinate not superapproved by boss",appru.isSuperApprovedBy(boss));
			Assert.assertTrue("boss not superapprover of subordinate",boss.isSuperApproverOf(appru));
		}
	}
	
	@Test
	public void testBoss(){
		Assert.assertFalse("boss problem has approver", boss.hasApprover());
		Assert.assertTrue("boss problem is not super user",boss.isSuperUser());
		Assert.assertTrue("boss problem is approver",boss.isRequestGranter());
	}
}
