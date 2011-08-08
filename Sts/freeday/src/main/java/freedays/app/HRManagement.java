package freedays.app;

import javax.persistence.DiscriminatorValue;

import freedays.domain.AdvancedUserRole;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Class describing a FreeDay human resources manager role.
 * @see AdvancedUserRole
 * @author fmacicasan
 *
 */
@RooJavaBean
@RooToString
@RooEntity
@DiscriminatorValue("HRManagement")
public class HRManagement extends AdvancedUserRole {

	public String toString() {
//        StringBuilder sb = new StringBuilder();
////        sb.append("AppRegUsers: ").append(getAppRegUsers() == null ? "null" : getAppRegUsers().size()).append(", ");
////        sb.append("Id: ").append(getId()).append(", ");
////        sb.append("Version: ").append(getVersion());
//        sb.append(super.toString());
//        sb.append("HRManagement");
//        return sb.toString().toUpperCase();
        return super.toString();
    }

	@Override
	protected String getRole() {
		return "HRManagement";
	}
}
