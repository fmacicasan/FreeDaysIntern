package freedays.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import freedays.app.FDUser;
import freedays.app.FreeDay;
import freedays.app.FreeDayC;
import freedays.app.FreeDayR;
import freedays.app.FreeDayRCMatchable;
import freedays.app.FreeDayRequest;
import freedays.app.FreeDayRequest.RequestType;
import freedays.domain.ApplicationRegularUser;
import freedays.domain.Request;
import freedays.domain.ApplicationRegularUser.JobRole;

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
	
	
	@RequestMapping(params = "form=l", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("reqbean", FreeDayRequest.generateReq(RequestType.L));
        uiModel.addAttribute("typeLMarker",true);
        System.out.println("testing");
        return "requests/create";
    }
	
	@RequestMapping(params = "form=c", method = RequestMethod.GET)
	public String createFormReqC(Model uiModel,Principal p){
		uiModel.addAttribute("reqbean", FreeDayRequest.generateReq(RequestType.C));
		uiModel.addAttribute("matchings",FreeDayR.getAllUnmatchedRequestsByUsername(p.getName()));
		return "requests/create";
	}
	
	@RequestMapping(params = "form=r", method = RequestMethod.GET)
	public String createFormReqR(Model uiModel, Principal p){
		uiModel.addAttribute("reqbean", FreeDayRequest.generateReq(RequestType.R));
		uiModel.addAttribute("matchings",FreeDayC.getAllUnmatchedRequestsByUsername(p.getName()));
		return "requests/create";
	}
		

	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid FreeDayRequest request, BindingResult bindingResult, Model uiModel, Principal p) {
        if (bindingResult.hasErrors()) {
        	
        	System.out.println("ciuyciulete");
        	
        	uiModel.addAttribute("hasError",true);
            uiModel.addAttribute("reqbean", request);
            // populate based on type with the corresponding matchings
            switch(request.getReqtype()){
	            case L:
	            	uiModel.addAttribute("matchings",FreeDayR.getAllUnmatchedRequestsByUsername(p.getName()));
	            	break;
	            case R:
	            	uiModel.addAttribute("matchings",FreeDayC.getAllUnmatchedRequestsByUsername(p.getName()));
	            	break;
	            default:;
            }
            return "requests/create";
        }
        System.out.println("cacenflitz");
        
        //uiModel.asMap().clear();
        //request.persist();
        Request.createPersistentReq(request,p.getName());
        uiModel.asMap().clear();
        return "redirect:/requests?own";
    }
	
	
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
	
	@RequestMapping(value = "/{id}", params = {"eval","cancel"}, method = RequestMethod.POST)
	public String evalRequestCancel(@PathVariable("id") Long id, Model uiModel){
		
		Request.cancel(id);
		System.out.println("Eroriuy!");
		
		uiModel.asMap().clear();
		return "redirect:/requests?approve";
	}
	
//	@RequestMapping(params = "form",method = RequestMethod.POST)
//    public String create(@Valid Request request, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
//        if (bindingResult.hasErrors()) {
//            uiModel.addAttribute("request", request);
//            return "requests/create";
//        }
//        uiModel.asMap().clear();
//        request.persist();
//        return "redirect:/requests/" + encodeUrlPathSegment(request.getId().toString(), httpServletRequest);
//    }
	
//    void addDateTimeFormatPatterns(Model uiModel) {
//        uiModel.addAttribute("request_date_format", DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale()));
//    }
    
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
	
	
    /**
     * Renders a request by id
     * 	- adapted for marking requests of the currently logged user
     * 		REASON: can't user dynamic verification of role in authorize tag
     * @param id
     * @param uiModel
     * @return
     */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel, Principal p) {
		Request req = Request.findRequest(id);
        uiModel.addAttribute("request", req);
        uiModel.addAttribute("itemId", id);
        uiModel.addAttribute("isApprover",req.isApprover(p.getName()));
        uiModel.addAttribute("isPersonal",req.isOwner(p.getName()));
        uiModel.addAttribute("isCancelable",req.isCancelable());
        return "requests/show";
    }
	
	
    @ModelAttribute("activeRequestCount")
	public long populateRemaningDaysCount(){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return Request.countActiveRequests(username);
	}
    
    @ModelAttribute("remainingDaysCount")
    public long populateRemainingDaysCount(){
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return Request.computeAvailableFreeDays(username);
    }
    
    @ModelAttribute("requesttypes")
    public Collection<RequestType> populateRequestTypes() {
        return Arrays.asList(RequestType.class.getEnumConstants());
    }
    
    @ModelAttribute("request_date_format")
    public String populateRequestDateFormat(){
    	return DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale());
    }
    
    

}
