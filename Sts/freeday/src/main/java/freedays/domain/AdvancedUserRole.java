package freedays.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import freedays.domain.ApplicationRegularUser;
import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooEntity(inheritanceType = "TABLE_PER_CLASS")
public abstract class AdvancedUserRole {

    @ManyToOne
    private ApplicationRegularUser appRegUser;
}
