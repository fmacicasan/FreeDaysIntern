package freedays.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import freedays.app.FDUser;
import freedays.domain.ApplicationRegularUser;
import freedays.domain.Request;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Request request, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("request", request);
            return "requests/create";
        }
        uiModel.asMap().clear();
        request.persist();
        return "redirect:/requests/" + encodeUrlPathSegment(request.getId().toString(), httpServletRequest);
    }


	@RequestMapping(params = "for", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("request", new Request());
        return "requests/create";
    }
}
