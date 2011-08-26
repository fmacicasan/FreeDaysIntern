package freedays.app.form;

import org.springframework.roo.addon.javabean.RooJavaBean;

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
