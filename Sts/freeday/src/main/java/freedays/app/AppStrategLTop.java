package freedays.app;

import javax.persistence.DiscriminatorValue;

import freedays.domain.ApplicationRegularUser;
import freedays.domain.ApprovalStrategy;

@DiscriminatorValue("LevelTop")
public class AppStrategLTop extends ApprovalStrategy {

	@Override
	public ApplicationRegularUser getApprover(ApplicationRegularUser user) {
		ApplicationRegularUser aru;
		for(aru=user;aru.getGranter()!=null;aru=aru.getGranter());
		return aru;
	}

}
