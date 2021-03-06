package freedays.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import freedays.domain.RegularUser;
import freedays.domain.form.ResetPass;
import freedays.security.InfoChanger;

/**
 * Controller used to intercept requests related to the password recovery process.
 * @author fmacicasan
 *
 */
@RequestMapping("/recoverpass")
@Controller
public class RecoverPassController {

	/**
	 * Handler for retrieving the reset pass form
	 * @param uiModel
	 * @return
	 */
	@PreAuthorize("!isAuthenticated()")
	@RequestMapping(method=RequestMethod.GET)
	public String createRecoverPass(Model uiModel){
		uiModel.addAttribute("resetpass", new ResetPass());
		return "recoverpass";
	}
	
	@PreAuthorize("!isAuthenticated()")
	@RequestMapping(method = RequestMethod.GET,params="token")
	public String changePassword(@RequestParam("token") String token, Model uiModel){
		System.out.println("opoloniu");
		if(InfoChanger.verifyToken(token)){
			return "changepass";
		}
		return "registerty";
	}
	
	/**
	 * Handler for posting the change password request
	 * @param resetpass
	 * @param bindingResult
	 * @param uiModel
	 * @param httpServletRequest
	 * @return
	 */
	@PreAuthorize("!isAuthenticated()")
	@RequestMapping(method=RequestMethod.POST)
	public String recoverPass(@Valid ResetPass resetpass, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if(bindingResult.hasErrors()){
			uiModel.addAttribute("resetpass",resetpass);
			uiModel.addAttribute("reason", true);
			return "recoverpass";
		}
		System.out.println("i received email:"+resetpass.getEmail().toString());
		RegularUser.resetPassword(resetpass.getEmail());
		//dont send differentiated message on success/failure
		uiModel.addAttribute("reason", true);
		return "recoverpass";
	}
}
