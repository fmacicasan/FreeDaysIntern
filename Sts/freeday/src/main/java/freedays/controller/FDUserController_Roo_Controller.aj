// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.controller;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import freedays.domain.AdvancedUserRole;
import freedays.domain.Request;

privileged aspect FDUserController_Roo_Controller {
    
    @ModelAttribute("advanceduserroles")
    public Collection<AdvancedUserRole> FDUserController.populateAdvancedUserRoles() {
        return AdvancedUserRole.findAllAdvancedUserRoles();
    }
    
    @ModelAttribute("requests")
    public Collection<Request> FDUserController.populateRequests() {
        return Request.findAllRequests();
    }
    
    void FDUserController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("FDUser_hiredate_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
    }
    
    String FDUserController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
