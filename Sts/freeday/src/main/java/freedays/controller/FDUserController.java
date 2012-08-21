package freedays.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import freedays.app.FDUser;
import freedays.domain.ApplicationRegularUser;
import freedays.domain.ApplicationRegularUser.JobRole;
import freedays.domain.RegularUser;
import freedays.security.UserContextService;
import freedays.util.MailUtils;

/**
 * Controller used to handle FDUser related requests.
 */
@RooWebScaffold(path = "fdusers", formBackingObject = FDUser.class)
@RequestMapping("/fdusers")
@Controller
public class FDUserController {


	@Autowired
	private UserContextService userContextService;
	
	/**
	 * Handler for the creation of a new FDUser.
	 * @param FDUser
	 * @param bindingResult
	 * @param uiModel
	 * @param httpServletRequest
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_HRMANAGEMENT','ROLE_FDADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid FDUser FDUser, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("FDUser", FDUser);
			addDateTimeFormatPatterns(uiModel);
			return "fdusers/create";
		}
//		System.out.println(fDUser.getGranter());
//		if(fDUser.getGranter() == null || this.defaultGranter.equals(fDUser.getGranter())){
//			System.out.println("is equal");
//			fDUser.setGranter(null);
//		}
//		System.out.println(fDUser);
//		fDUser.persist();
		uiModel.asMap().clear();
		FDUser.persist();
		RegularUser ru = FDUser.getRegularUser();
		MailUtils.sendPostRegisterProcessing(ru.getFullName(),ru.getEmail());
		return "redirect:/fdusers/"
				+ encodeUrlPathSegment(FDUser.getId().toString(),
						httpServletRequest);
	}




	
	/**
	 * Handler for the display of a FDUSer based on his identifier
	 * @param id
	 * @param uiModel
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_HRMANAGEMENT','ROLE_FDADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		FDUser fdu = FDUser.findFDUser(id);
		uiModel.addAttribute("fduser", fdu);
		uiModel.addAttribute("fduser_col", fdu.getRoles());
		uiModel.addAttribute("itemId", id);
		uiModel.addAttribute("noGranter",false);
		return "fdusers/show";
	}

	/**
	 * Model attribute exposed to the view containing all the application regular users.
	 * @return
	 */
	@ModelAttribute("applicationregularusers")
	public Collection<ApplicationRegularUser> populateApplicationRegularUsers() {
		return ApplicationRegularUser.findAllRequestGranters();
	}

	/**
	 * Model attribute exposed to the view containing all the regular user that have
	 * no associated FDUser yet.
	 * @return
	 */
	@ModelAttribute("regularusers")
	public Collection<RegularUser> populateRegularUsers() {
		return RegularUser.findAllRegularUsersUnasociated();
	}
	
	/**
	 * Model attribute exposed to the view deciding weather or not new FDUser-s can be created.
	 * @return
	 */
	@ModelAttribute("isRenderable")
	public boolean pupulateIsRenderable(){
		return !populateRegularUsers().isEmpty();
	}
	
	/**
	 * Model attribute exposed to the view containing all the possible job roles.
	 * @return
	 */
    @ModelAttribute("jobroles")
    public Collection<JobRole> populateJobRoles() {
        return Arrays.asList(JobRole.class.getEnumConstants());
    }

    /**
	 * Model attribute exposed to the view containing all the active FDUsers.
	 * @return
	 */
	@ModelAttribute("fdusers")
    public Collection<FDUser> populateFDUsers() {
        return FDUser.findAllActiveFDUsers();
    }

	/**
	 * Handler for listing the FDUsers
	 * @return
	 */
	@PreAuthorize("hasAnyRole('ROLE_HRMANAGEMENT','ROLE_FDADMIN')")
	@RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        List<FDUser> lfu;
		if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            lfu = FDUser.findActiveFDUserEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo);
            float nrOfPages = (float) FDUser.countActiveFDUsers() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            lfu = FDUser.findAllActiveFDUsers();
        }
        
      //process deletable
    	evaluateDeletable(lfu);
    	uiModel.addAttribute("fdusers", lfu);
        addDateTimeFormatPatterns(uiModel);
        return "fdusers/list";
    }
	
	private void evaluateDeletable(List<FDUser> lfu){
		for (FDUser fu : lfu) {
			if(fu.getRegularUser().getUsername().equals(this.userContextService.getCurrentUser())){
				fu.setDeletable(false);
			}
		}
	}

	/**
	 * Handler for the update of a FDUser
	 * @return
	 */
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

	@PreAuthorize("hasAnyRole('ROLE_HRMANAGEMENT','ROLE_FDADMIN')")
	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid FDUser fdu, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("FDUser", fdu);
            addDateTimeFormatPatterns(uiModel);
            return "fdusers/update";
        }
        uiModel.asMap().clear();
        FDUser actual = FDUser.findFDUser(fdu.getId());
        fdu.setRegularUser(actual.getRegularUser());
        FDUser.updateFDUser(fdu);

       
        return "redirect:/fdusers/" + encodeUrlPathSegment(fdu.getId().toString(), httpServletRequest);
    }

	@PreAuthorize("hasRole('ROLE_HRMANAGEMENT') or hasRole('ROLE_FDADMIN')")
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("FDUser", new FDUser());
        addDateTimeFormatPatterns(uiModel);
        return "fdusers/create";
    }

	@PreAuthorize("hasAnyRole('ROLE_HRMANAGEMENT','ROLE_FDADMIN') and !hasPermission(#id,'FDUser', 'own')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        FDUser.findFDUser(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/fdusers";
    }
}
