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
@RooToString
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

	public static PhaseLabor getVacationPhaseLabor() {
		// TODO Auto-generated method stub
		PhaseLabor phlb = new PhaseLabor();
		phlb.percentage = (float) 100;
		phlb.phase = Phase.getVacationPhase();
		phlb.project = Project.getVacationProject();
		phlb.laborbilling = LaborBilling.getVacationLaborBilling();
		return phlb;
        }
	
	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	private transient boolean deletable=true;
	
	public static List<PhaseLabor> findAllUnassignedPhaseLabor() {
        EntityManager em = PhaseLabor.entityManager();
        TypedQuery<PhaseLabor> q = em.createQuery("SELECT o FROM PhaseLabor AS o WHERE o.pattern is null", PhaseLabor.class);
        return q.getResultList();
	}
}
