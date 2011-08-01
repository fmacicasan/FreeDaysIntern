package freedays.app;

import java.util.Calendar;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.util.StringUtils;

import freedays.domain.ApplicationRegularUser;
import freedays.domain.ApprovalStrategy;

@RooJavaBean
@RooToString
@RooEntity(inheritanceType="SINGLE_TABLE")
@DiscriminatorColumn(name="freeDayType",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("AbstractFreeDay")
public abstract class FreeDay {
	
    @ManyToOne
    private ApprovalStrategy approval;
    
    private String reason;
    
    public ApplicationRegularUser getApprover(ApplicationRegularUser user){
    	return this.approval.getApprover(user);
    }
    
    public enum FreeDayStatus {IN_PROGRESS,WAITING,COMPLETED_SUCCESS, COMPLETED_FAILURE};
    @Enumerated
    private FreeDayStatus status;
    
    public boolean nextApproval(){
    	ApprovalStrategy as = this.approval.getSuccesor();
    	if(as==null)return false;
    	this.approval = as;
    	return true;
    }
    
    public boolean isCancelable() {
		return this.getDate().after(Calendar.getInstance());
	}
    
    public String toString(){
    	StringBuilder sb = new StringBuilder();
    	sb.append(String.format("%1$tA, %1$te %1$tB %1$tY", this.getDate()));
    	sb.append(" reason -> ");
    	sb.append((StringUtils.hasText(this.getReason()))?this.getReason():"none");
    		
    	return sb.toString();
    }
    
    protected abstract Calendar getDate();
	protected abstract FreeDayStatus  getApproveStatus();
	
	public void setInitStatus(){
		this.status = FreeDayStatus.IN_PROGRESS;
	}
	
	public void setFinalApproveStatus(){
		this.status = this.getApproveStatus();
	}
	public void setFinalFailStatus(){
		this.status = FreeDayStatus.COMPLETED_FAILURE;
	}
	
	protected void setMergedStatus(){
		this.status = FreeDayStatus.COMPLETED_SUCCESS;
	}
}
