package freedays.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import freedays.app.FDUser;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RooWebScaffold(path = "fdusers", formBackingObject = FDUser.class)
@RequestMapping("/fdusers")
@Controller
public class FDUserController {
	
    @RequestMapping(method = RequestMethod.POST)
    public String create(@Valid FDUser FDUser, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("FDUser", FDUser);
            addDateTimeFormatPatterns(uiModel);
            return "fdusers/create";
        }
        System.out.println("testing gogo");
        uiModel.asMap().clear();
        FDUser.persist();
        return "redirect:/fdusers/" + encodeUrlPathSegment(FDUser.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel) {
        addDateTimeFormatPatterns(uiModel);
        System.out.println("cucurigu");
        System.out.println("cucurigu");
        FDUser fdu = FDUser.findFDUser(id);
        uiModel.addAttribute("fduser",fdu );
        System.out.println("Roles for the dude"+fdu.getRoles().size());
        uiModel.addAttribute("fduser_col", fdu.getRoles());
        uiModel.addAttribute("itemId", id);
        return "fdusers/show";
    }
}
