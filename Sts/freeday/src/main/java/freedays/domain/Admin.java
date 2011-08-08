package freedays.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Class describing a general user administration role.
 * @see AdvancedUserRole
 * @author fmacicasan
 *
 */
@RooJavaBean
@RooToString
@RooEntity
@DiscriminatorValue("Admin")
public class Admin extends AdvancedUserRole {

	public String toString() {
//        StringBuilder sb = new StringBuilder();
////        sb.append("AppRegUsers: ").append(getAppRegUsers() == null ? "null" : getAppRegUsers().size()).append(", ");
////        sb.append("Id: ").append(getId()).append(", ");
////        sb.append("Version: ").append(getVersion());
//        sb.append(super.toString());
//        sb.append("Admin");
//        return sb.toString().toUpperCase();
        return super.toString();
    }

	@Override
	protected String getRole() {
		return "Admin";
	}
}
