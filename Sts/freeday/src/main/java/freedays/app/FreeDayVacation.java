package freedays.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.EntityManager;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import freedays.app.FreeDay.FreeDayStatus;
import freedays.app.form.FreeDayRequest;
import freedays.app.form.FreeDayRequest.RequestType;
import freedays.app.form.FreeDayRequestVacation;
import freedays.util.DateUtils;
import freedays.util.ValidationUtils;

/**
 * Class representing a TypeV request. Such a request
 * represents a vacation taken between two business days. 
 * @author fmacicasan
 *
 */
@RooJavaBean
@RooToString
@RooEntity
@DiscriminatorValue("typeV")
public class FreeDayVacation extends FreeDay {

    @NotNull
  //@Future removed to solve IN-105 request creation is still restricted from the wrapper but in the backend
    //such entities should be matched so they will be processed some time in the future with an already specified
    //date
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "S-")
    private Calendar beginning;

    @NotNull
    private long span;

    /**
     * Describes the priority of the request.
     * @author fmacicasan
     *
     */
    public enum ConfidenceLevel{LOW, MEDIUM, HIGH};
    private ConfidenceLevel confidence;

    @Override
    public Calendar getDate() {
        return this.getBeginning();
    }

    @Override
    protected void setDate(Calendar date) {
    	this.setBeginning(date);
    }

    @Override
    protected FreeDayStatus getApproveStatus() {
        return FreeDayStatus.FINALIZE_SUCCESS;
    }
    
    @Override
    protected void finalizeFail() {
    	// nothing special here
    }

    @Override
    public RequestType getType() {
        return RequestType.V;
    }

	@Override
	protected void initialize(FreeDayRequest fdr) {
		if(!(fdr instanceof FreeDayRequestVacation))throw new IllegalArgumentException("The fdr argument must be of type FreeDayRequestVacation");
		FreeDayRequestVacation fdrv = (FreeDayRequestVacation)fdr;
		this.setConfidence(fdrv.getConfidence());
		this.setSpan(DateUtils.dateDifferenceInWorkingDays(fdrv.getReqdate(), fdrv.getFinish()));
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Vacation Request:");
		sb.append(" from ").append(DateUtils.printShortDate(this.getDate()));
		sb.append(" to ").append(DateUtils.printShortDate(DateUtils.dateAddBusinessDay(this.getDate(), this.getSpan())));
		return sb.toString();
	}
	
	@Override
	public List<String> reportPrint() {
		List<String> ls = new ArrayList<String>();
		long spn = this.getSpan();
		for(Long i=0L;i<spn;i++){
			Calendar newDate = DateUtils.dateAddBusinessDay(this.getDate(),i);
			if(ValidationUtils.checkBusinessDay(newDate)){
				StringBuilder sb = new StringBuilder();
				sb.append(DateUtils.printShortDate(newDate));
				sb.append(RequestType.L);
				ls.add(sb.toString());
			}
		}
		return ls;
	}

	/**
	 * Retrieves all the approved vacations based on the username of their
	 * associated regular user.
	 * @param username
	 * @return
	 */
	public static List<FreeDayVacation> getAllGrantedVacationsByUsername(String username) {
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        EntityManager em = FreeDayVacation.entityManager();
        TypedQuery<FreeDayVacation> q = em.createQuery("SELECT o FROM FreeDayVacation o, Request r WHERE r.appreguser.regularUser.username = :username AND r.requestable = o AND r.status = :approveStatus ", FreeDayVacation.class);
        q.setParameter("username", username);
        q.setParameter("approveStatus",RequestStatus.GRANTED);
        return q.getResultList();
	}
	
	/**
	 * Computes the sum of all the approved/under approval vacation requests
	 * made by a FDUser associated to a regular user identified by the
	 * provided username
	 * @param username
	 * @return
	 */
	public static Long sumAllVacationSpansByUsername(String username){
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
		EntityManager em = FreeDayVacation.entityManager();
		TypedQuery<Long> q = em.createQuery("SELECT SUM(o.span) FROM FreeDayVacation o, Request r WHERE r.appreguser.regularUser.username = :username AND r.requestable = o AND o.status != :completedfailure", Long.class);
        q.setParameter("username", username);
        q.setParameter("completedfailure",FreeDayStatus.COMPLETED_FAILURE);
        Long sum = q.getSingleResult();
        return sum==null?0:sum;
	}
	
	/**
	 * Verifies weather or no the provided calendar instance can represent
	 * a date for a new free day request.
	 */
	@Override
	public boolean verifyUniqueness(Calendar date){
		if(date == null)  throw new IllegalArgumentException("The date argument is required");
		return (this.getDate().before(date) && this.getEnd().after(date))
				|| this.getDate().equals(date)
				|| this.getEnd().equals(date);
	}
	
	/**
	 * COmputes the final date of the associated vacation.
	 * @return
	 */
	public Calendar getEnd(){
		return DateUtils.dateAddBusinessDay(this.getBeginning(), this.getSpan());
	}
	
	/**
	 * Counts all the free days that are under approval or already approved
	 * that are associated with requests made by a FDUser associated with a 
	 * RegularUser identified by the provided username.
	 * @param username
	 * @return
	 */
	public static Long countAllNotFailedRequestsByUsername(String username){
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
		EntityManager em = FreeDay.entityManager();
		TypedQuery<Long> q = em.createQuery("SELECT COUNT(o) FROM FreeDayVacation o, Request r WHERE r.appreguser.regularUser.username = :username AND r.requestable = o AND o.status != :completedfailure", Long.class);
        q.setParameter("username", username);
        q.setParameter("completedfailure",FreeDayStatus.COMPLETED_FAILURE);
        return q.getSingleResult(); 
	}

	@Override
	protected String getReportType() {
		return "Vacation";
	}

}
