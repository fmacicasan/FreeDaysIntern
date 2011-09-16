package freedays.controller;

import freedays.timesheet.LaborBilling;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "laborbillings", formBackingObject = LaborBilling.class)
@RequestMapping("/laborbillings")
@Controller
public class LaborBillingController {
}
