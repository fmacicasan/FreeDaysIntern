package freedays.controller;

import java.util.ArrayList;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RooWebScaffold(path = "fdusers", formBackingObject = FDUser.class)
@RequestMapping("/fdusers")
@Controller
public class FDUserController {

	@PreAuthorize("hasAnyRole('ROLE_HRMANAGEMENT','ROLE_FDADMIN')")
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
	
	@PreAuthorize("hasAnyRole('ROLE_HRMANAGEMENT','ROLE_FDADMIN') or T(freedays.app.FDUser).findFDUser(#id).regularUser.username == principal.name")
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

	@ModelAttribute("fdusers")
    public Collection<FDUser> populateFDUsers() {
        return FDUser.findAllActiveFDUsers();
    }

	@PreAuthorize("hasAnyRole('ROLE_HRMANAGEMENT','ROLE_FDADMIN')")
	@RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("fdusers", FDUser.findActiveFDUserEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) FDUser.countActiveFDUsers() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("fdusers", FDUser.findAllActiveFDUsers());
        }
        addDateTimeFormatPatterns(uiModel);
        return "fdusers/list";
    }

	@PreAuthorize("hasAnyRole('ROLE_HRMANAGEMENT','ROLE_FDADMIN')")
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		FDUser fdu = FDUser.findFDUser(id);
		Collection<FDUser> col = new ArrayList<FDUser>();
		col.add(fdu);
        uiModel.addAttribute("FDUser", fdu );
        uiModel.addAttribute("regularusers", col);
        addDateTimeFormatPatterns(uiModel);
        return "fdusers/update";
    }
}
