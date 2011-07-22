package freedays.app;

import org.springframework.roo.addon.dod.RooDataOnDemand;

import freedays.domain.ApplicationRegularUser;
import freedays.domain.RegularUserDataOnDemand;

@RooDataOnDemand(entity = FDUser.class)
public class FDUserDataOnDemand {
	
	/**
	 * generate random FDUser
	 *  - granter set to NULL
	 * @return
	 */
	public static FDUser generateFDUser(int cnt){
		FDUserDataOnDemand fdudod = new FDUserDataOnDemand();
		FDUser fdu = fdudod.getSpecificFDUser(cnt);
		return fdu;
	}
	
	public static FDUser generateFDUser(int cnt, ApplicationRegularUser granter){
		FDUserDataOnDemand fdudod = new FDUserDataOnDemand();
		RegularUserDataOnDemand rudod = new RegularUserDataOnDemand();
		FDUser fdu = fdudod.getNewTransientFDUser(cnt);
		fdu.setRegularUser(rudod.getNewTransientRegularUser(cnt));
		fdu.setGranter(granter);
		return fdu;
	}


}
