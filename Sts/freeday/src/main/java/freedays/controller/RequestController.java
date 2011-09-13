package freedays.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;

import javax.validation.Valid;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import freedays.app.FreeDayC;
import freedays.app.FreeDayR;
import freedays.app.FreeDayVacation.ConfidenceLevel;
import freedays.app.form.FreeDayRequest;
import freedays.app.form.FreeDayRequest.RequestType;
import freedays.app.form.FreeDayRequestVacation;
import freedays.domain.Request;
import freedays.security.UserContextService;

/**
 * Controller used to intercept free day requests
 * @author fmacicasan
 *
 */
@RooWebScaffold(path = "requests", formBackingObject = Request.class,update=false,delete=false)
@RequestMapping("/requests")
@Controller
@RooJavaBean
public class RequestController {
	
	@Autowired
	private UserContextService userContextService;
	
	/**
	 * Retrieves the TypeL registration form
	 * @param uiModel
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(params = "form=l", method = RequestMethod.GET)
    public String createForm(Model uiModel) {
        uiModel.addAttribute("reqbean", FreeDayRequest.generateReqFactory(RequestType.L));
        uiModel.addAttribute("typeLMarker",true);
        System.out.println("testing");
        return "requests/create";
    }
	
	/**
	 * Retrieves the typeC registration form
	 * @param uiModel
	 * @param p
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(params = "form=c", method = RequestMethod.GET)
	public String createFormReqC(Model uiModel,Principal p){
		uiModel.addAttribute("reqbean", FreeDayRequest.generateReqFactory(RequestType.C));
		uiModel.addAttribute("matchings",FreeDayR.getAllUnmatchedRequestsByUsername(p.getName()));
		return "requests/create";
	}
	
	/**
	 * Retrieves the typeR registration form
	 * @param uiModel
	 * @param p
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(params = "form=r", method = RequestMethod.GET)
	public String createFormReqR(Model uiModel, Principal p){
		uiModel.addAttribute("reqbean", FreeDayRequest.generateReqFactory(RequestType.R));
		uiModel.addAttribute("matchings",FreeDayC.getAllUnmatchedRequestsByUsername(p.getName()));
		return "requests/create";
	}
	
	/**
	 * Retrieves the typeV registration form
	 * @param uiModel
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(params = "form=v", method = RequestMethod.GET)
	public String createFormReqV(Model uiModel){
		uiModel.addAttribute("reqbean", FreeDayRequest.generateReqFactory(RequestType.V));
		return "requests/vacation";
	}
	
	/**
	 * Handler for free day request creation
	 * @param request
	 * @param bindingResult
	 * @param uiModel
	 * @param p
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid FreeDayRequest request, BindingResult bindingResult, Model uiModel, Principal p) {
        if (bindingResult.hasErrors()) {
        	
        	System.out.println("ciuyciulete");
        	
        	uiModel.addAttribute("hasError",true);
            uiModel.addAttribute("reqbean", request);
            // populate based on type with the corresponding matchings
            switch(request.getReqtype()){
	            case C:
	            	uiModel.addAttribute("matchings",FreeDayR.getAllUnmatchedRequestsByUsername(p.getName()));
	            	uiModel.addAttribute("daytypeerror","BusinessDay");
	            	break;
	            case R:
	            	uiModel.addAttribute("matchings",FreeDayC.getAllUnmatchedRequestsByUsername(p.getName()));
	            	uiModel.addAttribute("daytypeerror","Weekend");
	            	break;
	            case L:
	            	uiModel.addAttribute("typeLMarker", true);
	            	uiModel.addAttribute("daytypeerror","BusinessDay");
	            	break;
	            default:;
	            	uiModel.addAttribute("daytypeerror","BusinessDay(both)");
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
	
	/**
	 * Handler for vacation creation
	 * @param request
	 * @param bindingResult
	 * @param uiModel
	 * @param p
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/vacation", method = RequestMethod.POST)
	public String createVacation(@Valid FreeDayRequestVacation request, BindingResult bindingResult, Model uiModel, Principal p){
		if(bindingResult.hasErrors()){
			uiModel.addAttribute("hasError", true);
			uiModel.addAttribute("reqbean", request);
			uiModel.addAttribute("daytypeerror","BusinessDay(both)");
			return "requests/vacation"; 
		}
		Request.createPersistentReq(request, p.getName());
		uiModel.asMap().clear();
		return "redirect:/requests?own";
	}
	
	/**
	 * Handler for request evaluation upon approval
	 * @param id
	 * @param uiModel
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_REQUESTGRANTER') and hasPermission(#id, 'Request', 'approve')")
	@RequestMapping(value = "/{id}", params = {"eval","approve"}, method = RequestMethod.POST)
	public String evalRequestApprove(@RequestParam("feedback") String feedback,@PathVariable("id") Long id, Model uiModel){
		//System.out.println(feedback);
		Request.approve(id);
		Request.handleFeedback(id,feedback);
		uiModel.asMap().clear();
		return "redirect:/requests?approve";
	}
	
	@PreAuthorize("hasRole('ROLE_REQUESTGRANTER') and hasPermission(#id, 'Request', 'superapprove')")
	@RequestMapping(value = "/{id}", params = {"eval","superapprove"}, method = RequestMethod.POST)
	public String evalRequestSuperApprove(@RequestParam("feedback") String feedback, @PathVariable("id") Long id, Model uiModel){
		Request.superApprove(id);
		Request.handleFeedback(id, feedback);
		uiModel.asMap().clear();
		return "redirect:/requests?superapprove";
	}
	
	/**
	 * Handler for request evaluation upon denial
	 * @param id
	 * @param uiModel
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_REQUESTGRANTER') and hasPermission(#id, 'Request', 'approve')")
	@RequestMapping(value = "/{id}", params = {"eval","deny"}, method = RequestMethod.POST)
	public String evalRequestDeny(@RequestParam("feedback") String feedback, @PathVariable("id") Long id, Model uiModel){
		//System.out.println(feedback);
		Request.deny(id);
		Request.handleFeedback(id,feedback);
		uiModel.asMap().clear();
		return "redirect:/requests?approve";
	}
	
	@PreAuthorize("hasRole('ROLE_REQUESTGRANTER') and hasPermission(#id,'Request','superapprove')")
	@RequestMapping(value = "/{id}", params = {"eval","superdeny"}, method = RequestMethod.POST)
	public String evalRequestSuperDeny(@RequestParam("feedback") String feedback, @PathVariable("id") Long id, Model uiModel){
		Request.superDeny(id);
		Request.handleFeedback(id, feedback);
		uiModel.asMap().clear();
		return "redirect:/request?superapprove";
	}
	
	/**
	 * Handler for request evaluation upon cancelation
	 * @param id
	 * @param uiModel
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_FDADMIN') or hasPermission(#id, 'Request', 'own')")
	@RequestMapping(value = "/{id}", params = {"eval","cancel"}, method = RequestMethod.POST)
	public String evalRequestCancel(@PathVariable("id") Long id, Model uiModel){
		
		Request.cancel(id);
		System.out.println("Eroriuy!");
		
		uiModel.asMap().clear();
		if(this.userContextService.isOwn(Request.findRequest(id).getAppreguser().getRegularUser())){
			return "redirect:/requests?own";
		}
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
    
	/**
	 * Handler for personal request listing
	 * @param id
	 * @param uiModel
	 * @return
	 */
	//will find only intended requests
	@PreAuthorize("isAuthenticated()")
    @RequestMapping(params= "own", method = RequestMethod.GET)
    public String listOwn(Model uiModel, Principal p){
    	uiModel.addAttribute("requests",Request.findAllRequestsByUsername(p.getName()));
    	return "requests/list";
    }
    
