package freedays.app;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import java.util.Calendar;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Future;
import javax.persistence.DiscriminatorValue;
import javax.persistence.EntityManager;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.springframework.format.annotation.DateTimeFormat;

import freedays.app.FreeDayRequest.RequestType;
import freedays.domain.RegularUser;
import freedays.util.DateUtils;
import freedays.validation.annotation.Weekend;

@RooJavaBean
@RooToString
@RooEntity
@DiscriminatorValue("typeR")
public class FreeDayR extends FreeDaysRCMatch {

    @NotNull
    @Future
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "S-")
    @Weekend
    private Calendar recoverdate;

    @OneToOne
    private FreeDayC request;
    
	@Override
	public Calendar getDate() {
		return this.getRecoverdate();
	}

	public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Approval: ").append(getApproval()).append(", ");
//        sb.append("Date: ").append(getDate() == null ? "null" : getDate().getTime()).append(", ");
//        sb.append("Id: ").append(getId()).append(", ");
//        sb.append("Reason: ").append(getReason()).append(", ");
//        sb.append("Recoverdate: ").append(getRecoverdate() == null ? "null" : getRecoverdate().getTime()).append(", ");
//        sb.append("Request: ").append(getRequest()).append(", ");
//        sb.append("Version: ").append(getVersion()).append(", ");
//        sb.append("Cancelable: ").append(isCancelable());
//        return sb.toString();
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString());
		sb.append(" R");
		if(this.getMatch()!=null){
			sb.append("/").append(DateUtils.printShortDate(this.getMatch().getDate()));
		}
		
		return sb.toString();
    }

	

//	@Override
//	public boolean match(FreeDayRCMatchable match) {
//		if(match == null)throw new IllegalArgumentException("The username argument is required");
//		if(match.canMatch())return false;
//		//this.setMatch(match);
//		match.setMatch(this);
//		//this.setMergedStatus();
//		match.setMergedStatus();
//		//this.persist();
//		match.persist();
//		return true;
//	}

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

//	@Override
//	public boolean canMatch() {
//		return this.getMatch() != null;
//	}
	
	public static List<FreeDayR> getAllUnmatchedRequestsByUsername(String username){
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
		EntityManager em = FreeDayR.entityManager();
		TypedQuery<FreeDayR> q = em.createQuery("SELECT o FROM FreeDayR o, Request r WHERE r.requestable = o and r.appreguser.regularUser.username = :username and o.status=:status", FreeDayR.class);
		q.setParameter("username",username);
		q.setParameter("status", FreeDayStatus.WAITING);
		return q.getResultList();
	}

	@Override
	protected void setDate(Calendar date) {
		this.setRecoverdate(date);
		
	}
	


	@Override
	protected RequestType getType() {
		return RequestType.R;
	}

	
}
