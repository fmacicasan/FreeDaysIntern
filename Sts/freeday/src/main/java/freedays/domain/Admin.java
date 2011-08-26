package freedays.domain;

import javax.persistence.DiscriminatorValue;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

/**
 * Class describing a general user administration role.
 * @see AdvancedUserRole
 * @author fmacicasan
 *
 */
@RooJavaBean
@RooEntity
@DiscriminatorValue("Admin")
public class Admin extends AdvancedUserRole {

	@Override
	protected String getRole() {
		return "Admin";
	}

	
}
