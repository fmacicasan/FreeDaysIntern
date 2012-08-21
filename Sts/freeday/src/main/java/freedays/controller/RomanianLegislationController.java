package freedays.controller;

import freedays.app.FreeDayRL;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RooWebScaffold(path = "roleg", formBackingObject = FreeDayRL.class)
@RequestMapping("/roleg")
@PreAuthorize("hasAnyRole('ROLE_HRMANAGEMENT','ROLE_FDADMIN')")
@Controller
public class RomanianLegislationController {


}
