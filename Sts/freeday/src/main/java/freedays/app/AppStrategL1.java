package freedays.app;

import javax.persistence.DiscriminatorValue;

import freedays.domain.ApplicationRegularUser;
import freedays.domain.ApprovalStrategy;
@DiscriminatorValue("Level1")
public class AppStrategL1 extends ApprovalStrategy {

	@Override
	public ApplicationRegularUser getApprover(ApplicationRegularUser user) {
		return user.getGranter();
	}

}
