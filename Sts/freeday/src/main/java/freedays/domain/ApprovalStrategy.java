package freedays.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.OneToOne;

@RooJavaBean
@RooToString
@RooEntity(inheritanceType = "SINGLE_TABLE")
@DiscriminatorColumn(name="strategy_type",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("AdvancedUserRole")
public abstract class ApprovalStrategy {

    @OneToOne
    private freedays.domain.ApprovalStrategy succesor;
    
    public abstract ApplicationRegularUser getApprover(ApplicationRegularUser user);
    
}
