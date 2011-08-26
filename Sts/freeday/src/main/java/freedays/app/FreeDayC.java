package freedays.app;

import java.util.Calendar;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.EntityManager;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.app.form.FreeDayRequest.RequestType;
import freedays.util.DateUtils;
import freedays.validation.annotation.BusinessDay;

/**
 * Class that represents a TypeC free day request. Such a request
 * can be made:
 * 	<ul>
 *  <li>on demand if the user has available derogations</li>
 *  <li>in conjunction with a TypeR waiting request</li>
 * 	</ul>
 * @author fmacicasan
 *
 */
@RooJavaBean
@RooEntity
@DiscriminatorValue("typeC")
public class FreeDayC extends FreeDaysRCMatch {

    @NotNull
  //@Future removed to solve IN-105 request creation is still restricted from the wrapper but in the backend
    //such entities should be matched so they will be processed some time in the future with an already specified
    //date
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "S-")
    @BusinessDay
    private Calendar requestdate;

    @OneToOne
    private FreeDayR recover;

	@Override
	public Calendar getDate() {
		return this.getRequestdate();
	}

	@Override
	public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Approval: ").append(getApproval()).append(", ");
//        sb.append("Date: ").append(getDate() == null ? "null" : getDate().getTime()).append(", ");
//        sb.append("Id: ").append(getId()).append(", ");
//        sb.append("Reason: ").append(getReason()).append(", ");
//        sb.append("Recover: ").append(getRecover()).append(", ");
//        sb.append("Requestdate: ").append(getRequestdate() == null ? "null" : getRequestdate().getTime()).append(", ");
//        sb.append("Version: ").append(getVersion()).append(", ");
//        sb.append("Cancelable: ").append(isCancelable());
//        return sb.toString();
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
//		sb.append(" C");
		if(this.getMatch()!=null){
			sb.append("/").append(DateUtils.printShortDate(this.getMatch().getDate()));
		}
		return sb.toString();
    }

//	@Override
//	public FreeDayStatus getApproveStatus() {
//		return FreeDayStatus.WAITING;
//	}

//	@Override
//	public boolean match(FreeDayRCMatchable match) {
//		if(this.canMatch() &&  match.canMatch())return false;
//		this.setMatch(match);
//		match.setMatch(this);
//		this.setMergedStatus();
//		match.setMergedStatus();
//		//this.persist();
//		//match.persist();
//		return true;
//	}

	@Override
	public void setMatch(FreeDaysRCMatch match) {
		try{
			this.recover = (FreeDayR) match;
		}catch(ClassCastException e){
			throw new IllegalArgumentException("The math argument is invalid: should be FreeDayR");
		}
		
	}

	@Override
	public FreeDaysRCMatch getMatch() {
		return this.recover;
	}

//	@Override
//	public boolean canMatch() {
//		return this.getMatch() != null;
//	}

	/**
	 * Retrueves all type C unmatched requests of FDUsers associated with
	 * regular users identified by the provided username
	 * @param username
	 * @return
	 */
	public static List<FreeDayC> getAllUnmatchedRequestsByUsername(String username) {
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
		EntityManager em = FreeDayR.entityManager();
		TypedQuery<FreeDayC> q = em.createQuery("SELECT o FROM FreeDayC o, Request r WHERE r.requestable = o and r.appreguser.regularUser.username = :username and o.status=:status", FreeDayC.class);
		q.setParameter("username",username);
		q.setParameter("status", FreeDayStatus.WAITING);
		return q.getResultList();
		//TOOD: think at avoid duplicates at request
	}

	@Override
	protected void setDate(Calendar date) {
		this.setRequestdate(date);
		
	}

	@Override
	public RequestType getType() {
		return RequestType.C;
	}

//	@Override
//	protected void initialize(FreeDayRequest fdr) {
//		this.setMatch(fdr.getMatch());
//		
//	}

//	public static List<FreeDayC> findFreeDayCEntries(int firstResult, int maxResults) {
//        TypedQuery<FreeDayC> tqfdc = entityManager().createQuery("SELECT o FROM FreeDayC o WHERE o.id >= :firstResult AND o.id <= :maxResults", FreeDayC.class);
//        tqfdc.setParameter("firstResult",(long)firstResult);
//        tqfdc.setParameter("maxResults",(long)maxResults);
//        return tqfdc.getResultList();
//    }
}
