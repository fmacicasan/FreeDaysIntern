package freedays.controller;

import freedays.timesheet.Phase;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "phases", formBackingObject = Phase.class)
@RequestMapping("/phases")
@Controller
public class PhaseController {
}
