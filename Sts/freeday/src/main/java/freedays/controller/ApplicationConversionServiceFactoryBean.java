package freedays.controller;

import java.util.Set;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.RooConversionService;

import freedays.app.FDUser;
import freedays.app.FreeDay;
import freedays.app.FreeDaysRCMatch;
import freedays.app.form.FreeDayRequest;
import freedays.domain.AdvancedUserRole;
import freedays.domain.ApplicationRegularUser;
import freedays.domain.RegularUser;
import freedays.timesheet.Pattern;
import freedays.timesheet.Phase;
import freedays.timesheet.Project;
import freedays.timesheet.Schedule;

import java.util.Set;
/**
 * A central place to register application Converters and Formatters.
 * INFO: default location where spring retrieves conversion and formatting strategies for view exposion.
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {


	/**
	 * strategy for converting a set of application regular users to string
	 * @return
	 */
	Converter<Set<ApplicationRegularUser>, String> getApplicationRegularUserSetConverter() {
        return new Converter<Set<ApplicationRegularUser>, String>() {
            public String convert(Set<ApplicationRegularUser> set) {
                //return Joiner.on(",").join(properties.toArray());
            	StringBuilder sb = new StringBuilder();
            	for(ApplicationRegularUser aru:set){
            		sb.append(aru.toString()).append(" ");
            	}
                return sb.toString();// 1
            }
        };
    }
	
	/**
	 * strategy for converting a set of patterns to string
	 * @return
	 */
	Converter<Set<Pattern>, String> getPatternSetConverter() {
        return new Converter<Set<Pattern>, String>() {
            public String convert(Set<Pattern> set) {
                //return Joiner.on(",").join(properties.toArray());
            	StringBuilder sb = new StringBuilder();
            	for(Pattern aru:set){
            		sb.append(aru.toString());
            	}
                return sb.toString();// 1
            }
        };
    }
	
	/**
	  Strategy for converting a set of Phases to string
	 * @return
	 */
	Converter<Set<Phase>, String> getPhaseSetConverter() {
        return new Converter<Set<Phase>, String>() {
            public String convert(Set<Phase> set) {
                //return Joiner.on(",").join(properties.toArray());
            	StringBuilder sb = new StringBuilder();
            	for(Phase aru:set){
            		sb.append(aru.toString());
            	}
                return sb.toString();// 1
            }
        };
    }
	
	/**
	  Strategy for converting a Phase to string
	 * @return
	 */
	Converter<Phase, String> getPhaseConverter() {
    	return new Converter<Phase, String>(){
			@Override
			public  String convert(Phase source) {
				return source.toString();
			}
    	
    	};
    }
	
	/**
	 * Strategy for converting a set of Advance user roles to string
	 * @return
	 */
	Converter<Set<AdvancedUserRole>, String> getAdvancedUserRoleSetConverter() {
        return new Converter<Set<AdvancedUserRole>, String>() {
            public String convert(Set<AdvancedUserRole> set) {
                //return Joiner.on(",").join(properties.toArray());
            	StringBuilder sb = new StringBuilder();
            	for(AdvancedUserRole aru:set){
            		sb.append(aru.toString());
            	}
                return sb.toString();// 1
            }
        };
    }
	
	/**
	 * Strategy for converting a set of Schedules to string
	 * @return
	 */
	Converter<Set<Schedule>, String> getScheduleSetConverter() {
        return new Converter<Set<Schedule>, String>() {
            public String convert(Set<Schedule> set) {
                //return Joiner.on(",").join(properties.toArray());
            	StringBuilder sb = new StringBuilder();
            	for(Schedule aru:set){
            		sb.append(aru.toString());
            		sb.append(aru.toString()).append(" ");
            	}
                return sb.toString();// 1
            }
        };
    }
    
	/**
	 * Strategy for converting a Application Regular User to string
	 * @return
	 */
    Converter<ApplicationRegularUser, String> getApplicationRegularUserConverter(){
    	return new Converter<ApplicationRegularUser, String>(){
			@Override
			public String convert(ApplicationRegularUser source) {
				
				return source.toString();
			}
    	
    	};
    }
    /**
	 * Strategy for converting an Object to string
	 * @return
	 */
    Converter<Object, String> getObjectConverter(){
    	return new Converter<Object, String>(){
			@Override
			public String convert(Object source) {
				return source.toString();
			}
    	
    	};
    }
    
    /**
	 * Strategy for converting a set of Objects to string
	 * @return
	 */
    Converter<Set<Object>, String> getObjectSetConverter() {
        return new Converter<Set<Object>, String>() {
            public String convert(Set<Object> set) {
                //return Joiner.on(",").join(properties.toArray());
            	StringBuilder sb = new StringBuilder();
            	for(Object aru:set){
            		sb.append(aru.toString());
            	}
                return sb.toString();// 1
            }
        };
    }
    
    
    /**
	 * Strategy for converting a set of Projects to string
	 * @return
	 */
    Converter<Set<Project>, String> getProjectSetConverter() {
        return new Converter<Set<Project>, String>() {
            public String convert(Set<Project> set) {
                //return Joiner.on(",").join(properties.toArray());
            	StringBuilder sb = new StringBuilder();
            	for(Project aru:set){
            		sb.append(aru.toString());
            	}
                return sb.toString();// 1
            }
        };
    }
    /**
	  Strategy for converting a Project to string
	 * @return
	 */
	Converter<Project, String> getProjectConverter() {
  	return new Converter<Project, String>(){
			@Override
			public  String convert(Project source) {
				return source.toString();
			}
  	
  	};
  }
    
    /**
	 * Strategy for converting a Advanced User Role to string
	 * @return
	 */
    Converter<AdvancedUserRole, String> getAdvancedUserRoleConverter(){
    	return new Converter<AdvancedUserRole, String>(){
			@Override
			public  String convert(AdvancedUserRole source) {
				return source.toString();
			}
    	
    	};
    }
    
    /**
	 * Strategy for converting a FreeDay to string
	 * @return
	 */
    Converter<FreeDay, String> getFreeDayConverter(){
    	return new Converter<FreeDay, String>(){
			@Override
			public String convert(FreeDay source) {
				return source.toString();
			}
    	};
    }
    
    /**
	 * Strategy for converting a FreeDayRCMatch to string
	 * @return
	 */
    Converter<FreeDaysRCMatch, String> getFreeDayRCMatchConverter(){
    	return new Converter<FreeDaysRCMatch, String>(){

			@Override
			public String convert(FreeDaysRCMatch source) {
				return source.toString();
			}
    		
    	};
    }
    
    
    
    /**
     * {@inheritDoc}
     */
	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
		registry.addConverter(getApplicationRegularUserSetConverter());
		registry.addConverter(getAdvancedUserRoleSetConverter());
        registry.addConverter(getApplicationRegularUserConverter());
        registry.addConverter(getAdvancedUserRoleConverter());
        registry.addConverter(getFreeDayConverter());
        registry.addConverter(getObjectSetConverter());
        /*registry.addConverter(getPatternSetConverter());
        registry.addConverter(getProjectConverter());*/
        registry.addConverter(getObjectConverter());
        /*registry.addConverter(getPhaseConverter());
        registry.addConverter(getProjectConverter());
        registry.addConverter(getPhaseSetConverter());
        registry.addConverter(getProjectSetConverter());
        */
        
	}
	

	/**
	 * Default installation hook generated by roo.
	 * @param registry
	 */
	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(new FDUserConverter());
        registry.addConverter(new RegularUserConverter());
        registry.addConverter(new FreeDayRequestConverter());
    }

	/**
	 * Hook for custom label installment
	 */
	@Override
	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }

	/**
	 * Automatically generated converter by roo
	 * @author roo
	 *
	 */
	static class FDUserConverter implements Converter<FDUser, String>  {
		/**
		 * Converts a FDUser to it's textual representation
		 */
		@Override
        public String convert(FDUser fDUser) {
            //return new StringBuilder().append(fDUser.getHireDate()).append(" ").append(fDUser.getInitDays()).append(" ").append(fDUser.getMaxFreeDays()).toString();
        	return fDUser.toString();
        }
        
    }

	/**
	 * Automatically generated converter by roo
	 * @author roo
	 *
	 */
	static class RegularUserConverter implements Converter<RegularUser, String>  {
		/**
		 * Converts a RegularUser to it's textual representation
		 */
		@Override
        public String convert(RegularUser regularUser) {
            //return new StringBuilder().append(regularUser.getUsername()).append(" ").append(regularUser.getPassword()).append(" ").append(regularUser.getEmail()).append(" ").append(regularUser.getSurename()).toString();
        	return regularUser.toString();
        } 
    }
	
	/**
	 * Automatically generated converter by roo
	 * @author roo
	 *
	 */
	static class FreeDayRequestConverter implements Converter<FreeDayRequest, String>{
		/**
		 * Converts a FreeDayRequest to it's textual representation
		 */
		@Override
		public String convert(FreeDayRequest reqBean){
			return reqBean.toString();
		}

	}
}
