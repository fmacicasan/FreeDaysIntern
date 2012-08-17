package freedays.controller;

import java.security.Principal;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import freedays.app.FDUser;
import freedays.domain.Document;
import freedays.domain.Profile;
import freedays.domain.RegularUser;
import freedays.domain.Request;
import freedays.util.PropertiesUtil;

@RooWebScaffold(path = "profile", formBackingObject = Profile.class)
@RequestMapping("/profile")
@Controller
@RooJavaBean
public class ProfileController {

	/**
	 * Model attribute exposed to the view containing all the regular user that
	 * have no associated FDUser yet.
	 * 
	 * @return
	 */
	@ModelAttribute("regularusers")
	public Collection<RegularUser> populateRegularUsers() {
		return RegularUser.findAllRegularUsers();
	}

	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws ServletException {
		// Convert multipart object to byte[]
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	// TODO de facut verificari pt fisier
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Profile profile, BindingResult bindingResult,
			Model uiModel,
			@RequestParam("document.content") MultipartFile content,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("profile", profile);
			return "profile/create";
		}

		uiModel.asMap().clear();

		profile.getDocument().setContentType(content.getContentType());
		profile.getDocument().setFilename(content.getOriginalFilename());
		profile.getDocument().setSize(content.getSize());

		profile.getDocument().persist();
		profile.persist();
		return "redirect:/profile/"
				+ encodeUrlPathSegment(profile.getId().toString(),
						httpServletRequest);
	}

	/**
	 * Handler for own profile
	 * 
	 * @param id
	 * @param uiModel
	 * @return
	 */
	// will find only intended requests
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(params = "own", method = RequestMethod.GET)
	public String showOwn(Model uiModel, Principal p) {

		Profile profile = Profile.findProfileByRegularUserId(FDUser.findFDUserByUsername(p.getName()).getId());
		return "redirect:/profile/" + profile.getId();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model uiModel) {
		uiModel.addAttribute("profile", Profile.findProfile(id));
		uiModel.addAttribute("itemId", id);
		return "profile/show";
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {

		Document.findDocument(Profile.findProfile(id).getDocument().getId())
				.remove();
		Profile.findProfile(id).remove();

		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/profile";
	}
}
