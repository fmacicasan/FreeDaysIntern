package freedays.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.roo.addon.dod.RooDataOnDemand;

import freedays.domain.ApplicationRegularUser;
import freedays.domain.RegularUser;
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
//        Calendar hireDate = DateUtils.generatePastBusinessDay();
    	Calendar hireDate = DateUtils.generateBusinessDay();
        obj.setHireDate(hireDate);
    }
    
    public FDUser getRandomNormalUser(){
    	FDUser fdu = this.getRandomFDUser();
    	int tries = 5;
		while((fdu.getGranter() == null ||
				(fdu.getGranter() != null && fdu.getGranter().getGranter() == null))&&tries > 0){
			fdu = this.getRandomFDUser();
			tries--;
		}
		if(tries == 0){
			return null;
		}
		return fdu;
    }
    
    public FDUser getRandomLevel1User(){
    	FDUser fdu = this.getRandomFDUser();
    	int tries = 5;
		while(fdu.getGranter() == null && tries > 0){
			fdu = this.getRandomFDUser();
			tries--;
		}
		if(tries == 0){
			return null;
		}
		return fdu;
    }
    
    public FDUser getRandomLevelTopUser(){
    	FDUser fdu = this.getRandomFDUser();
		while(fdu.getGranter() != null){
			fdu = this.getRandomFDUser();
		}
		return fdu;
    }


    private List<FDUser> data;
	public void init() {
//        data = FDUser.findFDUserEntries(0, 10);
//        if (data == null) throw new IllegalStateException("Find entries implementation for 'FDUser' illegally returned null");
//        if (!data.isEmpty()) {
//            return;
//        }
        
        data = new ArrayList<freedays.app.FDUser>();
        for (int i = 0; i < 10; i++) {
            FDUser obj = getNewTransientFDUser(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> it = e.getConstraintViolations().iterator(); it.hasNext();) {
                    ConstraintViolation<?> cv = it.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }

	public void setRegularUser(FDUser obj, int index) {
		RegularUserDataOnDemand rudod = new RegularUserDataOnDemand();
        RegularUser regularUser = rudod.getSpecificRegularUser(index);
        obj.setRegularUser(regularUser);
    }
}
