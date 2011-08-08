package freedays.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import freedays.domain.RegularUser;
import freedays.util.DAOUtils;

@RequestMapping("/updateRegularUsers")
@Controller
public class UpdateRegularUserController {

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(method = RequestMethod.GET)
	public String createForm(Model uiModel,
			HttpServletRequest httpServletRequest) {
		try{
		RegularUser regularUser = RegularUser.findRegularUsersByUsername(
				httpServletRequest.getUserPrincipal().getName()).getSingleResult();
		
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
}
