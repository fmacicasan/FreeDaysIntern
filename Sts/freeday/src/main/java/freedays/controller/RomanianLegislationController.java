package freedays.controller;

import freedays.app.FreeDayRL;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "roleg", formBackingObject = FreeDayRL.class)
@RequestMapping("/roleg")
@Controller
public class RomanianLegislationController {
}
