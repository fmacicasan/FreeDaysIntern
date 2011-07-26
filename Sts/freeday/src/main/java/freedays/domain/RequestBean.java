package freedays.domain;

import java.util.Calendar;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class RequestBean {
	
	@DateTimeFormat(style = "S-")
	private Calendar date;
	
	private String reason;
}
