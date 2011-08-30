package freedays.timesheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = PhaseLabor.class)
public class PhaseLaborDataOnDemand {
	
	
	@Autowired
    private LaborBillingDataOnDemand laborBillingDataOnDemand;
    
    @Autowired
    private PatternDataOnDemand patternDataOnDemand;
    
    @Autowired
    private PhaseDataOnDemand phaseDataOnDemand;
    
    @Autowired
    private ProjectDataOnDemand projectDataOnDemand;
    
	private List<PhaseLabor> data;
	public void init() {
//        data = PhaseLabor.findPhaseLaborEntries(0, 10);
//        if (data == null) throw new IllegalStateException("Find entries implementation for 'PhaseLabor' illegally returned null");
        
		
//        if (!data.isEmpty()) {
//            return;
//        }
        
        data = new ArrayList<freedays.timesheet.PhaseLabor>();
        for (int i = 0; i < 10; i++) {
            PhaseLabor obj = getNewTransientPhaseLabor(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> it = e.getConstraintViolations().iterator(); it.hasNext();) {
                    ConstraintViolation<?> cv = it.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }

	public void setLaborbilling(PhaseLabor obj, int index) {
        LaborBilling laborbilling = null;//laborBillingDataOnDemand.getSpecificLaborBilling(index);
        obj.setLaborbilling(laborbilling);
    }

	public void setPattern(PhaseLabor obj, int index) {
        Pattern pattern = null;//patternDataOnDemand.getSpecificPattern(index);
        obj.setPattern(pattern);
    }

	public void setPhase(PhaseLabor obj, int index) {
        Phase phase = null;//phaseDataOnDemand.getSpecificPhase(index);
        obj.setPhase(phase);
    }

	public void setProject(PhaseLabor obj, int index) {
        Project project = null;//projectDataOnDemand.getSpecificProject(index);
        obj.setProject(project);
    }
}
