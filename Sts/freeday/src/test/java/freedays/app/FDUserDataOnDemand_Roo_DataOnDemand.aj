// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import freedays.app.FDUser;
import freedays.domain.ApplicationRegularUser;
import freedays.domain.ApplicationRegularUser.JobRole;
import java.lang.Integer;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect FDUserDataOnDemand_Roo_DataOnDemand {
    
    declare @type: FDUserDataOnDemand: @Component;
    
    private Random FDUserDataOnDemand.rnd = new SecureRandom();
    
    public FDUser FDUserDataOnDemand.getNewTransientFDUser(int index) {
        FDUser obj = new FDUser();
        setGranter(obj, index);
        setHireDate(obj, index);
        setInitDays(obj, index);
        setJobrole(obj, index);
        setMaxDerogation(obj, index);
        setMaxFreeDays(obj, index);
        setRegularUser(obj, index);
        return obj;
    }
    
    public void FDUserDataOnDemand.setGranter(FDUser obj, int index) {
        ApplicationRegularUser granter = null;
        obj.setGranter(granter);
    }
    
    public void FDUserDataOnDemand.setInitDays(FDUser obj, int index) {
        Integer initDays = new Integer(index);
        if (initDays < 2 || initDays > 7) {
            initDays = 7;
        }
        obj.setInitDays(initDays);
    }
    
    public void FDUserDataOnDemand.setJobrole(FDUser obj, int index) {
        JobRole jobrole = JobRole.class.getEnumConstants()[0];
        obj.setJobrole(jobrole);
    }
    
    public void FDUserDataOnDemand.setMaxDerogation(FDUser obj, int index) {
        Integer maxDerogation = new Integer(index);
        obj.setMaxDerogation(maxDerogation);
    }
    
    public void FDUserDataOnDemand.setMaxFreeDays(FDUser obj, int index) {
        Integer maxFreeDays = new Integer(index);
        if (maxFreeDays < 21) {
            maxFreeDays = 21;
        }
        obj.setMaxFreeDays(maxFreeDays);
    }
    
    public FDUser FDUserDataOnDemand.getSpecificFDUser(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        FDUser obj = data.get(index);
        return FDUser.findFDUser(obj.getId());
    }
    
    public FDUser FDUserDataOnDemand.getRandomFDUser() {
        init();
        FDUser obj = data.get(rnd.nextInt(data.size()));
        return FDUser.findFDUser(obj.getId());
    }
    
    public boolean FDUserDataOnDemand.modifyFDUser(FDUser obj) {
        return false;
    }
    
}
