package freedays.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import freedays.app.FDUser;
import freedays.domain.RegularUser;
import freedays.domain.form.ChangePassWrapper;
import freedays.domain.form.UpdateWrapper;
import freedays.security.UserContextService;
import freedays.util.MailUtils;

/**
 * Controller used to intercept account related requests
 * @author fmacicasan
 *
 */
@RequestMapping("/account")
@Controller
@RooJavaBean
public class AccountController {
	
	@Autowired
	private UserContextService userContextService;
	@Autowired
	private MessageDigestPasswordEncoder messageDigestPasswordEncoder;

	
	/**
	 * Populates the account information form.
	 * @param uiModel
	 * @param httpServletRequest
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(method = RequestMethod.GET)
	public String createForm(Model uiModel,
			HttpServletRequest httpServletRequest) {
		try{
			RegularUser regularUser = RegularUser.findRegularUsersByUsername(
					httpServletRequest.getUserPrincipal().getName()).getSingleResult();
			UpdateWrapper uw =new UpdateWrapper();
			uw.setEmail(regularUser.getEmail());
			uw.setFirstname(regularUser.getFirstname());
			uw.setSurename(regularUser.getSurename());
			uw.setUsername(regularUser.getUsername());
			uiModel.addAttribute("regularUser", uw);
		
		}catch(EmptyResultDataAccessException e){
		}
		return "account";
	}

	/**
	 * Persists the changes in the account info.
	 * @param uw
	 * @param bindingResult
	 * @param uiModel
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid UpdateWrapper uw, BindingResult bindingResult, Model uiModel) {
		if(bindingResult.hasErrors()){
			uiModel.addAttribute("regularUser", uw);
			return "account";
		}
		RegularUser ru = RegularUser.findRegularUsersByUsername(uw.getUsername()).getSingleResult();
		ru.update(uw);
		uiModel.asMap().clear();
		return "redirect:/regularusers/"+ru.getId();
	}

	
	/**
	 * Retrieves the FDUser info associated to the currently logged user.
	 * @param uiModel
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="fduser", method = RequestMethod.GET)
	public String viewFDuser(Model uiModel) {	
		
		FDUser fdu = FDUser.findFDUserByUsername(userContextService.getCurrentUser());
		uiModel.addAttribute("fduser", fdu);
		uiModel.addAttribute("fduser_col", fdu.getRoles());
		uiModel.addAttribute("itemId", fdu.getId());
		return "fdusers/show";
	}
	
	/**
	 * Creates a change password form.
	 * @param uiModel
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="changepass", method = RequestMethod.GET)
	public String changePass(Model uiModel){
		uiModel.addAttribute("newpass", new ChangePassWrapper());
		return "changepass";
	}
	
	/**
	 * Update the regular user information related to a password change.
	 * @param newpass
	 * @param bindingResult
	 * @param uiModel
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="changepass",method = RequestMethod.POST)
	public String updatePass(@Valid ChangePassWrapper newpass, BindingResult bindingResult, Model uiModel) {
		if(bindingResult.hasErrors()){
			uiModel.addAttribute("newpass", newpass);
			return "changepass";
		}
		String username = this.userContextService.getCurrentUser();
 		RegularUser ru = RegularUser.findRegularUsersByUsername(username).getSingleResult();
 		String encryptedpass = this.messageDigestPasswordEncoder.encodePassword(newpass.getPassword(), null);
 		newpass.setPassword(encryptedpass);
		ru.update(newpass);
		
		uiModel.asMap().clear();
		return "redirect:/regularusers/"+ru.getId();
	}
}
