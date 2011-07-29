package freedays.app;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import freedays.domain.ApplicationRegularUser;
import freedays.domain.RegularUser;
import freedays.domain.Request;
import freedays.validation.annotation.BusinessDay;


@RooJavaBean
@RooToString
@RooEntity
public class FDUser extends ApplicationRegularUser {

	@NotNull
    @Past
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "S-")
	@BusinessDay
    private Calendar hireDate;

    @NotNull
    @Min(2L)
    @Max(7L)
    private Integer initDays;

    @NotNull
    @Min(21L)
    private Integer maxFreeDays;

	public String toString() {
        StringBuilder sb = new StringBuilder();
//        sb.append("Granter: ").append(getGranter()).append(", ");
//        sb.append("HireDate: ").append(getHireDate() == null ? "null" : getHireDate().getTime()).append(", ");
//        sb.append("Id: ").append(getId()).append(", ");
//        sb.append("InitDays: ").append(getInitDays()).append(", ");
//        sb.append("MaxFreeDays: ").append(getMaxFreeDays()).append(", ");
//        sb.append("RegularUser: ")
        sb.append(getRegularUser()).append(" ");
//        sb.append("Roles: ").append(getRoles() == null ? "null" : getRoles().size()).append(", ");
//        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
	
	public long computeAvailableFreeDays(){
		
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

		remainingDays -= Request.countActiveRequests(this.getRegularUser().getUsername());
		remainingDays -= Request.countRequests(this, RequestStatus.GRANTED);

		return remainingDays;
		
	}

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

	public static Collection<FDUser> findAllActiveFDUsers() {
		TypedQuery<FDUser> q = entityManager().createQuery("SELECT o FROM FDUser AS o WHERE o.regularUser.deleted = :deleted ", FDUser.class);
        q.setParameter("deleted", false);
        List<FDUser> results = q.getResultList();
		return results;
	}

	public static List<FDUser> findActiveFDUserEntries(int firstResult, int maxResults) {
		return entityManager().createQuery("SELECT o FROM FDUser o WHERE o.regularUser.deleted = false", FDUser.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}

	public static float countActiveFDUsers() {
		 return entityManager().createQuery("SELECT COUNT(o) FROM FDUser o WHERE o.regularUser.deleted = false", Long.class).getSingleResult();
	}
	
	
}
