package freedays.controller;

import java.util.Arrays;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import freedays.app.FDUser;
import freedays.app.RequestStatus;
import freedays.domain.ApplicationRegularUser;
import freedays.domain.ApplicationRegularUser.JobRole;
import freedays.domain.RegularUser;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RooWebScaffold(path = "fdusers", formBackingObject = FDUser.class)
@RequestMapping("/fdusers")
@Controller
public class FDUserController {

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid FDUser FDUser, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("FDUser", FDUser);
			addDateTimeFormatPatterns(uiModel);
			return "fdusers/create";
		}
		uiModel.asMap().clear();
		FDUser.persist();
		return "redirect:/fdusers/"
				+ encodeUrlPathSegment(FDUser.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		FDUser fdu = FDUser.findFDUser(id);
		uiModel.addAttribute("fduser", fdu);
		uiModel.addAttribute("fduser_col", fdu.getRoles());
		uiModel.addAttribute("itemId", id);
		return "fdusers/show";
	}

	@ModelAttribute("applicationregularusers")
	public Collection<ApplicationRegularUser> populateApplicationRegularUsers() {
		return ApplicationRegularUser.findAllRequestGranters();
	}

	@ModelAttribute("regularusers")
	public Collection<RegularUser> populateRegularUsers() {
		return RegularUser.findAllRegularUsersUnasociated();
	}
	
	@ModelAttribute("isRenderable")
	public boolean pupulateIsRenderable(){
		return !populateRegularUsers().isEmpty();
	}
	
    @ModelAttribute("jobroles")
    public Collection<JobRole> populateJobRoles() {
        return Arrays.asList(JobRole.class.getEnumConstants());
    }
}
