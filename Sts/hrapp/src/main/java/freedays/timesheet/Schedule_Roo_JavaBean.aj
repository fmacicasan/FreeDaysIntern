// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.timesheet;

import freedays.timesheet.Pattern;
import freedays.timesheet.TimesheetUser;
import java.util.Calendar;

privileged aspect Schedule_Roo_JavaBean {
    
    public Calendar Schedule.getStartDate() {
        return this.startDate;
    }
    
    public void Schedule.setStartDate(Calendar startDate) {
        this.startDate = startDate;
    }
    
    public Calendar Schedule.getEndDate() {
        return this.endDate;
    }
    
    public void Schedule.setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }
    
    public Pattern Schedule.getPattern() {
        return this.pattern;
    }
    
    public void Schedule.setPattern(Pattern pattern) {
        this.pattern = pattern;
    }
    
    public TimesheetUser Schedule.getEmployee() {
        return this.employee;
    }
    
    public void Schedule.setEmployee(TimesheetUser employee) {
        this.employee = employee;
    }
    
}
