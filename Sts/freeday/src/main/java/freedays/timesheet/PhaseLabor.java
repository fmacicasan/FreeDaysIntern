package freedays.timesheet;

import java.util.List;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import freedays.timesheet.Project;
import javax.persistence.ManyToOne;
import freedays.timesheet.Phase;
import freedays.timesheet.LaborBilling;
import freedays.timesheet.Pattern;

@RooJavaBean
@RooEntity
public class PhaseLabor {


    @NotNull
    private Float percentage;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Phase phase;

    @ManyToOne
    private LaborBilling laborbilling;

    @ManyToOne
    private Pattern pattern;

	public static PhaseLabor getSpecialPhaseLabor(String phase, String project, String laborbilling) {
		// TODO Auto-generated method stub
		PhaseLabor phlb = new PhaseLabor();
		phlb.percentage = (float) 100;
		phlb.phase = Phase.getSpecialPhase(phase);
		phlb.project = Project.getSpecialProject(project);
		phlb.laborbilling = LaborBilling.getSpecialLaborBilling(laborbilling);
		return phlb;
        }
	
//	public boolean isDeletable() {
//		return deletable;
//	}
//
//	public void setDeletable(boolean deletable) {
//		this.deletable = deletable;
//	}
//
//	private transient boolean deletable=true;
	
	public static List<PhaseLabor> findAllUnassignedPhaseLabor() {
        EntityManager em = PhaseLabor.entityManager();
        TypedQuery<PhaseLabor> q = em.createQuery("SELECT o FROM PhaseLabor AS o WHERE o.pattern is null", PhaseLabor.class);
        return q.getResultList();
	}

	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Laborbilling: ").append(getLaborbilling()).append(", ");
        sb.append("Pattern: ").append(getPattern()).append(", ");
        sb.append("Percentage: ").append(getPercentage()).append(", ");
        sb.append("Phase: ").append(getPhase()).append(", ");
        sb.append("Project: ").append(getProject());
        return sb.toString();
    }
}