	/**
	 * Handler for the listing of requests in need of approval
	 * @param id
	 * @param uiModel
	 * @return
	 */
    @PreAuthorize("hasRole('ROLE_REQUESTGRANTER')")
    @RequestMapping(params= "approve", method = RequestMethod.GET)
    public String listApprove(Model uiModel, Principal p){
    	uiModel.addAttribute("requests",Request.findAllPendingApprovalsByUsername(p.getName()));
    	return "requests/list";
    }
    
    @PreAuthorize("hasRole('ROLE_REQUESTGRANTER') and hasPermission(0,'ApplicationRegularUser','superapprover')")
    @RequestMapping(params="superapprove", method = RequestMethod.GET)
    public String listSuperApprove(Model uiModel){
    	uiModel.addAttribute("requests",Request.findAllPendingSuperApprovalsByUsername(this.userContextService.getCurrentUser()));
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
    @PreAuthorize("hasRole('ROLE_FDADMIN')" +
    		" or hasPermission(#id, 'Request', 'own')" +
    		" or (hasRole('ROLE_REQUESTGRANTER') and hasPermission(#id, 'Request', 'approve'))" +
    		" or (hasRole('ROLE_REQUESTGRANTER') and hasPermission(#id, 'Request', 'superapprove'))")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String show(@PathVariable("id") Long id, Model uiModel, Principal p) {
		Request req = Request.findRequest(id);
        uiModel.addAttribute("request", req);
        uiModel.addAttribute("itemId", id);
        //:(modifed to check if the request is approvable) added req.isCancelable() to isApprover & isPersonal so that we can't approve/deny if the date passed
        boolean isApprover = req.isApprover(p.getName());
        boolean isApprovable = req.isApprovalble();
        boolean isSuperApprover = req.isUltimateApprover(p.getName());
        //System.out.println(isApprover);
        uiModel.addAttribute("isApprover",isApprover);
        uiModel.addAttribute("isApprovable",isApprovable);
        uiModel.addAttribute("isSuperApprover",isSuperApprover);
        uiModel.addAttribute("isGreaterApprover",req.isSuperiorApprover(p.getName()));
        if((isApprover || isSuperApprover) && isApprovable){
        	String username = req.getAppreguser().getRegularUser().getUsername();
        	uiModel.addAttribute("activeRequestCount", Request.countActiveRequests(username));
        	uiModel.addAttribute("remainingDaysCount", Request.computeAvailableFreeDays(username));
        	uiModel.addAttribute("remainingDerogationCount", Request.computeAvailableDerogations(username));
        	uiModel.addAttribute("remainingTotalDaysCount",Request.computeTotalAvailableFreeDays(username));
        }
        uiModel.addAttribute("isPersonal",req.isOwner(p.getName()));
        uiModel.addAttribute("isCancelable",req.isCancelable());
        return "requests/show";
    }

    /**
	 * Model attribute exposed to the view containing the number of active requests of the authenticated user.
	 * @return
	 */
    @ModelAttribute("activeRequestCount")
	public long populateActiveDaysCount(){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return Request.countActiveRequests(username);
	}
    
    /**
	 * Model attribute exposed to the view containing the number of available requests of the authenticated user.
	 * @return
	 */
    @ModelAttribute("remainingDaysCount")
    public long populateRemainingDaysCount(){
    	String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return Request.computeAvailableFreeDays(username);
    }
    
    /**
     * Model attribute exposed to the view containing the number of remaining
     * derogations.
     * @return
     */
    @ModelAttribute("remainingDerogationCount")
    public long populateRemainingDerogationCount(){
    	String username = this.userContextService.getCurrentUser();
    	return Request.computeAvailableDerogations(username);
    }
    
    @ModelAttribute("remainingTotalDaysCount")
    public long populateRemaningTotalDaysCount(){
    	String username = this.userContextService.getCurrentUser();
    	return Request.computeTotalAvailableFreeDays(username);
    }
    
    /**
	 * Model attribute exposed to the view containing the possible request types.
	 * @return
	 */
    @ModelAttribute("requesttypes")
    public Collection<RequestType> populateRequestTypes() {
        return Arrays.asList(RequestType.class.getEnumConstants());
    }
    
    /**
	 * Model attribute exposed to the view containing the possible confidence levels.
	 * @return
	 */
    @ModelAttribute("confidencelevels")
    public Collection<ConfidenceLevel> populateConfidenceLevels(){
    	return Arrays.asList(ConfidenceLevel.class.getEnumConstants());
    }
    
    /**
	 * Model attribute exposed to the view containing the default date format.
	 * @return
	 */
    @ModelAttribute("request_date_format")
    public String populateRequestDateFormat(){
    	return DateTimeFormat.patternForStyle("S-", LocaleContextHolder.getLocale());
    }
    
    

}
