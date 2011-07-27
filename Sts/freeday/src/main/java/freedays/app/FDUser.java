package freedays.app;

import freedays.domain.ApplicationRegularUser;
import freedays.domain.RegularUser;
import freedays.domain.Request;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import java.io.Serializable;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.persistence.EntityManager;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

@RooJavaBean
@RooToString
@RooEntity
public class FDUser extends ApplicationRegularUser{


	private static final long serialVersionUID = 1L;

	@NotNull
    @Past
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
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

		remainingDays -= Request.countRequests(this, RequestStatus.GRANTED);

		return remainingDays;
		
	}

	public static FDUser findFDUserByUsername(String username) {
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        EntityManager em = RegularUser.entityManager();
        TypedQuery<FDUser> q = em.createQuery("SELECT o FROM FDUser AS o WHERE o.regularUser.username = :username ", FDUser.class);
        q.setParameter("username", username);
        return q.getSingleResult();
	}
	
	
}
