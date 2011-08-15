package freedays.app;

import javax.persistence.DiscriminatorValue;

import freedays.domain.AdvancedUserRole;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Class describing a FreeDay administration role.
 * @see AdvancedUserRole
 * @author fmacicasan
 *
 */
@RooJavaBean
@RooEntity
@DiscriminatorValue("FDAdmin")
public class FDAdmin extends AdvancedUserRole {

	@Override
	protected String getRole() {
		return "FDAdmin";
	}
}
