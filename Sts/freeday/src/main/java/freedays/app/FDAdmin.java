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
}
