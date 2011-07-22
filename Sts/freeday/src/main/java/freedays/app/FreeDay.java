package freedays.app;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import java.util.Calendar;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Future;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

import freedays.domain.ApplicationRegularUser;
import freedays.domain.ApprovalStrategy;
import freedays.util.ApprovalUtils;

import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooEntity
public class FreeDay {

    @NotNull
    @Future
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Calendar requestdate;

    @ManyToOne
    private ApprovalStrategy approval;
    
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
    	return String.format("%1$tA, %1$te %1$tB %1$tY", this.requestdate);
    }
    
    public static FreeDay createFreeDay(Calendar date){
    	FreeDay fd = new FreeDay();
    	fd.setRequestdate(date);
    	fd.setApproval(ApprovalUtils.getDefaultApprovalStrategy());
    	return fd;
    }
}
