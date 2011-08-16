package freedays.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import freedays.domain.RegularUser;
import freedays.domain.form.SignupWrapper;
import freedays.security.UserContextService;

/**
 * Controller used to intercept registration related requests
 */
@RequestMapping("/register")
@Controller
@RooJavaBean
public class RegisterController {
	
	@Autowired
	private UserContextService userContextService;
	@Autowired
	private MessageDigestPasswordEncoder messageDigestPasswordEncoder;

	/**
	 * Handler for creating a new regular user (signup procesS)
	 */
	@PreAuthorize("!isAuthenticated()")
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid SignupWrapper regularUser,
			BindingResult bindingResult, Model uiModel) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("regularUser", regularUser);
			uiModel.addAttribute("hasError",true);
			return "register";
		}
		uiModel.asMap().clear();
		String pass = regularUser.getPassword();
		regularUser.setPassword(messageDigestPasswordEncoder.encodePassword(pass, null));
		RegularUser ru = RegularUser.signupnew(regularUser);

		uiModel.addAttribute("regularuser", ru);
        uiModel.addAttribute("itemId", ru.getId());
        return "regularusers/show";

	}

	/**
	 * Handler for retrieving the update form
	 * @param uiModel
	 * @return
	 */
	@PreAuthorize("!isAuthenticated()")
	@RequestMapping(method = RequestMethod.GET)
	public String createForm(Model uiModel) {
		uiModel.addAttribute("regularUser", new SignupWrapper());
		return "register";
	}

}
