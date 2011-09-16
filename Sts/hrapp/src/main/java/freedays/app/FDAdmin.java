package freedays.app;

import javax.persistence.DiscriminatorValue;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.domain.AdvancedUserRole;

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
