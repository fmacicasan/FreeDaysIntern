// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.timesheet;

import freedays.app.FDUser;
import freedays.app.FDUserDataOnDemand;
import freedays.timesheet.TimesheetUser;
import freedays.timesheet.TimesheetUser.Department;
import java.lang.Integer;
import java.security.SecureRandom;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect TimesheetUserDataOnDemand_Roo_DataOnDemand {
    
    declare @type: TimesheetUserDataOnDemand: @Component;
    
    private Random TimesheetUserDataOnDemand.rnd = new SecureRandom();
    
    @Autowired
    private FDUserDataOnDemand TimesheetUserDataOnDemand.fDUserDataOnDemand;
    
    public TimesheetUser TimesheetUserDataOnDemand.getNewTransientTimesheetUser(int index) {
        TimesheetUser obj = new TimesheetUser();
        setDepartment(obj, index);
        setFduser(obj, index);
        setTeampay(obj, index);
        return obj;
    }
    
    public void TimesheetUserDataOnDemand.setDepartment(TimesheetUser obj, int index) {
        Department department = Department.class.getEnumConstants()[0];
        obj.setDepartment(department);
    }
    
    public void TimesheetUserDataOnDemand.setFduser(TimesheetUser obj, int index) {
        FDUser fduser = fDUserDataOnDemand.getSpecificFDUser(index);
        obj.setFduser(fduser);
    }
    
    public void TimesheetUserDataOnDemand.setTeampay(TimesheetUser obj, int index) {
        Integer teampay = new Integer(index);
        obj.setTeampay(teampay);
    }
    
    public TimesheetUser TimesheetUserDataOnDemand.getSpecificTimesheetUser(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        TimesheetUser obj = data.get(index);
        return TimesheetUser.findTimesheetUser(obj.getId());
    }
    
    public TimesheetUser TimesheetUserDataOnDemand.getRandomTimesheetUser() {
        init();
        TimesheetUser obj = data.get(rnd.nextInt(data.size()));
        return TimesheetUser.findTimesheetUser(obj.getId());
    }
    
    public boolean TimesheetUserDataOnDemand.modifyTimesheetUser(TimesheetUser obj) {
        return false;
    }
    
}
