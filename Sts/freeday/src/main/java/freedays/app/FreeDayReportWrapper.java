package freedays.app;

import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.app.FreeDay.FreeDayStatus;
import freedays.app.FreeDayRequest.RequestType;

@RooJavaBean
public class FreeDayReportWrapper {

	private Integer type;
	private String status;
}
