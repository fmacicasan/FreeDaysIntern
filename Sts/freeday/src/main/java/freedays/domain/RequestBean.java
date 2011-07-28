package freedays.domain;

import java.util.Calendar;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.validation.annotation.BusinessDay;

@RooJavaBean
public class RequestBean {
	
	@Future
	@DateTimeFormat(style = "S-")
	@NotNull
	@BusinessDay
	private Calendar reqdate;
	
	private String reason;
}
