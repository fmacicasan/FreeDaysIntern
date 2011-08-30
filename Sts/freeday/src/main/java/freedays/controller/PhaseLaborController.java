package freedays.controller;

import freedays.timesheet.PhaseLabor;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "phaselabors", formBackingObject = PhaseLabor.class)
@RequestMapping("/phaselabors")
@Controller
public class PhaseLaborController {
}
