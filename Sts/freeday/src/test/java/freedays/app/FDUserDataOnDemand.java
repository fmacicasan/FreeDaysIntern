package freedays.app;

import org.springframework.roo.addon.dod.RooDataOnDemand;

import freedays.domain.ApplicationRegularUser;

@RooDataOnDemand(entity = FDUser.class)
public class FDUserDataOnDemand {
	
	/**
	 * generate random FDUser
	 *  - granter set to NULL
	 * @return
	 */
	public static FDUser generateFDUser(){
		FDUserDataOnDemand fdudod = new FDUserDataOnDemand();
		FDUser fdu = fdudod.getRandomFDUser();
		return fdu;
	}
	
	public static FDUser generateFDUser(ApplicationRegularUser granter){
		FDUserDataOnDemand fdudod = new FDUserDataOnDemand();
		FDUser fdu = fdudod.getRandomFDUser();
		fdu.setGranter(granter);
		return fdu;
	}
}
