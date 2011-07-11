package freedays.web;

import freedays.domain.RegularUser;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "regularusers", formBackingObject = RegularUser.class)
@RequestMapping("/regularusers")
@Controller
public class RegularUserController {
}
