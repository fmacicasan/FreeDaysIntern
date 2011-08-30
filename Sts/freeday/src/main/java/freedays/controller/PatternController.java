package freedays.controller;

import freedays.timesheet.Pattern;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "patterns", formBackingObject = Pattern.class)
@RequestMapping("/patterns")
@Controller
public class PatternController {
}
