// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.controller;

import freedays.timesheet.LaborBilling;
import freedays.timesheet.Pattern;
import freedays.timesheet.Phase;
import freedays.timesheet.PhaseLabor;
import freedays.timesheet.Project;
import freedays.timesheet.Schedule;
import java.lang.String;
import org.springframework.core.convert.converter.Converter;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    static class freedays.controller.ApplicationConversionServiceFactoryBean.LaborBillingConverter implements Converter<LaborBilling, String> {
        public String convert(LaborBilling laborBilling) {
            return new StringBuilder().append(laborBilling.getCode()).append(" ").append(laborBilling.getName()).toString();
        }
        
    }
    
    static class freedays.controller.ApplicationConversionServiceFactoryBean.PatternConverter implements Converter<Pattern, String> {
        public String convert(Pattern pattern) {
            return new StringBuilder().append(pattern.getNoOfHours()).toString();
        }
        
    }
    
    static class freedays.controller.ApplicationConversionServiceFactoryBean.PhaseConverter implements Converter<Phase, String> {
        public String convert(Phase phase) {
            return new StringBuilder().append(phase.getCode()).append(" ").append(phase.getName()).toString();
        }
        
    }
    
    static class freedays.controller.ApplicationConversionServiceFactoryBean.PhaseLaborConverter implements Converter<PhaseLabor, String> {
        public String convert(PhaseLabor phaseLabor) {
            return new StringBuilder().append(phaseLabor.getPercentage()).toString();
        }
        
    }
    
    static class freedays.controller.ApplicationConversionServiceFactoryBean.ProjectConverter implements Converter<Project, String> {
        public String convert(Project project) {
            return new StringBuilder().append(project.getCode()).append(" ").append(project.getName()).toString();
        }
        
    }
    
    static class freedays.controller.ApplicationConversionServiceFactoryBean.ScheduleConverter implements Converter<Schedule, String> {
        public String convert(Schedule schedule) {
            return new StringBuilder().append(schedule.getStartDate()).append(" ").append(schedule.getEndDate()).toString();
        }
        
    }
    
}
