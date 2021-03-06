package freedays.timesheet;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import freedays.timesheet.Schedule;

@RooJavaBean
@RooEntity
@RooToString
public class Pattern {

    @NotNull
    private Integer noOfHours;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="pattern", fetch = FetchType.EAGER)
    private List<PhaseLabor> phaseLaborLst = new ArrayList<PhaseLabor>();

    @OneToMany(mappedBy = "pattern")
    private Set<Schedule> scheduleLst = new HashSet<Schedule>();
    
    public void addPhL(PhaseLabor x) {
    	phaseLaborLst.add(x);
	}
    public static Pattern getSpecialPattern(String phase, String project, String laborbilling ) {
    	ArrayList<PhaseLabor> phlList = new ArrayList<PhaseLabor>();
    	phlList.add(PhaseLabor.getSpecialPhaseLabor(phase,project,laborbilling));
		Pattern patt = new Pattern();
		patt.setNoOfHours(8);
		patt.setPhaseLaborLst(phlList);
		return patt;
    	
    }
    public PhaseLabor getPhaseLabor(LaborBilling lbS, Phase phS, Project phProject) {
		for(int i = 0; i < phaseLaborLst.size(); i++) {
			if (phaseLaborLst.get(i).getLaborbilling().getId().equals(lbS.getId())) 
			 if (phaseLaborLst.get(i).getPhase().getId().equals(phS.getId()))
				 if (phaseLaborLst.get(i).getProject().getId().equals(phProject.getId())) {
				 return phaseLaborLst.get(i);
			}
		}
		return null;
		
	}

	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("NoOfHours: ").append(getNoOfHours()).append(", ");
	    sb.append("PhaseLaborLst: ").append(getPhaseLaborLst() == null ? "null" : getPhaseLaborLst().size()).append(", ");
	    //sb.append("Schedule: ").append(getSchedule());
	    sb.append("id:").append(getId());
	    return sb.toString();
	}

//	/**
//	 * @return the deletable
//	 */
//	public boolean isDeletable() {
//		return deletable;
//	}
//
//	/**
//	 * @param deletable the deletable to set
//	 */
//	public void setDeletable(boolean deletable) {
//		this.deletable = deletable;
//	}
//	private transient boolean deletable = true;
}
