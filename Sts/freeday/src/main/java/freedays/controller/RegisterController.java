package freedays.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import freedays.domain.RegularUser;

@RequestMapping("/register")
@Controller
public class RegisterController {

	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid RegularUser regularUser,
			BindingResult bindingResult, Model uiModel,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("regularUser", regularUser);
			return "register";
		}
		uiModel.asMap().clear();
		regularUser.persist();
		return "redirect:/regularusers/"
				+ encodeUrlPathSegment(regularUser.getId().toString(),
						httpServletRequest);
	}

	@RequestMapping(method = RequestMethod.GET)
	public String createForm(Model uiModel) {
		uiModel.addAttribute("regularUser", new RegularUser());
		return "regularusers/create";
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
