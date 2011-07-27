package freedays.controller;

import org.springframework.format.FormatterRegistry;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.RooConversionService;
    
import freedays.app.FDUser;
import freedays.app.FreeDay;
import freedays.domain.AdvancedUserRole;
import freedays.domain.ApplicationRegularUser;
import freedays.domain.RegularUser;
import freedays.domain.RequestBean;

import java.util.Set;
/**
 * A central place to register application Converters and Formatters. 
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {


	
	Converter<Set<ApplicationRegularUser>, String> getApplicationRegularUserSetConverter() {
        return new Converter<Set<ApplicationRegularUser>, String>() {
            public String convert(Set<ApplicationRegularUser> set) {
                //return Joiner.on(",").join(properties.toArray());
            	StringBuilder sb = new StringBuilder();
            	for(ApplicationRegularUser aru:set){
            		sb.append(aru.toString());
            	}
                return sb.toString();// 1
            }
        };
    }
	
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
    
    Converter<ApplicationRegularUser, String> getApplicationRegularUserConverter(){
    	return new Converter<ApplicationRegularUser, String>(){
			@Override
			public String convert(ApplicationRegularUser source) {
				return source.toString();
			}
    	
    	};
    }
    
    Converter<AdvancedUserRole, String> getAdvancedUserRoleConverter(){
    	return new Converter<AdvancedUserRole, String>(){
			@Override
			public  String convert(AdvancedUserRole source) {
				return source.toString();
			}
    	
    	};
    }
    
    Converter<FreeDay, String> getFreeDayConverter(){
    	return new Converter<FreeDay, String>(){
			@Override
			public String convert(FreeDay source) {
				return source.toString();
			}
    	};
    }
    
	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
		registry.addConverter(getApplicationRegularUserSetConverter());
		registry.addConverter(getAdvancedUserRoleSetConverter());
        registry.addConverter(getApplicationRegularUserConverter());
        registry.addConverter(getAdvancedUserRoleConverter());
        registry.addConverter(getFreeDayConverter());
	}
	

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(new FDUserConverter());
        registry.addConverter(new RegularUserConverter());
        registry.addConverter(new RequestBeanConverter());
    }

	public void afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }

	static class FDUserConverter implements Converter<FDUser, String>  {
        public String convert(FDUser fDUser) {
            //return new StringBuilder().append(fDUser.getHireDate()).append(" ").append(fDUser.getInitDays()).append(" ").append(fDUser.getMaxFreeDays()).toString();
        	return fDUser.toString();
        }
        
    }

	static class RegularUserConverter implements Converter<RegularUser, String>  {
        public String convert(RegularUser regularUser) {
            //return new StringBuilder().append(regularUser.getUsername()).append(" ").append(regularUser.getPassword()).append(" ").append(regularUser.getEmail()).append(" ").append(regularUser.getSurename()).toString();
        	return regularUser.toString();
        } 
    }
	
	static class RequestBeanConverter implements Converter<RequestBean, String>{
		@Override
		public String convert(RequestBean reqBean){
			return reqBean.toString();
		}

	}
}
