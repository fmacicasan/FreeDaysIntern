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
			return "register/create";
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
		return "register/create";
	}

	// @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	// public String RegularUserController.show(@PathVariable("id") Long id,
	// Model uiModel) {
	// addDateTimeFormatPatterns(uiModel);
	// uiModel.addAttribute("regularuser", RegularUser.findRegularUser(id));
	// uiModel.addAttribute("itemId", id);
	// return "regularusers/show";
	// }
	//
	// @RequestMapping(method = RequestMethod.GET)
	// public String RegularUserController.list(@RequestParam(value = "page",
	// required = false) Integer page, @RequestParam(value = "size", required =
	// false) Integer size, Model uiModel) {
	// if (page != null || size != null) {
	// int sizeNo = size == null ? 10 : size.intValue();
	// uiModel.addAttribute("regularusers",
	// RegularUser.findRegularUserEntries(page == null ? 0 : (page.intValue() -
	// 1) * sizeNo, sizeNo));
	// float nrOfPages = (float) RegularUser.countRegularUsers() / sizeNo;
	// uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages ||
	// nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
	// } else {
	// uiModel.addAttribute("regularusers", RegularUser.findAllRegularUsers());
	// }
	// addDateTimeFormatPatterns(uiModel);
	// return "regularusers/list";
	// }
	//
	// @RequestMapping(method = RequestMethod.PUT)
	// public String RegularUserController.update(@Valid RegularUser
	// regularUser, BindingResult bindingResult, Model uiModel,
	// HttpServletRequest httpServletRequest) {
	// if (bindingResult.hasErrors()) {
	// uiModel.addAttribute("regularUser", regularUser);
	// addDateTimeFormatPatterns(uiModel);
	// return "regularusers/update";
	// }
	// uiModel.asMap().clear();
	// regularUser.merge();
	// return "redirect:/regularusers/" +
	// encodeUrlPathSegment(regularUser.getId().toString(), httpServletRequest);
	// }
	//
	// @RequestMapping(value = "/{id}", params = "form", method =
	// RequestMethod.GET)
	// public String RegularUserController.updateForm(@PathVariable("id") Long
	// id, Model uiModel) {
	// uiModel.addAttribute("regularUser", RegularUser.findRegularUser(id));
	// addDateTimeFormatPatterns(uiModel);
	// return "regularusers/update";
	// }
	//
	// @ModelAttribute("register")
	// public Collection<RegularUser> populateRegularUsers() {
	// return RegularUser.findAllRegularUsers();
	// }
	//
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
