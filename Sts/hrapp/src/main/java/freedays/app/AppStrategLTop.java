package freedays.app;

import javax.persistence.DiscriminatorValue;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import freedays.domain.ApplicationRegularUser;
import freedays.domain.ApprovalStrategy;

/**
 * Approval strategy describing a request to the highest
 * superior in the hierarchy. 
 * @see ApprovalStrategy
 * @author fmacicasan
 *
 */
@RooJavaBean
@RooToString
@RooEntity
@DiscriminatorValue("LevelTop")
public class AppStrategLTop extends ApprovalStrategy {

	/**
	 * Returns the top granter in the hierarchy.
	 */
	@Override
	public ApplicationRegularUser getApprover(ApplicationRegularUser user) {
		ApplicationRegularUser aru;
		for(aru=user;aru.getGranter()!=null;aru=aru.getGranter());
		return aru;
	}

}
