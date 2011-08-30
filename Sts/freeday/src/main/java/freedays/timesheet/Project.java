package freedays.timesheet;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import java.util.Set;
import freedays.timesheet.Phase;
import java.util.HashSet;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;

@RooJavaBean
@RooToString
@RooEntity
public class Project {

    @NotNull
    @Column(unique = true)
    private String code;

    @NotNull
    private String name;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "projectLst")
    private Set<Phase> phaseLst = new HashSet<Phase>();
    
    public void addPhase(Phase newPhase) {
    	phaseLst.add(newPhase);
	}

	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Code: ").append(getCode()).append(", ");
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Name: ").append(getName()).append(", ");
        sb.append("PhaseLst: ").append(getPhaseLst() == null ? "null" : getPhaseLst().size()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
}
