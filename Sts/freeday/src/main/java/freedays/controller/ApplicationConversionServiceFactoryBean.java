package freedays.controller;

import org.springframework.format.FormatterRegistry;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.RooConversionService;
    
import freedays.app.FDUser;
import freedays.domain.AdvancedUserRole;
import freedays.domain.ApplicationRegularUser;
import freedays.domain.RegularUser;
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
    
	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		// Register application converters and formatters
		registry.addConverter(getApplicationRegularUserSetConverter());
        registry.addConverter(getApplicationRegularUserConverter());
        registry.addConverter(getAdvancedUserRoleConverter());
	}
	

	public void installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(new FDUserConverter());
        registry.addConverter(new RegularUserConverter());
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
}
