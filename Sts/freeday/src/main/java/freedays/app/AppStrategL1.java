package freedays.app;

import javax.persistence.DiscriminatorValue;
import javax.persistence.TypedQuery;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import freedays.domain.ApplicationRegularUser;
import freedays.domain.ApprovalStrategy;

/**
 * Approval strategy describing a request to the immediate
 * superior. 
 * @see ApprovalStrategy
 * @author fmacicasan
 *
 */
@RooJavaBean
@RooToString
@RooEntity
@DiscriminatorValue("Level1")
public class AppStrategL1 extends ApprovalStrategy {
	
	/**
	 * Returns the immediate granter of the provided user.
	 */
	@Override
	public ApplicationRegularUser getApprover(ApplicationRegularUser user) {
		return user.getGranter();
	}
	
	/**
	 * Obtains the initial strategy in the approval chain corresponding
	 * to a Request for a FreeDay.
	 * @return the initial strategy in the approval chain
	 */
	public static ApprovalStrategy getDefaultInitialStrateg(){
		 TypedQuery<AppStrategL1> q = entityManager().createQuery("SELECT o FROM AppStrategL1 o", AppStrategL1.class);
		 AppStrategL1 res;
			try{
				res=q.getSingleResult();
			}catch(EmptyResultDataAccessException e){
				return null;
			}
			return res;
	}

	


	public String toString() {
        StringBuilder sb = new StringBuilder();
        //sb.append("DefaultInitialStrateg: ").append(getDefaultInitialStrateg()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Succesor: ").append(getSuccesor()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
}
