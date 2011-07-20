package freedays.domain;

import javax.persistence.DiscriminatorValue;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity

@DiscriminatorValue(RequestGranter.DISCRIM)
public class RequestGranter extends AdvancedUserRole {

	public static final String DISCRIM = "RequestGranter";
	public String toString() {
        StringBuilder sb = new StringBuilder();
//        sb.append("AppRegUsers: ").append(getAppRegUsers() == null ? "null" : getAppRegUsers().size()).append(", ");
//        sb.append("Id: ").append(getId()).append(", ");
//        sb.append("Version: ").append(getVersion());
        sb.append(RequestGranter.DISCRIM);
        return sb.toString();
    }
}
