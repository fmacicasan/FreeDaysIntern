package freedays.timesheet;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;

import java.util.List;
import java.util.Set;

import freedays.domain.RegularUser;
import freedays.timesheet.Project;
import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.TypedQuery;

@RooJavaBean
@RooEntity
public class Phase {

    @NotNull
    @Column(unique = true)
    private String code;

    @NotNull
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)    
    private Set<Project> projectLst = new HashSet<Project>();
    
    public static Phase getSpecialPhase(String code) {
    	EntityManager em = RegularUser.entityManager();
        TypedQuery<Phase> q = em.createQuery("SELECT o FROM Phase AS o WHERE o.code = :code ", Phase.class);
        q.setParameter("code", code);        
        List<Phase> results = q.getResultList();
        if (!results.isEmpty())
           return  results.get(0);
        else
           return null;

    }

	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Code: ").append(getCode()).append(", ");
        sb.append("Name: ").append(getName()).append(", ");
        //sb.append("ProjectLst: ").append(getProjectLst() == null ? "null" : getProjectLst().size());
        return sb.toString();
    }
}
