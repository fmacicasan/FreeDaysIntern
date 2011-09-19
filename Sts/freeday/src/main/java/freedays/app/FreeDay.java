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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.util.StringUtils;
import freedays.app.form.FreeDayRequest;
import freedays.app.form.FreeDayRequest.RequestType;
import freedays.app.form.FreeDayUserList;
import freedays.domain.ApplicationRegularUser;
import freedays.domain.ApprovalStrategy;
import freedays.domain.RegularUser;
import freedays.util.DateUtils;
import freedays.util.ValidationUtils;
import freedays.validation.annotation.FreeDaySpecificDateConstraint;

/**
 * Abstract class representing a Request object
 * for the FreeDay context.
 * @author fmacicasan
 *
 */
@RooJavaBean
@RooToString
@RooEntity(inheritanceType = "SINGLE_TABLE")
@DiscriminatorColumn(name = "freeDayType", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("AbstractFreeDay")
@FreeDaySpecificDateConstraint
public abstract class FreeDay {

    public static final int DEFAULT_MAXIMUM_CANCELATION_HOUR = 22;

    @ManyToOne
    private ApprovalStrategy approval;

    private String reason;

//    @NotNull
//    @Temporal(TemporalType.DATE)
//    @DateTimeFormat(style = "S-")
//    private Calendar date;

    /**
     * Returns the current approver based on the given user and the current strategy.
     * @param user the owner of the request associated with this request object
     * @return the approver of the request
     */
    public ApplicationRegularUser getApprover(ApplicationRegularUser user) {
        return this.approval.getApprover(user);
    }

    public ApplicationRegularUser getNextApprover(ApplicationRegularUser user) {
        return this.approval.getNextApprover(user);
    }

    public ApplicationRegularUser getUltimateApprover(ApplicationRegularUser user) {
        return this.approval.getUltimateApprover(user);
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
		FINALIZE_SUCCESS, 
		
		/**
		 * The FreeDay is associated with a request that
		 * terminated with failure. It was either denied or canceled.
		 */
		COMPLETED_FAILURE;

        /**
    	 * List with all the free day status representing a granted request.
    	 * @return
    	 */
        public static List<FreeDayStatus> getAllGrantedStatus() {
            List<FreeDayStatus> lfds = new ArrayList<FreeDayStatus>();
            lfds.add(FreeDayStatus.FINALIZE_SUCCESS);
            lfds.add(FreeDayStatus.WAITING);
            return lfds;
        }
        
        public static FreeDayStatus getSuccessStatus(){
        	return FreeDayStatus.FINALIZE_SUCCESS;
        }
    }

    ;

    @Enumerated
    private FreeDayStatus status;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Calendar date;

    /**
     * Verifies weather or not there still is a successors
     * in the approval chain for the current approval strategy.
     * If there still is a successor, it becomes the current approval
     * strategy otherwise the approval strategy is set to null.
     * @return true if there still is a successor in the approval chain
     * @see ApprovalStrategy
     */
    public boolean nextApproval() {
        ApprovalStrategy as = this.approval.getSuccesor();
        if (as == null) return false;
        this.approval = as;
        return true;
    }

    /**
     * Verifies weather or not the FreeDay can be canceled.
     * @return true if the FreeDay can be canceled.
     */
    public boolean isCancelable() {
        Calendar now = Calendar.getInstance();
        Calendar date = this.getDate();
        date.set(Calendar.HOUR_OF_DAY, DEFAULT_MAXIMUM_CANCELATION_HOUR);
        return date.compareTo(now) >= 0;
    }

    /**
     * Verifies if the FreeDay can take part in a report.
     * @return true if the freeday can take part in a report
     */
    public boolean isReportable() {
        return this.getDate().before(Calendar.getInstance()) && (this.getStatus() == FreeDayStatus.WAITING || this.getStatus() == FreeDayStatus.FINALIZE_SUCCESS);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nDay:\t").append(String.format("%1$tA, %1$te %1$tB %1$tY", this.getDate()));
        sb.append("\nReason:\t").append((StringUtils.hasText(this.getReason())) ? this.getReason() : "none");
        sb.append("\nType:\t").append(this.getReportType());
        sb.append(" (").append(this.getStatus()).append(")");
        return sb.toString();
    }

    /**
     * Returns a calendar instance registered by a child as being
     * the primary date of the FreeDay.
     * @return the calendar instance
     */
    public Calendar getDate() {
        return this.date;
    }

    /**
     * Sets the primary date of a child instance.
     * @param date
     */
    protected void setDate(Calendar date) {
        this.date = date;
    }

    /**
     * Gets the free day status of the FreeDay when the
     * associated Request is approved.
     * @return the free day status after request approval
     */
    protected abstract FreeDayStatus getApproveStatus();

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
    public abstract RequestType getType();

    public abstract String getReportType();

    /**
	 * Sets the initial status. Common for all FreeDays.
	 */
    public void setInitStatus() {
        this.status = FreeDayStatus.IN_PROGRESS;
    }

    /**
	 * Sets the final approve status based on child's
	 * implementation.
	 */
    public void setFinalApproveStatus() {
        this.status = this.getApproveStatus();
    }

    /**
	 * Sets the final status for failure. Common for all FreeDays.
	 */
    public void setFinalFailStatus() {
        this.status = FreeDayStatus.COMPLETED_FAILURE;
        this.finalizeFail();
    }

    /**
	 * Creates a persistent request based on the FreeDayRequest wrapper.
	 * @param fdr the wrapper
	 * @return the created free day
	 * @see FreeDayRequest
	 */
    public static FreeDay createPersistentFreeDay(FreeDayRequest fdr) {
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
    @Deprecated
    public static List<FreeDayUserList> getAllUserFreeDays() {
        List<FreeDayUserList> fdrl = new ArrayList<FreeDayUserList>();
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
        q.setParameter("approveList", FreeDayStatus.getAllGrantedStatus());
        return q.getResultList();
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
        if (start == null) throw new IllegalArgumentException("the start argument is required");
        if (end == null) throw new IllegalArgumentException("the end argument is required");
        if (start.after(end)) throw new IllegalArgumentException("the start should be before the end");
        List<FreeDayUserList> fdrl = new ArrayList<FreeDayUserList>();
        List<FDUser> fdul = FDUser.findAllFDUsers();
        for (FDUser fdUser : fdul) {
            fdrl.add(FreeDayUserList.generateVacationList(fdUser, start, end));
        }
        return fdrl;
    }

    /**
	 * Retrieves all the free days in a given month
	 * @param month
	 * @return
	 */
    public static List<FreeDayUserList> getAllUserFreeDays(int month) {
        List<FreeDayUserList> fdrl = new ArrayList<FreeDayUserList>();
        List<FDUser> fdul = FDUser.findAllFDUsers();
        for (FDUser fdu : fdul) {
            fdrl.add(FreeDayUserList.generateAllFreeDays(fdu, month));
        }
        return fdrl;
    }

    /**
	 * Retrieves all the free days that are under approval or already approved
	 * that are associated with requests made by a FDUser associated with a 
	 * RegularUser identified by the provided username.
	 * @param username
	 * @return
	 */
    public static List<FreeDay> getAllNotFailedRequestsByUsername(String username) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        EntityManager em = FreeDay.entityManager();
        TypedQuery<FreeDay> q = em.createQuery("SELECT o FROM FreeDay o, Request r WHERE r.appreguser.regularUser.username = :username AND r.requestable = o AND o.status != :completedfailure", FreeDay.class);
        q.setParameter("username", username);
        q.setParameter("completedfailure", FreeDayStatus.COMPLETED_FAILURE);
        return q.getResultList();
    }

    /**
	 * Decides weather or not the provided date can be a date corresponding to a
	 * new free day request.
	 * @param date
	 * @return
	 */
    public boolean verifyUniqueness(Calendar date) {
        if (date == null) throw new IllegalArgumentException("The date argument is required");
        return this.getDate().equals(date);
    }

    /**
	 * Counts all the type C free days that are under approval or already approved
	 * associated with a request made by the provided FDUser
	 * @param fdUser
	 * @return
	 */
    public static long countAllNotFailedTypeCRequestsByFDUser(FDUser fdUser) {
        if (fdUser == null) throw new IllegalArgumentException("The fdUser argument is required");
        EntityManager em = FreeDay.entityManager();
        TypedQuery<Long> q = em.createQuery("SELECT COUNT(o) FROM FreeDayC o, Request r WHERE r.appreguser = :fdUser AND r.requestable = o AND o.status != :completedfailure", Long.class);
        q.setParameter("fdUser", fdUser);
        q.setParameter("completedfailure", FreeDayStatus.COMPLETED_FAILURE);
        return q.getSingleResult();
    }

	public  boolean customValidationPolicy(){
		return ValidationUtils.checkBusinessDay(this.getDate());
	}

	public String getDateReport() {
		return String.format("%1$tA, %1$te %1$tB %1$tY", this.getDate());
	}
}
