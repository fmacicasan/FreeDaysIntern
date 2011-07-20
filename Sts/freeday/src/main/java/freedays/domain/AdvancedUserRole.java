package freedays.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import freedays.domain.ApplicationRegularUser;
import javax.persistence.DiscriminatorValue;
import javax.persistence.DiscriminatorType;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Inheritance;
@RooJavaBean
@RooToString
@RooEntity(inheritanceType="SINGLE_TABLE")
@DiscriminatorColumn(name="roleType",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("AdvancedUserRole")
public abstract class AdvancedUserRole {

    @ManyToOne
    private ApplicationRegularUser appRegUser;
}
