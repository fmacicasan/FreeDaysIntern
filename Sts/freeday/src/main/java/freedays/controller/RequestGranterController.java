package freedays.controller;

import freedays.domain.RequestGranter;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RooWebScaffold(path = "requestgranters", formBackingObject = RequestGranter.class)
@RequestMapping("/requestgranters")
@Controller
public class RequestGranterController {

}
