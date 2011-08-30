package freedays.timesheet;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
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
}
