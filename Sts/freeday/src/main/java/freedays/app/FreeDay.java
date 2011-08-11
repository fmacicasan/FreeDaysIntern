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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.util.StringUtils;

import freedays.app.FreeDayRequest.RequestType;
import freedays.domain.ApplicationRegularUser;
import freedays.domain.ApprovalStrategy;
import freedays.domain.RegularUser;
import freedays.util.DateUtils;

/**
 * Abstract class representing a Request object
 * for the FreeDay context.
 * @author fmacicasan
 *
 */
@RooJavaBean
@RooToString
@RooEntity(inheritanceType="SINGLE_TABLE")
@DiscriminatorColumn(name="freeDayType",discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue("AbstractFreeDay")
public abstract class FreeDay {
	
    @ManyToOne
    private ApprovalStrategy approval;
    
    private String reason;
    
    /**
     * Returns the current approver based on the given user and the current strategy.
     * @param user the owner of the request associated with this request object
     * @return the approver of the request
     */
    public ApplicationRegularUser getApprover(ApplicationRegularUser user){
    	return this.approval.getApprover(user);
    }
    
    /**
     * Describes the states in which a FreeDay object can be.
     * These states will mark the status of a FreeDay. Such a status
     * is synchronized with the status of a Request.
     * @author fmacicasan
     * @see RequestStatus
     *
     */
    public enum FreeDayStatus {
    	/**
    	 * The FreeDay is associated with a Request that is under the approval
    	 * mechanism either on its own or associated with another complementary
    	 * Request.
    	 */
    	IN_PROGRESS,
    	/**
    	 * The FreeDay is associated with an approved request but is waiting
    	 * for a complementary request to complete successfully.
    	 */
    	WAITING,
    	/**
    	 * The FreeDays is associated with an request that was approved
    	 * and, if it is the case, its associated with a complementary request.
    	 */
    	COMPLETED_SUCCESS, 
    	/**
    	 * The FreeDay is associated with a request that terminated with failure.
    	 * It was either denied or canceled.
    	 */
    	COMPLETED_FAILURE
    };
    @Enumerated
    private FreeDayStatus status;
    
    /**
     * Verifies weather or not there still is a successors
     * in the approval chain for the current approval strategy.
     * If there still is a successor, it becomes the current approval
     * strategy otherwise the approval strategy is set to null.
     * @return true if there still is a successor in the approval chain
     * @see ApprovalStrategy
     */
    public boolean nextApproval(){
    	ApprovalStrategy as = this.approval.getSuccesor();
    	if(as==null)return false;
    	this.approval = as;
    	return true;
    }
    
    /**
     * Verifies weather or not the FreeDay can be canceled.
     * @return true if the FreeDay can be canceled.
     */
    public boolean isCancelable() {
		return this.getDate().after(Calendar.getInstance()); //in future
	}
    
    /**
     * Verifies if the FreeDay can take part in a report.
     * @return true if the freeday can take part in a report
     */
    public boolean isReportable(){
    	return this.getDate().before(Calendar.getInstance()) && //in past
    			(this.getStatus() == FreeDayStatus.WAITING		//status waiting
    				|| this.getStatus() == FreeDayStatus.COMPLETED_SUCCESS); //status completed sucessfull
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
    
    /**
     * Returns a calendar instance registered by a child as being
     * the primary date of the FreeDay.
     * @return the calendar instance
     */
    public abstract Calendar getDate();
    
    /**
     * Sets the primary date of a child instance.
     * @param date
     */
    protected abstract void setDate(Calendar date);
    
    /**
     * Gets the free day status of the FreeDay when the
     * associated Request is approved.
     * @return the free day status after request approval
     */
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
	
	/**
	 * Return the type of a FreeDay child as a RequestType.
	 * @return
	 * @see RequestType
	 */
	protected abstract RequestType getType();

	/**
	 * Sets the initial status. Common for all FreeDays.
	 */
	public void setInitStatus(){
		this.status = FreeDayStatus.IN_PROGRESS;
	}
	
	/**
	 * Sets the final approve status based on child's
	 * implementation.
	 */
	public void setFinalApproveStatus(){
		this.status = this.getApproveStatus();
	}
	
	/**
	 * Sets the final status for failure. Common for all FreeDays.
	 */
	public void setFinalFailStatus(){
		this.status = FreeDayStatus.COMPLETED_FAILURE;
		this.finalizeFail();
	}
	

	/**
	 * Creates a persistent request based on the FreeDayRequest wrapper.
	 * @param fdr the wrapper
	 * @return the created free day
	 * @see FreeDayRequest
	 */
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

    /**
     * Returns a list of FreeDayUserList containing the approved
     * free days for each FDUser in the database. 
     * @return the list
     * @see FDUser
     * @see FreeDayUserList
     */
	public static List<FreeDayUserList> getAllUserFreeDays() {
		List<FreeDayUserList> fdrl = new ArrayList<FreeDayUserList>();
		//TODO: decide weather to make report for all users or only all active users
		List<FDUser> fdul = FDUser.findAllFDUsers();
		for (FDUser fdUser : fdul) {
			fdrl.add(FreeDayUserList.generateFreeDaysList(fdUser));
		}
		return fdrl;
	}

	/**
	 * Generates a list of all the granted free days for a given FDUser
	 * based on his username.
	 * @param username
	 * @return
	 */
	public static List<FreeDay> getAllGrantedFreeDayByUsername(String username) {
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        EntityManager em = RegularUser.entityManager();
        TypedQuery<FreeDay> q = em.createQuery("SELECT o FROM FreeDay o, Request r WHERE r.appreguser.regularUser.username = :username AND r.requestable = o AND o.status IN :approveList ", FreeDay.class);
        q.setParameter("username", username);
        q.setParameter("approveList",FreeDay.getAllGrantedStatus());
        return q.getResultList();
	}
	
	/**
	 * List with all the free day status representing a granted request.
	 * @return
	 */
	public static List<FreeDayStatus> getAllGrantedStatus(){
		List<FreeDayStatus> lfds = new ArrayList<FreeDayStatus>();
		lfds.add(FreeDayStatus.COMPLETED_SUCCESS);
		lfds.add(FreeDayStatus.WAITING);
		return lfds;
	}

	/**
	 * Offers a specialized textual representation for reporting.
	 * @return the special string representation of the free day
	 */
	public List<String> reportPrint() {
		StringBuilder sb = new StringBuilder();
		sb.append(DateUtils.printShortDate(this.getDate()));
		sb.append(this.getType());
		List<String> lst = new LinkedList<String>();
		lst.add(sb.toString());
		return lst;
	}

	/**
     * Returns a list of FreeDayUserList containing the approved
     * vacations for each FDUser in the database. 
     * @return the list
     * @see FDUser
     * @see FreeDayUserList
     */
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
	
	public static List<FreeDayUserList> getAllUserFreeDays(int month){
		List<FreeDayUserList> fdrl = new ArrayList<FreeDayUserList>();
		//TODO: decide weather to make report for all users or only all active users
		List<FDUser> fdul = FDUser.findAllFDUsers();
		for (FDUser fdu : fdul) {
			fdrl.add(FreeDayUserList.generateAllFreeDays(fdu, month));
		}
		return fdrl;
	}
	
	public static List<FreeDay> getAllNotFailedRequestsByUsername(String username){
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
		EntityManager em = FreeDay.entityManager();
		TypedQuery<FreeDay> q = em.createQuery("SELECT o FROM FreeDay o, Request r WHERE r.appreguser.regularUser.username = :username AND r.requestable = o AND o.status != :completedfailure", FreeDay.class);
        q.setParameter("username", username);
        q.setParameter("completedfailure",FreeDayStatus.COMPLETED_FAILURE);
        return q.getResultList(); 
	}
	
	public static Long countAllNotFailedRequestsByUsername(String username){
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
		EntityManager em = FreeDay.entityManager();
		TypedQuery<Long> q = em.createQuery("SELECT COUNT(o) FROM FreeDay o, Request r WHERE r.appreguser.regularUser.username = :username AND r.requestable = o AND o.status != :completedfailure", Long.class);
        q.setParameter("username", username);
        q.setParameter("completedfailure",FreeDayStatus.COMPLETED_FAILURE);
        return q.getSingleResult(); 
	}
	
	public boolean verifyUniqueness(Calendar date){
		if(date == null)  throw new IllegalArgumentException("The date argument is required");
		return this.getDate().equals(date);
	}
	
	
	
	
	
}
