package freedays.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import freedays.app.FDUser;
import freedays.domain.RegularUser;
import freedays.security.UserContextService;
import freedays.util.DAOUtils;
import freedays.util.MailUtils;

@RequestMapping("/account")
@Controller
public class AccountController {
	
	@Autowired
	private UserContextService userContextService;

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(method = RequestMethod.GET)
	public String createForm(Model uiModel,
			HttpServletRequest httpServletRequest) {
		try{
		RegularUser regularUser = RegularUser.findRegularUsersByUsername(
				httpServletRequest.getUserPrincipal().getName()).getSingleResult();
		MailUtils mu = new MailUtils();
		uiModel.addAttribute("regularUser", regularUser);
		
		}catch(EmptyResultDataAccessException e){
		}
		return "regularusers/update";
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(method = RequestMethod.POST)
	public String create(HttpServletRequest httpServletRequest) {
		String userID = (String) httpServletRequest.getSession().getAttribute(
				"userID");
		return "redirect:/regularusers/"
				+ encodeUrlPathSegment(userID, httpServletRequest) + "?form";
	}

	private String encodeUrlPathSegment(String pathSegment,
			HttpServletRequest httpServletRequest) {
		String enc = httpServletRequest.getCharacterEncoding();
		if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		try {
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
		} catch (UnsupportedEncodingException uee) {
		}
		return pathSegment;
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="fduser", method = RequestMethod.GET)
	public String viewFDuser(Model uiModel) {	
		
		FDUser fdu = FDUser.findFDUserByUsername(userContextService.getCurrentUser());
		uiModel.addAttribute("fduser", fdu);
		uiModel.addAttribute("fduser_col", fdu.getRoles());
		uiModel.addAttribute("itemId", fdu.getId());
		return "fdusers/show";
	}
}
