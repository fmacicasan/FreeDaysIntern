package freedays.app;


import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.app.FreeDay.FreeDayStatus;
import freedays.app.form.FreeDayRequest;
import freedays.app.form.FreeDayRequest.RequestType;
import freedays.domain.RegularUser;
import freedays.timesheet.ReportLegend;
import freedays.util.PropertiesUtil;

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

   // @NotNull
  //@Future removed to solve IN-105 request creation is still restricted from the wrapper but in the backend
    //such entities should be processed (autoDeny) if the date they represent becomes past
//    @Temporal(TemporalType.DATE)
//    @DateTimeFormat(style = "S-")
//    @BusinessDay
//    private Calendar legalday;
    


//	@Override
//	public Calendar getDate() {
//		return this.getLegalday();
//	}

	@Override
	public FreeDayStatus getApproveStatus() {
		return FreeDayStatus.FINALIZE_SUCCESS;
	}

//	@Override
//	protected void setDate(Calendar date) {
//		this.setLegalday(date);	
//	}

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
	 * 
	 * @param username
	 * @since v1.7
	 * @param year year of counting the days
	 * @return
	 */
	public static Long countAllNotFailedRequestsByUsername(String username, Integer year){
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
		EntityManager em = FreeDay.entityManager();
		TypedQuery<Long> q = em.createQuery("SELECT COUNT(o) FROM FreeDayL o, Request r WHERE r.appreguser.regularUser.username = :username AND r.requestable = o AND o.status != :completedfailure AND o.year = :year", Long.class);
        q.setParameter("username", username);
        q.setParameter("completedfailure",FreeDayStatus.COMPLETED_FAILURE);
        q.setParameter("year", year);
        return q.getSingleResult(); 
	}

	@Override
	public String getReportType() {
		//return "Legal";
		return PropertiesUtil.getProperty("freedaysreport_legend_typel");
	}

//	@Override
//	public boolean customValidationPolicy() {
//		return ValidationUtils.checkBusinessDay(this.getDate());
//	}
	
    public static List<FreeDayL> getAllGrantedFreeDayLByUsername(String username) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        EntityManager em = RegularUser.entityManager();
        TypedQuery<FreeDayL> q = em.createQuery("SELECT o FROM FreeDayL o, Request r WHERE r.appreguser.regularUser.username = :username AND r.requestable = o AND o.status IN :approveList ", FreeDayL.class);
        q.setParameter("username", username);
        q.setParameter("approveList", FreeDayStatus.getAllGrantedStatus());
        return q.getResultList();
    }
    
    //Added for report generation
    @Override
    public boolean hasReportLegend() {
        return true;
    }

    @Override
    public String getReportLegendCode() {
        return ReportLegend.LEGAL.getTerm();
    }
}
