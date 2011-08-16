package freedays.app.form;

import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.app.FreeDay.FreeDayStatus;
import freedays.app.form.FreeDayRequest.RequestType;

/**
 * Wrapper class backing a free day representation in the free days
 * report.
 * @author Flopi
 *
 */
@RooJavaBean
public class FreeDayReportWrapper {

	private Integer type;
	private String status;
}
