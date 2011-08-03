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
    	sb.append(" int status " + this.getStatus());
    	return sb.toString();
    }
    
    protected abstract Calendar getDate();
    protected abstract void setDate(Calendar date);
	protected abstract FreeDayStatus  getApproveStatus();
	protected abstract void initialize(FreeDayRequest fdr);
	protected abstract void finalizeFail();

	
	public void setInitStatus(){
		this.status = FreeDayStatus.IN_PROGRESS;
	}
	
	public void setFinalApproveStatus(){
		this.status = this.getApproveStatus();
	}
	
	public void setFinalFailStatus(){
		this.status = FreeDayStatus.COMPLETED_FAILURE;
		this.finalizeFail();
	}
	
	protected void setMergedStatus(){
		this.status = FreeDayStatus.COMPLETED_SUCCESS;
	}
	
    public static FreeDay createPersistentFreeDay(FreeDayRequest fdr){
		if (fdr == null) throw new IllegalArgumentException("The FreeDayRequest argument is required");
    	FreeDay fd = FreeDayFactory.create(fdr);
    	fd.initialize(fdr);
    	fd.setInitStatus();
    	fd.setDate(fdr.getReqdate());
    	fd.setReason(fdr.getReason());
    	fd.setApproval(AppStrategL1.getDefaultInitialStrateg());
    	fd.persist();
    	return fd;
    }
}
