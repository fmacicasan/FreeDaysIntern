package freedays.controller;

import freedays.domain.Admin;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "admins", formBackingObject = Admin.class)
@RequestMapping("/admins")
@Controller
public class AdminController {
}
