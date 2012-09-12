package freedays.controller;

import java.awt.image.MultiPixelPackedSampleModel;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
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
	 * if the file is less than 2MB and is a pdf file, the method returns true
	 * 
	 * @param content
	 * @return
	 */
	private boolean isValidFile(MultipartFile content) {
		return content.getContentType().equals("application/pdf")
				&& content.getSize() <= 2097152;
	}

	/**
	 * Model attribute exposed to the view containing all the regular user that
	 * have no associated FDUser yet.
	 * 
	 * @return
	 */
	@ModelAttribute("regularusers")
	public Collection<RegularUser> populateRegularUsers() {
		Collection<RegularUser> allRegularUsers = RegularUser
				.findAllRegularUsers();
		Collection<Profile> allProfiles = Profile.findAllProfiles();

		for (Profile p : allProfiles) {

			if (allRegularUsers.contains(p.getRegularUser())) {
				allRegularUsers.remove(p.getRegularUser());
			}

		}

		return allRegularUsers;

	}

	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws ServletException {
		// Convert multipart object to byte[]
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

	@PreAuthorize("hasAnyRole('ROLE_HRMANAGEMENT','ROLE_FDADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Profile profile, BindingResult bindingResult,
			Model uiModel,
			@RequestParam("document.content") MultipartFile content,
			HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()
				|| userHasProfile(profile.getRegularUser().getId())
				|| !isValidFile(content)) {
			uiModel.addAttribute("hasErrors", true);
			uiModel.addAttribute("profile", profile);
			return "profile/create";
		}

		uiModel.asMap().clear();

		profile.setDocumentContent(content);

		// profile.getDocument().setContentType(content.getContentType());
		// profile.getDocument().setFilename(content.getOriginalFilename());
		// profile.getDocument().setSize(content.getSize());

		profile.getDocument().persist();
		profile.persist();
		return "redirect:/profile/"
				+ encodeUrlPathSegment(profile.getId().toString(),
						httpServletRequest);
	}

	@PreAuthorize("hasAnyRole('ROLE_HRMANAGEMENT','ROLE_FDADMIN')")
	@RequestMapping(params = "form", method = RequestMethod.GET)
	public String createForm(Model uiModel) {
		uiModel.addAttribute("profile", new Profile());
		return "profile/create";
	}

	@PreAuthorize("hasAnyRole('ROLE_HRMANAGEMENT','ROLE_FDADMIN')")
	@RequestMapping(params = "update", method = RequestMethod.POST)
	public String update(
			// @Valid Profile profile, BindingResult bindingResult,
			Model uiModel, @RequestParam("regularUser") RegularUser regUsr,
			@RequestParam("document.content") MultipartFile content,
			HttpServletRequest httpServletRequest) {

		uiModel.asMap().clear();

		Profile profile = Profile.findProfileByRegularUserId(regUsr.getId());

		if (!isValidFile(content)) {
			Collection<RegularUser> col = new ArrayList<RegularUser>();
			col.add(profile.getRegularUser());
			uiModel.addAttribute("regularuser", col);
			uiModel.addAttribute("profile", profile);
			uiModel.addAttribute("hasErrors", true);
			return "profile/update";
		}

		profile.setDocumentContent(content);

		profile.merge();

		return "redirect:/profile/"
				+ encodeUrlPathSegment(profile.getId().toString(),
						httpServletRequest);
	}

	@PreAuthorize("hasAnyRole('ROLE_HRMANAGEMENT','ROLE_FDADMIN')")
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model uiModel) {
		uiModel.asMap().clear();
		Profile profile = Profile.findProfile(id);

		uiModel.addAttribute("profile", profile);
		Collection<RegularUser> col = new ArrayList<RegularUser>();
		col.add(Profile.findProfile(id).getRegularUser());
		uiModel.addAttribute("regularuser", col);

		return "profile/update";
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

		Profile profile = Profile.findProfileByRegularUserId(RegularUser
				.findRegularUserByUsername(p.getName()).getId());

		if (profile == null) {
			uiModel.addAttribute("hasProfile", false);
			return "profile/show";
		} else {
			return "redirect:/profile/" + profile.getId();
		}
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/fromregularuser/{id}", method = RequestMethod.GET)
	public String getFromRegularUser(@PathVariable("id") Long id,
			HttpServletResponse response, Model uiModel) {

		if (Profile.findProfileByRegularUserId(id) == null) {
			uiModel.addAttribute("hasProfile", false);
			return "profile/show";
		} else {
			return "redirect:/profile/"
					+ Profile.findProfileByRegularUserId(id).getId();
		}
		// return show(Profile.findProfileByRegularUserId(id).getId(), response,
		// uiModel);
	}
	
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/fromfduser/{id}", method = RequestMethod.GET)
	public String getFromFDUser(@PathVariable("id") Long id,
			HttpServletResponse response, Model uiModel){
		
		return "redirect:/profile/fromregularuser/"+FDUser.findFDUser(id).getRegularUser().getId();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id,
			HttpServletResponse response, Model uiModel) {

		if (Profile.findProfile(id) == null) {
			uiModel.addAttribute("hasProfile", false);
			return "profile/show";
		} else {

			Document doc = Document.findDocument(Profile.findProfile(id)
					.getDocument().getId());

			try {
				response.setHeader("Content-Disposition", "inline;filename=\""
						+ doc.getFilename() + "\"");

				OutputStream out = response.getOutputStream();
				response.setContentType(doc.getContentType());

				IOUtils.copy(new ByteArrayInputStream(doc.getContent()), out);
				out.flush();

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		/*
		 * uiModel.addAttribute("profile", Profile.findProfile(id));
		 * uiModel.addAttribute("itemId", id); return "profile/show";
		 */

	}

	@PreAuthorize("hasAnyRole('ROLE_HRMANAGEMENT','ROLE_FDADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {

		Document doc = Profile.findProfile(id).getDocument();
		Profile.findProfile(id).remove();
		// Document.findDocument(doc.getId()).remove();

		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/profile";
	}

	private boolean userHasProfile(Long regUserId) {

		for (Profile p : Profile.findAllProfiles()) {
			if (p.getRegularUser().getId().equals(regUserId)) {
				return true;
			}
		}

		return false;

	}

	@RequestMapping(method = RequestMethod.GET)
    public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            uiModel.addAttribute("profiles", Profile.findProfileEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
            float nrOfPages = (float) Profile.countProfiles() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("profiles", Profile.findAllProfiles());
        }
        return "profile/list";
    }
}
