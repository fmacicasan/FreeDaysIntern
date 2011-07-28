package freedays.app;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.TypedQuery;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import freedays.domain.ApplicationRegularUser;
import freedays.domain.ApprovalStrategy;
import freedays.util.DAOUtils;

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
		 TypedQuery<AppStrategL1> q = entityManager().createQuery("SELECT o FROM AppStrategL1 o", AppStrategL1.class);
		 return DAOUtils.getSingleResult(q);
	}

	

}
