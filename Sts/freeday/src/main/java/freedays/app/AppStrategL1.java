package freedays.app;

import javax.persistence.DiscriminatorValue;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import freedays.domain.ApplicationRegularUser;
import freedays.domain.ApprovalStrategy;

@RooJavaBean
@RooToString
@RooEntity
@DiscriminatorValue("Level1")
public class AppStrategL1 extends ApprovalStrategy {

	@Override
	public ApplicationRegularUser getApprover(ApplicationRegularUser user) {
		return user.getGranter();
	}
	
	public static ApprovalStrategy getDefaultInitialStrateg(){
		return entityManager().createQuery("SELECT o FROM AppStrategL1 o", AppStrategL1.class).getSingleResult();
	}

}
