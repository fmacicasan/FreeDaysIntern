package freedays.app;

import java.util.Calendar;

import javax.persistence.DiscriminatorValue;
import javax.persistence.EntityManager;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.app.FreeDay.FreeDayStatus;
import freedays.app.form.FreeDayRequest;
import freedays.app.form.FreeDayRequest.RequestType;
import freedays.validation.annotation.BusinessDay;

/**
 * Class representing a TypeL free day request. Such a 
 * request represents a legal free day and can be made
 * if the user has available legal days.
 * @author fmacicasan
 *
 */
@RooJavaBean
@RooEntity
@DiscriminatorValue("typeL")
public class FreeDayL extends FreeDay {

    @NotNull
  //@Future removed to solve IN-105 request creation is still restricted from the wrapper but in the backend
    //such entities should be processed (autoDeny) if the date they represent becomes past
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "S-")
    @BusinessDay
    private Calendar legalday;
    


	@Override
	public Calendar getDate() {
		return this.getLegalday();
	}
    
//	public String toString() {
////        StringBuilder sb = new StringBuilder();
////        sb.append("Approval: ").append(getApproval()).append(", ");
////        sb.append("Date: ").append(getDate() == null ? "null" : getDate().getTime()).append(", ");
////        sb.append("Id: ").append(getId()).append(", ");
////        sb.append("Legalday: ").append(getLegalday() == null ? "null" : getLegalday().getTime()).append(", ");
////        sb.append("Reason: ").append(getReason()).append(", ");
////        sb.append("Version: ").append(getVersion()).append(", ");
////        sb.append("Cancelable: ").append(isCancelable());
////        return sb.toString();
//		StringBuilder sb = new StringBuilder();
//		sb.append(super.toString());
//		sb.append(" L");
//		return sb.toString();
//    }

	@Override
	public FreeDayStatus getApproveStatus() {
		return FreeDayStatus.FINALIZE_SUCCESS;
	}

	@Override
	protected void setDate(Calendar date) {
		this.setLegalday(date);	
	}

	@Override
	protected void initialize(FreeDayRequest fdr) {
		// nothing special here
	}

	@Override
	protected void finalizeFail() {
		// nothing special here	
	}
	

	@Override
	public RequestType getType() {
		return RequestType.L;
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
		TypedQuery<Long> q = em.createQuery("SELECT COUNT(o) FROM FreeDayL o, Request r WHERE r.appreguser.regularUser.username = :username AND r.requestable = o AND o.status != :completedfailure", Long.class);
        q.setParameter("username", username);
        q.setParameter("completedfailure",FreeDayStatus.COMPLETED_FAILURE);
        return q.getSingleResult(); 
	}

	@Override
	protected String getReportType() {
		return "Legal";
	}
}
