package freedays.app;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Future;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

import freedays.domain.ApplicationRegularUser;
import freedays.domain.ApprovalStrategy;
import freedays.validation.annotation.BusinessDay;

import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooEntity
public class FreeDay {

    @NotNull
    @Future
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "S-")
    @BusinessDay
    private Calendar requestdate;

    @ManyToOne
    private ApprovalStrategy approval;
    
    private String reason;
    
    public ApplicationRegularUser getApprover(ApplicationRegularUser user){
    	return this.approval.getApprover(user);
    }
    
    public boolean nextApproval(){
    	ApprovalStrategy as = this.approval.getSuccesor();
    	if(as==null)return false;
    	this.approval = as;
    	return true;
    }
    
    public String toString(){
    	StringBuilder sb = new StringBuilder();
    	sb.append(String.format("%1$tA, %1$te %1$tB %1$tY", this.requestdate));
    	sb.append(" reason -> ");
    	sb.append((StringUtils.hasText(this.getReason()))?this.getReason():"none");
    		
    	return sb.toString();
    }
    
    public static FreeDay createPersistentFreeDay(Calendar date, String reason){
		if (date == null) throw new IllegalArgumentException("The date argument is required");
    	FreeDay fd = new FreeDay();
    	fd.setRequestdate(date);
    	fd.setReason(reason);
    	fd.setApproval(AppStrategL1.getDefaultInitialStrateg());
    	fd.persist();
    	return fd;
    }
}
