package freedays.controller;

import java.util.Collection;

import freedays.timesheet.Pattern;
import freedays.timesheet.PhaseLabor;
import freedays.timesheet.Schedule;

import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "patterns", formBackingObject = Pattern.class)
@RequestMapping("/patterns")
@Controller
public class PatternController {
	
	
	@ModelAttribute("phaselabors")
    public Collection<PhaseLabor> populatePhaselabors() {
        return PhaseLabor.findAllUnassignedPhaseLabor();
    }
}
