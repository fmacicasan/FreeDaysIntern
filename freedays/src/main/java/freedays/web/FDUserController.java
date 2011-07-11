package freedays.web;

import freedays.app.FDUser;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "fdusers", formBackingObject = FDUser.class)
@RequestMapping("/fdusers")
@Controller
public class FDUserController {
}
