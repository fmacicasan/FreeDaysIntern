package freedays.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import freedays.domain.RegularUser;
import freedays.domain.ResetPass;

@RequestMapping("/recoverpass")
@Controller
public class RecoverPassController {

	@RequestMapping(method=RequestMethod.GET)
	public String createRecoverPass(Model uiModel){
		uiModel.addAttribute("resetpass", new ResetPass());
		return "recoverpass";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String recoverPass(@Valid ResetPass resetpass, BindingResult bindingResult,
			Model uiModel, HttpServletRequest httpServletRequest) {
		if(bindingResult.hasErrors()){
			uiModel.addAttribute("resetpass",resetpass);
			uiModel.addAttribute("reason", false);
			return "recoverpass";
		}
		System.out.println("i received email:"+resetpass.getEmail().toString());
		boolean result = RegularUser.resetPassword(resetpass.getEmail());
		uiModel.addAttribute("reason", result);
		return "recoverpass";
	}
}
