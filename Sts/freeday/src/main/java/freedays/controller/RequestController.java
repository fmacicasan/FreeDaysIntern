package freedays.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import freedays.app.FDUser;
import freedays.domain.ApplicationRegularUser;
import freedays.domain.Request;
import freedays.domain.RequestBean;

import org.joda.time.format.DateTimeFormat;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RooWebScaffold(path = "requests", formBackingObject = Request.class)
@RequestMapping("/requests")
@Controller
public class RequestController {
	
	@RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model uiModel, HttpServletRequest httpServletRequest) {
        uiModel.addAttribute("reqbean", new RequestBean());
        addDateTimeFormatPatterns(uiModel);
        String username = httpServletRequest.getUserPrincipal().getName();
        
        //TODO: place this in FDUser functionality
        FDUser aru = FDUser.findFDUserByUsername(username);
        uiModel.addAttribute("remainingDaysCount",aru.computeAvailableFreeDays());
        uiModel.addAttribute("activeRequestCount",Request.countActiveRequests(aru));
        System.out.println("testing");
        return "requests/request";
    }

	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid RequestBean request, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
        	System.out.println("ciuyciulete");
            uiModel.addAttribute("reqbean", request);
            return "requests/request";
        }
        System.out.println("cacenflitz");
        
        //uiModel.asMap().clear();
        //request.persist();
        Request.createPersistentReq(request.getReqdate(),httpServletRequest.getUserPrincipal().getName());
        return "index";
    }
	
//	@RequestMapping(value = "/{id}", params = "eval", method = RequestMethod.POST)
//	public String evalRequest(@PathVariable("id") Long id,@RequestParam(value="approve") String approve,@RequestParam(value="deny", required=false) String deny, Model uiModel){
//			
//		if(approve != null){
//			Request.approve(id);
//		} else{
//			if(deny != null){
//				Request.deny(id);
//			} else {
//				System.out.println("Eroriuy!");
//			}
//		}
//		
//		return "redirect:/requests?approve";
//	}
	
	@RequestMapping(value = "/{id}", params = {"eval","approve"}, method = RequestMethod.POST)
	public String evalRequestApprove(@PathVariable("id") Long id, Model uiModel){
			
		Request.approve(id);
		System.out.println("Approved!");
		
		uiModel.asMap().clear();
		return "redirect:/requests?approve";
	}
	
	@RequestMapping(value = "/{id}", params = {"eval","deny"}, method = RequestMethod.POST)
	public String evalRequestDeny(@PathVariable("id") Long id, Model uiModel){
		
		Request.deny(id);
		System.out.println("Eroriuy!");
		
		uiModel.asMap().clear();
		return "redirect:/requests?approve";
	}


	@RequestMapping(params = "for", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("request", new Request());
        return "requests/create";
    }
	
	@RequestMapping(params = "form",method = RequestMethod.POST)
    public String create(@Valid Request request, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("request", request);
            return "requests/create";
        }
        uiModel.asMap().clear();
        request.persist();
        return "redirect:/requests/" + encodeUrlPathSegment(request.getId().toString(), httpServletRequest);
    }
	
    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("request_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
    }
    
    @RequestMapping(params= "own", method = RequestMethod.GET)
    public String listOwn(Model uiModel, Principal p){
    	uiModel.addAttribute("requests",Request.findAllRequestsByUsername(p.getName()));
    	return "requests/list";
    }
    
    @Secured({"ROLE_REQUESTGRANTER"})
    @RequestMapping(params= "approve", method = RequestMethod.GET)
    public String listApprove(Model uiModel, Principal p){
    	uiModel.addAttribute("requests",Request.findAllPendingApprovalsByUsername(p.getName()));
    	return "requests/list";
    }
	
	
}
