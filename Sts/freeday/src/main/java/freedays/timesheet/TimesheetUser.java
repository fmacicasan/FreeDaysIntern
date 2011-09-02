package freedays.timesheet;

import freedays.app.FDUser;
import freedays.domain.ApplicationRegularUser.JobRole;
import freedays.domain.RegularUser;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import freedays.timesheet.Schedule;
import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.persistence.OneToOne;

@RooJavaBean
@RooEntity
public class TimesheetUser{

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee", fetch = FetchType.EAGER)
    private List<Schedule> scheduleLst = new ArrayList<Schedule>();

    @NotNull
    @OneToOne
    private FDUser fduser;

    public void test() {
    	fduser.getRegularUser().getFullName();
    	fduser.getJobrole();
    }
    public JobRole getJobrole(){
    	return fduser.getJobrole();
    }
	public RegularUser getRegularUser(){
		return fduser.getRegularUser();
	}
    public void addSchedule(Schedule sch) {
        scheduleLst.add(sch);
    }
	public static TimesheetUser findTimesheetUserByUsername(String currentUser) {
		if (currentUser == null || currentUser.length() == 0) throw new IllegalArgumentException("The username argument is required");
        EntityManager em = RegularUser.entityManager();
        TypedQuery<TimesheetUser> q = em.createQuery("SELECT t FROM TimesheetUser AS t WHERE t.fduser.regularUser.username = :username ", TimesheetUser.class);
        q.setParameter("username", currentUser);        
        List<TimesheetUser> results = q.getResultList();
        if (!results.isEmpty())
           return  results.get(0);
        else
           return null;
	}
	public String toString() {
		return fduser.getRegularUser().getFullName();
	}
}
