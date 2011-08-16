package freedays.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.OneToOne;

/**
 * Abstract class describing a general strategy for approval.
 * It also holds a successor reference used for the approval
 * chain generation. The chain will end when such a reference
 * becomes null.
 * @author fmacicasan
 *
 */
@RooJavaBean
@RooToString
@RooEntity(inheritanceType = "SINGLE_TABLE")
@DiscriminatorColumn(name="strategy_type",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("AdvancedUserRole")
public abstract class ApprovalStrategy {

	/**
	 * The successor in the approval chain of the current strategy.
	 * Null for no successor.
	 */
    @OneToOne
    private freedays.domain.ApprovalStrategy succesor;
    
    /**
     * Abstract method describing the current approval strategy.
     * Given the initial user (the one that makes the Request that 
     * needs approval) based on the child of the ApprovalStrategy
     * an approver is generated.
     * @param user the user that makes the request that needs approval
     * @return the user that is responsible for approving
     */
    public abstract ApplicationRegularUser getApprover(ApplicationRegularUser user);
    
    /**
     * {@inheritDoc}
     */
	public String toString() {
        StringBuilder sb = new StringBuilder();
        //sb.append("Id: ").append(getId()).append(", ");
        //sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
    
	/**
	 * Returns the successor in the approval chain.
	 * Null for no successor.
	 * @return the successor in the approval chain
	 */
	public ApprovalStrategy getSuccesor() {
        return this.succesor;
    }

	/**
	 * 
	 * @param succesor the successor in the approval chain
	 */
	public void setSuccesor(ApprovalStrategy succesor) {
        this.succesor = succesor;
    }
}
