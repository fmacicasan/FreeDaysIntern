package freedays.domain;

import javax.persistence.DiscriminatorValue;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

/**
 * Describes a general request granter role.
 * @see AdvancedUserRole
 * @author fmacicasan
 *
 */
@RooJavaBean
@RooEntity
@DiscriminatorValue(RequestGranter.DISCRIM)
public class RequestGranter extends AdvancedUserRole {

	public static final String DISCRIM = "RequestGranter";

	@Override
	protected String getRole() {
		return RequestGranter.DISCRIM;
	}
}
