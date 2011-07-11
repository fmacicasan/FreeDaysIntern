package freedays.web;

import freedays.domain.RequestGranter;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RooWebScaffold(path = "requestgranters", formBackingObject = RequestGranter.class)
@RequestMapping("/requestgranters")
@Controller
public class RequestGranterController {
}
