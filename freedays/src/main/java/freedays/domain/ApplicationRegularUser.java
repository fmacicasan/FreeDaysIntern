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

@RooJavaBean
@RooToString
@RooEntity
public abstract class ApplicationRegularUser {

    @ManyToOne
    private RegularUser regularUser;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<AdvancedUserRole> roles = new HashSet<AdvancedUserRole>();

    @ManyToOne
    private RegularUser granter;
}
