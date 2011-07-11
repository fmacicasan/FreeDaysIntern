package freedays.web;

import freedays.app.FDAdmin;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "fdadmins", formBackingObject = FDAdmin.class)
@RequestMapping("/fdadmins")
@Controller
public class FDAdminController {
}
