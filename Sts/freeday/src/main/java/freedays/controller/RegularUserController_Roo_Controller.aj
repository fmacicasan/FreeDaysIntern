// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.controller;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import freedays.domain.RegularUser;

privileged aspect RegularUserController_Roo_Controller {
    
	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String RegularUserController.createForm(Model uiModel) {
        uiModel.addAttribute("regularUser", new RegularUser());
        addDateTimeFormatPatterns(uiModel);
        return "regularusers/create";
    }
	
	
	    

    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String RegularUserController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("regularUser", RegularUser.findRegularUser(id));
        addDateTimeFormatPatterns(uiModel);
        return "regularusers/update";
    }
    
    @ModelAttribute("regularusers")
    public Collection<RegularUser> RegularUserController.populateRegularUsers() {
        return RegularUser.findAllRegularUsers();
    }
    
    String RegularUserController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        }
        catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
