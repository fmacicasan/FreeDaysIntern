// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.controller;

import freedays.app.FreeDayRL;
import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect RomanianLegislationController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String RomanianLegislationController.create(@Valid FreeDayRL freeDayRL, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("freeDayRL", freeDayRL);
            addDateTimeFormatPatterns(uiModel);
            return "roleg/create";
        }
        uiModel.asMap().clear();
        freeDayRL.persist();
        return "redirect:/roleg/" + encodeUrlPathSegment(freeDayRL.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String RomanianLegislationController.createForm(Model uiModel) {
        uiModel.addAttribute("freeDayRL", new FreeDayRL());
        addDateTimeFormatPatterns(uiModel);
        return "roleg/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String RomanianLegislationController.show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("freedayrl", FreeDayRL.findFreeDayRL(id));
        uiModel.addAttribute("itemId", id);
        return "roleg/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String RomanianLegislationController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("freedayrls", FreeDayRL.findFreeDayRLEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) FreeDayRL.countFreeDayRLs() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("freedayrls", FreeDayRL.findAllFreeDayRLs());
        }
        addDateTimeFormatPatterns(uiModel);
        return "roleg/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String RomanianLegislationController.update(@Valid FreeDayRL freeDayRL, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("freeDayRL", freeDayRL);
            addDateTimeFormatPatterns(uiModel);
            return "roleg/update";
        }
        uiModel.asMap().clear();
        freeDayRL.merge();
        return "redirect:/roleg/" + encodeUrlPathSegment(freeDayRL.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String RomanianLegislationController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("freeDayRL", FreeDayRL.findFreeDayRL(id));
        addDateTimeFormatPatterns(uiModel);
        return "roleg/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String RomanianLegislationController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        FreeDayRL.findFreeDayRL(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/roleg";
    }
    
    @ModelAttribute("freedayrls")
    public Collection<FreeDayRL> RomanianLegislationController.populateFreeDayRLs() {
        return FreeDayRL.findAllFreeDayRLs();
    }
    
    void RomanianLegislationController.addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("freeDayRL_romanianholiday_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
    }
    
    String RomanianLegislationController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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