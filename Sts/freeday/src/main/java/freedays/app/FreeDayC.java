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
import freedays.util.ValidationUtils;
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

    //@NotNull
  //@Future removed to solve IN-105 request creation is still restricted from the wrapper but in the backend
    //such entities should be matched so they will be processed some time in the future with an already specified
    //date
//    @Temporal(TemporalType.DATE)
//    @DateTimeFormat(style = "S-")
//    @BusinessDay
//    private Calendar requestdate;

    @OneToOne
    private FreeDayR recover;

//	@Override
//	public Calendar getDate() {
//		return this.getRequestdate();
//	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		if(this.getMatch()!=null){
			sb.append("/").append(DateUtils.printShortDate(this.getMatch().getDate()));
		}
		return sb.toString();
    }

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

//	@Override
//	protected void setDate(Calendar date) {
//		this.setRequestdate(date);
//		
//	}

	@Override
	public RequestType getType() {
		return RequestType.C;
	}

	@Override
	protected String getReportType() {
		return "OnDemand";
	}

//	@Override
//	public boolean customValidationPolicy() {
//		return ValidationUtils.checkBusinessDay(this.getDate());
//	}

}
