package freedays.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import freedays.domain.RegularUser;
import javax.persistence.ManyToOne;
import java.util.Set;
import freedays.domain.AdvancedUserRole;
import java.util.HashSet;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import freedays.domain.RequestGranter;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import freedays.domain.Request;
import javax.persistence.OneToMany;

@RooJavaBean
@RooToString
@RooEntity(inheritanceType = "TABLE_PER_CLASS")
public abstract class ApplicationRegularUser {

    @ManyToOne
    private RegularUser regularUser;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "AppRegUser_AdvRole", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<AdvancedUserRole> roles = new HashSet<AdvancedUserRole>();

    @ManyToOne
    private ApplicationRegularUser granter;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appreguser")
    private Set<Request> requests = new HashSet<Request>();
}
