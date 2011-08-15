package freedays.app.form;

import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.app.FreeDay.FreeDayStatus;
import freedays.app.form.FreeDayRequest.RequestType;

@RooJavaBean
public class FreeDayReportWrapper {

	private Integer type;
	private String status;
}
