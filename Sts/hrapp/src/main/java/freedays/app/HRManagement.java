 package freedays.app;

import javax.persistence.DiscriminatorValue;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.domain.AdvancedUserRole;

/**
 * Class describing a FreeDay human resources manager role.
 * @see AdvancedUserRole
 * @author fmacicasan
 *
 */
@RooJavaBean
@RooEntity
@DiscriminatorValue("HRManagement")
public class HRManagement extends AdvancedUserRole {

	@Override
	protected String getRole() {
		return "HRManagement";
	}
}
