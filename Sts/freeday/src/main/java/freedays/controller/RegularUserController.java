package freedays.controller;

import freedays.util.MailUtils;
import freedays.validation.RegularUserValidator;
import freedays.domain.RegularUser;
import freedays.domain.Search;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

@RooWebScaffold(path = "regularusers", formBackingObject = RegularUser.class)
@RequestMapping("/regularusers")
@Controller
public class RegularUserController {

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(params = "search", method = RequestMethod.GET)
	public String createSearch(Model uiModel) {
		// uiModel.addAttribute("regularUser", new RegularUser());
		// addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("search", new Search());
		uiModel.addAttribute("searchOptions", RegularUser.getSearchCriteria());
		uiModel.addAttribute("regularusers", RegularUser.findAllRegularUsers());
		addDateTimeFormatPatterns(uiModel);
		return "regularusers/search";
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(params = "search", method = RequestMethod.POST)
	public String doSearch(@Valid Search search, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors() || search.isNotValid()) {
			uiModel.addAttribute("search", search);
			uiModel.addAttribute("searchOptions",
					RegularUser.getSearchCriteria()); // added on both branches
			addDateTimeFormatPatterns(uiModel);
			return "regularusers/search";
		}
		uiModel.asMap().clear();
		uiModel.addAttribute("search", search);
		uiModel.addAttribute("regularusers",
				RegularUser.findAllRegularUsersLike(search));
		uiModel.addAttribute("searchOptions", RegularUser.getSearchCriteria());
		addDateTimeFormatPatterns(uiModel);
		//System.out.println("will start");
		//new MailUtils().send("flo.macicasan@gmail.com", "sa nu ana are mere", "Te rog sa nu te duci dupa paine.Sper sa nu ajungem tarziu.Florin");
		//System.out.println("will finish");
		return "regularusers/search";
	}

	// @RequestMapping(value = "/{id}", params = "search", method =
	// RequestMethod.GET)
	// public String updateSearch(@PathVariable("id") Long id, Model uiModel) {
	// //uiModel.addAttribute("regularUser", RegularUser.findRegularUser(id));
	// //addDateTimeFormatPatterns(uiModel);
	// return "regularusers/search";
	// }

	//TODO put date format in a constants file
	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute("regularUser_lastmodified_date_format",
				"hh:mm:ss dd-MM-yyyy");
		uiModel.addAttribute("regularUser_creationdate_date_format",
				"hh:mm:ss dd-MM-yyyy");
	}


    /**
     * Mark user as <i>deleted</i> without physical removal
     * @param id
     * @param page
     * @param size
     * @param uiModel 
     * @return redirection to user listing
     */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        RegularUser.deleteRegularUser(id);
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/regularusers";
    }
	
	@PreAuthorize("hasRole('ROLE_ADMIN') or !isAuthenticated()")
	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid RegularUser regularUser, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("regularUser", regularUser);
            addDateTimeFormatPatterns(uiModel);
            return "regularusers/create";
        }
        uiModel.asMap().clear();
        Principal p = httpServletRequest.getUserPrincipal();
        regularUser.setUsermodifier((p==null)?regularUser.getUsername():p.getName());
       
        if( RegularUserValidator.validate(regularUser) ){
	        regularUser.persist();
	        //return "redirect:/regularusers/" + encodeUrlPathSegment(regularUser.getId().toString(), httpServletRequest);
	        uiModel.addAttribute("regularuser", regularUser);
	        uiModel.addAttribute("itemId", regularUser.getId());
	        return "regularusers/show";
	        }
        else{
        	httpServletRequest.setAttribute("errorMessage","Email already taken."); 
        	uiModel.addAttribute("regularUser", regularUser);
            addDateTimeFormatPatterns(uiModel);
        	return "regularusers/create";
        }
        
	}
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#regularUser,'own')")
	@RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid RegularUser regularUser, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("regularUser", regularUser);
            addDateTimeFormatPatterns(uiModel);
            return "regularusers/update";
        }
        //TODO: should also check the validity of the RegularUser
        uiModel.asMap().clear();
        Principal p = httpServletRequest.getUserPrincipal();
        regularUser.setUsermodifier((p==null)?regularUser.getUsername():p.getName());
        regularUser.merge();
        return "redirect:/regularusers/" + encodeUrlPathSegment(regularUser.getId().toString(), httpServletRequest);
    }



	@PostAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#id,'RegularUser', 'own')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("regularuser", RegularUser.findRegularUser(id));
        uiModel.addAttribute("itemId", id);
        return "regularusers/show";
    }
}
