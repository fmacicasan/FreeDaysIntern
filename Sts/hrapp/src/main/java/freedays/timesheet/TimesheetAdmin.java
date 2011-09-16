package freedays.timesheet;

import javax.persistence.DiscriminatorValue;

import freedays.domain.AdvancedUserRole;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
@RooEntity
@DiscriminatorValue("TimesheetAdmin")
public class TimesheetAdmin extends AdvancedUserRole {

	public static final String DISCRIMINATOR = "TimesheetAdmin";
	
	@Override
	protected String getRole() {
		return TimesheetAdmin.DISCRIMINATOR;
	}
}
