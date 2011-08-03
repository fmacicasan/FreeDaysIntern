package freedays.app;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.roo.addon.dod.RooDataOnDemand;

import freedays.domain.ApplicationRegularUser;
import freedays.domain.RegularUserDataOnDemand;
import freedays.util.DateUtils;

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
	
	/**
	 * Creates a valid day respecting the @Past and @BusinessDay constrains
	 * @param obj
	 * @param index
	 */
    public void setHireDate(FDUser obj, int index) {
        Calendar hireDate = DateUtils.generatePastBusinessDay();
        obj.setHireDate(hireDate);
    }
    
    public FDUser getRandomNormalUser(){
    	FDUser fdu = new FDUserDataOnDemand().getRandomFDUser();
		while(fdu.getGranter() == null ||
				(fdu.getGranter() != null && fdu.getGranter().getGranter() == null)){
			fdu = new FDUserDataOnDemand().getRandomFDUser();
		}
		return fdu;
    }
    
    public FDUser getRandomLevel1User(){
    	FDUser fdu = new FDUserDataOnDemand().getRandomFDUser();
		while(fdu.getGranter() == null){
			fdu = new FDUserDataOnDemand().getRandomFDUser();
		}
		return fdu;
    }
    
    public FDUser getRandomLevelTopUser(){
    	FDUser fdu = new FDUserDataOnDemand().getRandomFDUser();
		while(fdu.getGranter() != null){
			fdu = new FDUserDataOnDemand().getRandomFDUser();
		}
		return fdu;
    }


}
