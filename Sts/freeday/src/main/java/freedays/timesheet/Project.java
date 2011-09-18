package freedays.timesheet;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;

import java.util.List;
import java.util.Set;

import freedays.timesheet.Phase;
import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.TypedQuery;

@RooJavaBean
@RooEntity
public class Project {

    @NotNull
    @Column(unique = true)
    private String code;

    @NotNull
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "projectLst", fetch = FetchType.EAGER)
    private Set<Phase> phaseLst = new HashSet<Phase>();
    
    public void addPhase(Phase newPhase) {
    	phaseLst.add(newPhase);
	}


	public static Project getSpecialProject(String code) {
		// TODO Auto-generated method stub
		EntityManager em = Project.entityManager();
        TypedQuery<Project> q = em.createQuery("SELECT o FROM Project AS o WHERE o.code = :code ", Project.class);
        q.setParameter("code", code);        
        List<Project> results = q.getResultList();
        if (!results.isEmpty())
           return  results.get(0);
        else
           return null;  
	}


	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Code: ").append(getCode()).append(", ");
        sb.append("Name: ").append(getName()).append(", ");
        sb.append("PhaseLst: ").append(getPhaseLst() == null ? "null" : getPhaseLst().size());
        return sb.toString();
    }
}
