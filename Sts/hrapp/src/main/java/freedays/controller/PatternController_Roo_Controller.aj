// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.controller;

import freedays.timesheet.Pattern;
import freedays.timesheet.PhaseLabor;
import freedays.timesheet.Schedule;
import java.io.UnsupportedEncodingException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect PatternController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String PatternController.create(@Valid Pattern pattern, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("pattern", pattern);
            return "patterns/create";
        }
        uiModel.asMap().clear();
        pattern.persist();
        return "redirect:/patterns/" + encodeUrlPathSegment(pattern.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String PatternController.createForm(Model uiModel) {
        uiModel.addAttribute("pattern", new Pattern());
        return "patterns/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String PatternController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("pattern", Pattern.findPattern(id));
        uiModel.addAttribute("itemId", id);
        return "patterns/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String PatternController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("patterns", Pattern.findPatternEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Pattern.countPatterns() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("patterns", Pattern.findAllPatterns());
        }
        return "patterns/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String PatternController.update(@Valid Pattern pattern, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("pattern", pattern);
            return "patterns/update";
        }
        uiModel.asMap().clear();
        pattern.merge();
        return "redirect:/patterns/" + encodeUrlPathSegment(pattern.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String PatternController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("pattern", Pattern.findPattern(id));
        return "patterns/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String PatternController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Pattern.findPattern(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/patterns";
    }
    
    @ModelAttribute("patterns")
    public Collection<Pattern> PatternController.populatePatterns() {
        return Pattern.findAllPatterns();
    }
    
    @ModelAttribute("phaselabors")
    public Collection<PhaseLabor> PatternController.populatePhaseLabors() {
        return PhaseLabor.findAllPhaseLabors();
    }
    
    @ModelAttribute("schedules")
    public Collection<Schedule> PatternController.populateSchedules() {
        return Schedule.findAllSchedules();
    }
    
    String PatternController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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