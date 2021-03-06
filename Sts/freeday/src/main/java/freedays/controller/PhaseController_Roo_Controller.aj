// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.controller;

import freedays.timesheet.Phase;
import freedays.timesheet.Project;
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

privileged aspect PhaseController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public String PhaseController.create(@Valid Phase phase, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("phase", phase);
            return "phases/create";
        }
        uiModel.asMap().clear();
        phase.persist();
        return "redirect:/phases/" + encodeUrlPathSegment(phase.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String PhaseController.createForm(Model uiModel) {
        uiModel.addAttribute("phase", new Phase());
        return "phases/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String PhaseController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("phase", Phase.findPhase(id));
        uiModel.addAttribute("itemId", id);
        return "phases/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public String PhaseController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("phases", Phase.findPhaseEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Phase.countPhases() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("phases", Phase.findAllPhases());
        }
        return "phases/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public String PhaseController.update(@Valid Phase phase, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("phase", phase);
            return "phases/update";
        }
        uiModel.asMap().clear();
        phase.merge();
        return "redirect:/phases/" + encodeUrlPathSegment(phase.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String PhaseController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("phase", Phase.findPhase(id));
        return "phases/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String PhaseController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Phase.findPhase(id).remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/phases";
    }
    
    @ModelAttribute("phases")
    public Collection<Phase> PhaseController.populatePhases() {
        return Phase.findAllPhases();
    }
    
    @ModelAttribute("projects")
    public Collection<Project> PhaseController.populateProjects() {
        return Project.findAllProjects();
    }
    
    String PhaseController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
