package freedays.controller;

import javax.servlet.http.HttpServletRequest;

import freedays.app.FDUser;
import freedays.domain.ApplicationRegularUser;
import freedays.domain.Request;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RooWebScaffold(path = "requests", formBackingObject = Request.class)
@RequestMapping("/requests")
@Controller
public class RequestController {
	
	


	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel, HttpServletRequest httpServletRequest) {
        uiModel.addAttribute("request", new Request());
        String username = httpServletRequest.getUserPrincipal().getName();
        FDUser aru = FDUser.findFDUserByUsername(username);
        uiModel.addAttribute("remainingDaysCount",aru.computeAvailableFreeDays());
        return "requests/create";
    }
}
