package freedays.controller;

import freedays.domain.RegularUser;
import freedays.domain.Search;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
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
	
	@RequestMapping(params = "search", method = RequestMethod.GET)
    public String createSearch(Model uiModel) {
        //uiModel.addAttribute("regularUser", new RegularUser());
        //addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("search",new Search());
		uiModel.addAttribute("searchOptions", RegularUser.getSearchCriteria());
		uiModel.addAttribute("regularusers", RegularUser.findAllRegularUsers());
		addDateTimeFormatPatterns(uiModel);
        return "regularusers/search";
    }
	
	@RequestMapping(params = "search", method = RequestMethod.POST)
    public String doSearch(@Valid Search search, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors() || search.isNotValid()) {
            uiModel.addAttribute("search", search);
            uiModel.addAttribute("searchOptions", RegularUser.getSearchCriteria()); //added on both branches
            addDateTimeFormatPatterns(uiModel);
            return "regularusers/search";
        }
        uiModel.asMap().clear();
        uiModel.addAttribute("search", search);
        uiModel.addAttribute("regularusers", RegularUser.findAllRegularUsersLike(search));
        uiModel.addAttribute("searchOptions", RegularUser.getSearchCriteria());
        addDateTimeFormatPatterns(uiModel);
        return "regularusers/search";
    }
	
//    @RequestMapping(value = "/{id}", params = "search", method = RequestMethod.GET)
//	public String updateSearch(@PathVariable("id") Long id, Model uiModel) {
//	    //uiModel.addAttribute("regularUser", RegularUser.findRegularUser(id));
//	    //addDateTimeFormatPatterns(uiModel);
//	    return "regularusers/search";
//	}
    
    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("regularUser_lastmodified_date_format", "yyyy-MM-dd hh:mm:ss");
        uiModel.addAttribute("regularUser_creationdate_date_format", "yyyy-MM-dd hh:mm:ss");
    }

}
