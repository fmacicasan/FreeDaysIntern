package freedays.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import freedays.app.FreeDayRequest.RequestType;
import freedays.domain.RegularUser;
import freedays.util.DateUtils;
import freedays.util.ValidationUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Future;
import javax.persistence.EntityManager;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.springframework.format.annotation.DateTimeFormat;

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
    @Future
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
    protected Calendar getDate() {
        return this.getBeginning();
    }

    @Override
    protected void setDate(Calendar date) {
    	this.setBeginning(date);
    }

    @Override
    protected FreeDayStatus getApproveStatus() {
        return FreeDayStatus.COMPLETED_SUCCESS;
    }
    
    @Override
    protected void finalizeFail() {
    	// nothing special here
    }

    @Override
    protected RequestType getType() {
        return RequestType.V;
    }

	@Override
	protected void initialize(FreeDayRequest fdr) {
		if(!(fdr instanceof FreeDayRequestVacation))throw new IllegalArgumentException("The fdr argument must be of type FreeDayRequestVacation");
		FreeDayRequestVacation fdrv = (FreeDayRequestVacation)fdr;
		this.setConfidence(fdrv.getConfidence());
		this.setSpan(DateUtils.dateDifferenceInDays(fdrv.getReqdate(), fdrv.getFinish()));
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Vacation Request:");
		sb.append(" from->").append(DateUtils.printShortDate(this.getDate()));
		sb.append(" to->").append(DateUtils.printShortDate(DateUtils.dateAddDay(this.getDate(), this.getSpan())));
		return sb.toString();
	}
	
	@Override
	public List<String> reportPrint() {
		List<String> ls = new ArrayList<String>();
		long spn = this.getSpan();
		for(Long i=0L;i<spn;i++){
			Calendar newDate = DateUtils.dateAddDay(this.getDate(),i);
			if(ValidationUtils.checkBusinessDay(newDate)){
				StringBuilder sb = new StringBuilder();
				sb.append(DateUtils.printShortDate(newDate));
				sb.append(RequestType.L);
				ls.add(sb.toString());
			}
		}
		return ls;
	}

	public static List<FreeDayVacation> getAllGrantedVacationsByUsername(String username) {
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        EntityManager em = RegularUser.entityManager();
        TypedQuery<FreeDayVacation> q = em.createQuery("SELECT o FROM FreeDayVacation o, Request r WHERE r.appreguser.regularUser.username = :username AND r.requestable = o AND r.status = :approveStatus ", FreeDayVacation.class);
        q.setParameter("username", username);
        q.setParameter("approveStatus",RequestStatus.GRANTED);
        return q.getResultList();
	}

}
