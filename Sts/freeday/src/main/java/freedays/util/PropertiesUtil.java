package freedays.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertiesUtil extends PropertyPlaceholderConfigurer {
	
	private static Map<String, String> propertiesMap;
	
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException{
		super.processProperties(beanFactory, props);
		
		propertiesMap = new HashMap<String, String>();
		for(Object key: props.keySet()){
			String keyStr = key.toString();
			propertiesMap.put(keyStr, props.getProperty(keyStr));
		}
	}
	
	public static String getProperty(String name){
		String result =  propertiesMap.get(name);
		return result;
	}
	
	public static Integer getInteger(String name){
		return Integer.parseInt(getProperty(name));
	}
	
    public static final String EMAIL_SOURCE = "email.source";
    public static final String REGISTERNOTIFICATION_SUBJECT = "registrationnotification.subject";
    public static final String REGISTERNOTIFICATION_CONTENT = "registrationnotification.content";
    public static final String POSTPROCESSINGNOTIF_SUBJECT = "postprocessingnotif.subeject";
    public static final String POSTPROCESSINGNOTIF_CONTENT = "postprocessingnotif.content";
    public static final String UPPER_REQUESTNOTIFICATION_SUBJECT = "upper.requestnotification.subject";
    public static final String UPPER_REQUESTNOTIFICATION_CONTENT = "upper.requestnotification.content";
    public static final String UPPER_REQUESTNOTIFICATION_DENY_CONTENT = "upper.requestnotification.deny.content";
    public static final String UPPER_REQUESTNOTIFICATION_CANCEL_CONTENT = "upper.requestnotification.cancel.content";
    public static final String APPLICATION_LINK = "application.link";
    public static final String LOWER_REQUESTNOTIFICATION_ONUPPEREVENT = "lower.requestnotification.onupperevent";

    public static final String RESET_PASS_TITLE = "reset.pass.title";
    public static final String RESET_PASS_MESSAGE = "reset.pass.message";
    public static final String RESET_PASS_MESSAGE_TOKEN = "reset.pass.message.token";

    public static final String TIMESHEET_CONTENT = "timesheet.content";
    public static final String TIMESHEET_SUBJECT = "timesheet.subject";
    
    public static final String TIMESHEET_LOCATION_ROOT = "timesheet.generation.location";
    
    public static final String CURRENT_YEAR = "default.current.year";

}
