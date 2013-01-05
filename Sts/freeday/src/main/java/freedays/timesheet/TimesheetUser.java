package freedays.timesheet;

import freedays.app.FDUser;
import freedays.domain.ApplicationRegularUser.JobRole;
import freedays.domain.RegularUser;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import freedays.timesheet.Schedule;
import freedays.timesheet.TimesheetUser.Department;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;
import javax.persistence.OneToOne;

@RooJavaBean
@RooEntity
public class TimesheetUser implements Serializable{

	
	private static final String DEFAULT_DEPARTMENT = "Engineering";
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee", fetch = FetchType.EAGER)
    private List<Schedule> scheduleLst = new ArrayList<Schedule>();

    @NotNull
    @OneToOne
    private FDUser fduser;
    
    
    public enum Department{SOFTWARE, ADMINISTRATIV, DATA, LANGUAGE_GATEWAY, LANGUAGE_TECHNOLOGIES, MARKETING, DESKTOP }
    @Enumerated(EnumType.STRING)
    @NotNull
    private Department department; 
    
    
    
    @NotNull
    @Column(name = "teampay")
    private Integer teampay;
    

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
	
	
	public String getDepartmentString(){
		return department.toString();
	}
	
	public Department getDepartment(){
	    return department;
	}
	
    public void setDepartment(Department administrativ) {
       this.department = administrativ;
        
    }
    
    public static List<TimesheetUser> findAllTimesheetUsersByDepartment(Department department2) {
        if (department2 == null) throw new IllegalArgumentException("The department2 argument is required");
        EntityManager em = TimesheetUser.entityManager();
        TypedQuery<TimesheetUser> q = em.createQuery("SELECT t FROM TimesheetUser AS t WHERE t.department = :department and t.fduser.jobrole != :jobRole and t.fduser.regularUser.deleted = :deleted order by t.teampay ASC ", TimesheetUser.class);
        q.setParameter("department", department2);
        q.setParameter("jobRole", JobRole.OBS);
        q.setParameter("deleted", false);        
        List<TimesheetUser> results = q.getResultList();
        return results;
    }
}
