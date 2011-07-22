package freedays.app;

import javax.persistence.DiscriminatorValue;

import freedays.domain.AdvancedUserRole;
import freedays.domain.ApplicationAdmin;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity
@DiscriminatorValue("FDAdmin")
public class FDAdmin extends AdvancedUserRole {

	public String toString() {
        StringBuilder sb = new StringBuilder();
//        sb.append("AppRegUsers: ").append(getAppRegUsers() == null ? "null" : getAppRegUsers().size()).append(", ");
//        sb.append("Id: ").append(getId()).append(", ");
//        sb.append("Version: ").append(getVersion());
        sb.append(super.toString());
        sb.append("FDAdmin");
        return sb.toString().toUpperCase();
    }
}
