package freedays.app;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import freedays.domain.ApplicationRegularUser;
import freedays.domain.RegularUser;
import freedays.domain.Request;
import freedays.validation.annotation.BusinessDay;

/**
 * Class describing a specific FreeDay user.
 * @see ApplicationRegularUser
 * @author fmacicasan
 *
 */
@RooJavaBean
@RooToString
@RooEntity
public class FDUser extends ApplicationRegularUser implements Serializable {

	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;
	
	@NotNull
    //@Past //chose to remove this constraint due to business
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "S-")
	@BusinessDay
    private Calendar hireDate;

    @NotNull
    @Value("0")
    private Integer initDays;

    @NotNull
   // @Min(21L)
    @Value("21")
    private Integer maxFreeDays;
    
    @NotNull
    @Value("6")
    private Integer maxDerogation;

    @Override
	public String toString() {
        StringBuilder sb = new StringBuilder();
//        sb.append("Granter: ").append(getGranter()).append(", ");
//        sb.append("HireDate: ").append(getHireDate() == null ? "null" : getHireDate().getTime()).append(", ");
//        sb.append("Id: ").append(getId()).append(", ");
//        sb.append("InitDays: ").append(getInitDays()).append(", ");
//        sb.append("MaxFreeDays: ").append(getMaxFreeDays()).append(", ");
//        sb.append("RegularUser: ")
        RegularUser ru = this.getRegularUser();
        if(ru==null){
        	sb.append("No approver!");
        } else {
        	sb.append(getRegularUser());
        }
//        sb.append("Roles: ").append(getRoles() == null ? "null" : getRoles().size()).append(", ");
//        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
	
	/**
	 * Computes the remaining legal free days available for request.
	 * A fraction of the maximimFreeDays will be available based on the 
	 * amount of time that passed from the start of the working year 
	 * (if the subject was employed in the current year the hire date
	 * otherwise the beginning of the year). This fraction is adjusted
	 * with a initial bonus value (initDays). Also all active and granted
	 * requests are subtracted from the fraction.
	 * @return the legal free days available for request
	 */
	public Long computeAvailableFreeDays(){
		
		long remainingDays = this.initDays;
		
		Calendar now = Calendar.getInstance();
		long time = now.getTimeInMillis();
		if(now.get(Calendar.YEAR)==hireDate.get(Calendar.YEAR)){
			time -= hireDate.getTimeInMillis();
		} else {
			now.set(Calendar.DAY_OF_YEAR, 1);
			time -= now.getTimeInMillis();
		}

		remainingDays += TimeUnit.MILLISECONDS.toDays(time) * (this.maxFreeDays-this.initDays) / now.getActualMaximum(Calendar.DAY_OF_YEAR);

//		remainingDays -= Request.countActiveRequests(this.getRegularUser().getUsername());
//		remainingDays -= Request.countRequests(this, RequestStatus.GRANTED);
		
		String username = this.getRegularUser().getUsername();
		remainingDays -= FreeDayL.countAllNotFailedRequestsByUsername(username);
		remainingDays -= FreeDayVacation.countAllNotFailedRequestsByUsername(username);
		remainingDays -= FreeDayVacation.sumAllVacationSpansByUsername(username);
		return remainingDays;		
	}
	
	/**
	 * Remaining days from the total amount available in a year without considering the amount of work until
	 * the computation point.
	 * @return
	 */
	public Long computeteAvailableFreeDaysTotal(){
		long remainingDays = this.getMaxFreeDays();
		String username = this.getRegularUser().getUsername();
		remainingDays -= FreeDayL.countAllNotFailedRequestsByUsername(username);
		remainingDays -= FreeDayVacation.countAllNotFailedRequestsByUsername(username); 
		remainingDays -= FreeDayVacation.sumAllVacationSpansByUsername(username);
		return remainingDays;
	}
	
	/**
	 * 
	 * @param username a string representing a RegularUser username
	 * @return its associated FDUser
	 */
	public static FDUser findFDUserByUsername(String username) {
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        EntityManager em = RegularUser.entityManager();
        TypedQuery<FDUser> q = em.createQuery("SELECT o FROM FDUser AS o WHERE o.regularUser.username = :username ", FDUser.class);
        q.setParameter("username", username);
        
        List<FDUser> results = q.getResultList();
        if (!results.isEmpty())
           return  results.get(0);
        else
           return null;
	}
	
	/**
	 * Returns the list of all active FDUsers.
	 * @return
	 */
	//@PostFilter("hasPermission(filterObject, 'list')")
	public static List<FDUser> findAllActiveFDUsers() {
		TypedQuery<FDUser> q = entityManager().createQuery("SELECT o FROM FDUser AS o WHERE o.regularUser.deleted = :deleted ", FDUser.class);
        q.setParameter("deleted", false);
        List<FDUser> results = q.getResultList();
		return results;
	}

	/**
	 * Returns a sublist of the active FDUsers list. The sublist is
	 * bound by the firstResult and maxResults parameter.
	 * 
	 * @param firstResult position of the first retrieved fduser
	 * @param maxResults the maximum number of results to retrieve
	 * @return
	 * @see TypedQuery
	 */
	//@PostFilter("hasPermission(filterObject, 'list')")
	public static List<FDUser> findActiveFDUserEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM FDUser o WHERE o.regularUser.deleted = false", FDUser.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	/**
	 * Returns the number of active FDUsers.
	 * @return
	 */
	public static float countActiveFDUsers() {
		 return entityManager().createQuery("SELECT COUNT(o) FROM FDUser o WHERE o.regularUser.deleted = false", Long.class).getSingleResult();
	}

	/**
	 * Verifies weather or not the FDUser has an associated Regular User.
	 * @param regularUser
	 * @return
	 */
	public static boolean isUnassociated(RegularUser regularUser) {
		try{
			return RegularUser.findAllRegularUsersUnasociated().contains(regularUser);
		}catch(Exception e){
			return false;
		}
	}

	/**
	 * Computes the remaining derogations.
	 * 	NOTE: TODO:count only the company requested days not all the typeC
	 * @return
	 */
	public long computeAvailableDerogations() {
		long derog = this.getMaxDerogation();
		return derog - FreeDay.countAllNotFailedTypeCRequestsByFDUser(this);
	}

	public static void updateFDUser(FDUser fdu) {
        FDUser back = FDUser.findFDUserById(fdu.getId()); 
        System.out.println("id"+fdu.getId());
//      RegularUser ru = back.getRegularUser(); //no more need for this the context gets updated
//      fdu.setRegularUser(ru);
      //: change also the requests for the ex approver to the new one
      //should i send mail to the involved parties ?
        System.out.println("will do check of back");
      if(back.getGranter() != null){
    	  System.out.println("is not null");
	      if((back.getGranter().getId() != fdu.getGranter().getId())){//the granter changed in UI => persistence context marked the change
	      	System.out.println("Testing");
	      	fdu.updateActiveRequests(back.getGranter());
	      	System.out.println("done testing");
	      }
      }
      fdu.merge();
		
	}

	private void updateActiveRequests(ApplicationRegularUser oldGranter) {
		if(oldGranter==null){throw new IllegalArgumentException("The oldGranter argument is required");}
		System.out.println("cucurigu");
		TypedQuery<Request> q = entityManager().createQuery("SELECT o FROM Request AS o WHERE o.appreguser.regularUser.username = :username and o.approver = :granter and o.status NOT IN :finishstates  ", Request.class);
        q.setParameter("granter", oldGranter);
        q.setParameter("finishstates", RequestStatus.getPossibleFinalStatusList());
        System.out.println("ciungus pius"+this.getRegularUser());
        RegularUser ru = this.getRegularUser();
        System.out.println("terminus"+ru);
        q.setParameter("username", ru.getUsername());
        System.out.println("pre cucurigen");
        List<Request> results = q.getResultList();
        System.out.println("opst cucurigen");
        for (Request request : results) {
        	System.out.println("cucuriging");
			request.setApprover(this.getGranter());
			request.merge();
		}
        System.out.println("don cucuring");
	}
	
	public static FDUser findFDUserById(Long id){
		if(id == null) throw new IllegalArgumentException("The id argument is required");
		TypedQuery<FDUser> q = entityManager().createQuery("SELECT o FROM FDUser o JOIN FETCH o.regularUser WHERE o.id = :id",FDUser.class);
		q.setParameter("id", id);
		return q.getSingleResult();
	}
	
	
}
