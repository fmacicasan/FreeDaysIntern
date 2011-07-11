package freedays.web;

import freedays.app.HRManagement;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "hrmanagements", formBackingObject = HRManagement.class)
@RequestMapping("/hrmanagements")
@Controller
public class HRManagementController {
}
