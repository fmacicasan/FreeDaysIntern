package freedays.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import freedays.domain.ApplicationRegularUser;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.DiscriminatorType;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Inheritance;
import javax.persistence.ManyToMany;

@RooJavaBean
@RooToString
@RooEntity(inheritanceType="SINGLE_TABLE")
@DiscriminatorColumn(name="roleType",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("AdvancedUserRole")
public class AdvancedUserRole  implements Serializable {

    @ManyToMany(cascade=CascadeType.ALL, mappedBy="roles")
    private Set<ApplicationRegularUser> appRegUsers = new HashSet<ApplicationRegularUser>();

	public String toString() {
        StringBuilder sb = new StringBuilder();
        //sb.append("AppRegUsers: ").append(getAppRegUsers() == null ? "null" : getAppRegUsers().size()).append(", ");
       // sb.append("Id: ").append(getId()).append(", ");
        //sb.append("Version: ").append(getVersion());
        sb.append("ROLE_");
        return sb.toString();
    }
}
