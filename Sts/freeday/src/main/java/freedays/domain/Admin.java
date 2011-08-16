package freedays.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

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
