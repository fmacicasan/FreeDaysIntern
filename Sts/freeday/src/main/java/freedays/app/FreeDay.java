package freedays.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.TypedQuery;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.util.StringUtils;

import freedays.app.FreeDayRequest.RequestType;
import freedays.domain.ApplicationRegularUser;
import freedays.domain.ApprovalStrategy;
import freedays.domain.RegularUser;
import freedays.util.DateUtils;

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
    
    public boolean isReportable(){
    	return this.getDate().before(Calendar.getInstance()) &&
    			(this.getStatus() == FreeDayStatus.WAITING
    				|| this.getStatus() == FreeDayStatus.COMPLETED_SUCCESS);
    }
    
    public String toString(){
    	StringBuilder sb = new StringBuilder();
    	sb.append(String.format("%1$tA, %1$te %1$tB %1$tY", this.getDate()));
    	sb.append(" reason -> ");
    	sb.append((StringUtils.hasText(this.getReason()))?this.getReason():"none");
    	sb.append(" int status " + this.getStatus());
    	sb.append("of type ").append(this.getType());
    	//sb.append(String.format("%1$te.%1$tb", this.getDate()));
    	return sb.toString();
    }
    
    
    protected abstract Calendar getDate();
    protected abstract void setDate(Calendar date);
	protected abstract FreeDayStatus  getApproveStatus();
	
	/**
	 * Performs custom child initialization needed upon creation
	 * of child instances but not supported via the FreeDay creation
	 * process. Any new member a child defines should be initialized
	 * using this method.
	 * @param fdr the request holding the initialization information
	 */
	protected abstract void initialize(FreeDayRequest fdr);
	
	/**
	 * Performs custom child finalization when a Request fails.
	 * Any specialized fail behavior a child needs to implement
	 * should be implemented using this method.
	 */
	protected abstract void finalizeFail();
	
	
	protected abstract RequestType getType();

	
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

	public static List<FreeDayUserList> getAllUserFreeDays() {
		List<FreeDayUserList> fdrl = new ArrayList<FreeDayUserList>();
		//TODO: decide weather to make report for all users or only all active users
		List<FDUser> fdul = FDUser.findAllFDUsers();
		for (FDUser fdUser : fdul) {
			fdrl.add(FreeDayUserList.generateFreeDaysList(fdUser));
		}
		return fdrl;
	}

	public static List<FreeDay> getAllGrantedFreeDayByUsername(String username) {
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        EntityManager em = RegularUser.entityManager();
        TypedQuery<FreeDay> q = em.createQuery("SELECT o FROM FreeDay o, Request r WHERE r.appreguser.regularUser.username = :username AND r.requestable = o AND o.status IN :approveList ", FreeDay.class);
        q.setParameter("username", username);
        q.setParameter("approveList",FreeDay.getAllGrantedStatus());
        return q.getResultList();
	}
	
	public static List<FreeDayStatus> getAllGrantedStatus(){
		List<FreeDayStatus> lfds = new ArrayList<FreeDayStatus>();
		lfds.add(FreeDayStatus.COMPLETED_SUCCESS);
		lfds.add(FreeDayStatus.WAITING);
		return lfds;
	}

	public List<String> reportPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append(DateUtils.printShortDate(this.getDate()));
		sb.append(this.getType());
		List<String> lst = new LinkedList<String>();
		lst.add(sb.toString());
		return lst;
	}

	public static List<FreeDayUserList> getAllUserVacations(Calendar start, Calendar end) {
		if(start == null)throw new IllegalArgumentException("the start argument is required");
		if(end == null)throw new IllegalArgumentException("the end argument is required");
		if(start.after(end))throw new IllegalArgumentException("the start should be before the end");
		List<FreeDayUserList> fdrl = new ArrayList<FreeDayUserList>();
		//TODO: decide weather to make report for all users or only all active users
		List<FDUser> fdul = FDUser.findAllFDUsers();
		for (FDUser fdUser : fdul) {
			fdrl.add(FreeDayUserList.generateVacationList(fdUser,start,end));
		}
		return fdrl;
	}

}
