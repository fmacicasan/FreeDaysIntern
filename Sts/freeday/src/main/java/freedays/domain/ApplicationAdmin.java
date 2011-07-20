package freedays.domain;

import javax.persistence.DiscriminatorValue;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@DiscriminatorValue("ApplicationAdmin")
public abstract class ApplicationAdmin extends AdvancedUserRole {

	public String toString() {
        StringBuilder sb = new StringBuilder();
//        sb.append("AppRegUsers: ").append(getAppRegUsers() == null ? "null" : getAppRegUsers().size()).append(", ");
//        sb.append("Id: ").append(getId()).append(", ");
//        sb.append("Version: ").append(getVersion());
        sb.append("AppAdmin");
        return sb.toString();
    }
}
