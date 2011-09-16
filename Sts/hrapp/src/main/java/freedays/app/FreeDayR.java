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
import org.springframework.roo.addon.tostring.RooToString;

import freedays.app.form.FreeDayRequest;
import freedays.app.form.FreeDayRequest.RequestType;
import freedays.util.DateUtils;
import freedays.util.PropertiesUtil;
import freedays.util.ValidationUtils;
import freedays.validation.annotation.Weekend;

/**
 * Class that represents a TypeR free day request. Such a request
 * can be made:
 * 	<ul>
 *  <li>on demand if the user worked an extra day (in weekend)</li>
 *  <li>in conjunction with a TypeC waiting request</li>
 * 	</ul>
 * @author fmacicasan
 *
 */
@RooJavaBean
@RooToString
@RooEntity
@DiscriminatorValue("typeR")
public class FreeDayR extends FreeDaysRCMatch {

    //@NotNull
    //@Future removed to solve IN-105 request creation is still restricted from the wrapper but in the backend
    //such entities should be matched so they will be processed some time in the future with an already specified
    //date
//    @Temporal(TemporalType.DATE)
//    @DateTimeFormat(style = "S-")
//    @Weekend
//    private Calendar recoverdate;

    @OneToOne
    private FreeDayC request;
    
//	@Override
//	public Calendar getDate() {
//		return this.getRecoverdate();
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
			this.request = (FreeDayC)match;
		}catch(ClassCastException e){
			throw new IllegalArgumentException("The math argument is invalid: should be FreeDayC");
		}
	}

	@Override
	public FreeDaysRCMatch getMatch() {
		return this.request;
	}
	
	
	/**
	 * Retrieves all the type R unmatchet requests.
	 * @param username
	 * @return
	 */
	public static List<FreeDayR> getAllUnmatchedRequestsByUsername(String username){
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
		EntityManager em = FreeDayR.entityManager();
		TypedQuery<FreeDayR> q = em.createQuery("SELECT o FROM FreeDayR o, Request r WHERE r.requestable = o and r.appreguser.regularUser.username = :username and o.status=:status", FreeDayR.class);
		q.setParameter("username",username);
		q.setParameter("status", FreeDayStatus.WAITING);
		return q.getResultList();
	}

//	@Override
//	protected void setDate(Calendar date) {
//		this.setRecoverdate(date);
//		
//	}
	


	@Override
	public RequestType getType() {
		return RequestType.R;
	}

	@Override
	public String getReportType() {
		//return "Recover";
		return PropertiesUtil.getProperty("freedaysreport_legend_typer");
	}


	@Override
	public boolean customValidationPolicy() {
		return ValidationUtils.checkWeekend(this.getDate());
	}
	
}
