package freedays.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import freedays.domain.RegularUser;
import freedays.domain.form.AdminRegUserUpdate;
import freedays.domain.form.Search;
import freedays.security.UserContextService;
import freedays.validation.RegularUserValidator;

/**
 * Controller used to handle regular user related requests.
 * 
 * @author fmacicasan
 * 
 */
@RooWebScaffold(path = "regularusers", formBackingObject = RegularUser.class)
@RequestMapping("/regularusers")
@Controller
public class RegularUserController {

	@Autowired
	private MessageDigestPasswordEncoder messageDigestPasswordEncoder;

	@Autowired
	private UserContextService userContextService;

	private void evaluateDeletable(List<RegularUser> lru) {
		for (RegularUser ru : lru) {
			if (ru.getUsername().equals(this.userContextService.getCurrentUser())) {
				ru.setDeletable(false);
			}
		}
	}

	/**
	 * Handler for retrieving the search form
	 * 
	 * @param uiModel
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(params = "search", method = RequestMethod.GET)
	public String createSearch(Model uiModel) {
		// uiModel.addAttribute("regularUser", new RegularUser());
		// addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("search", new Search());
		uiModel.addAttribute("searchOptions", RegularUser.getSearchCriteria());
		List<RegularUser> lru = RegularUser.findAllNotDeletedRegularUsers();
		evaluateDeletable(lru);
		uiModel.addAttribute("regularusers", lru);
		addDateTimeFormatPatterns(uiModel);
		return "regularusers/search";
	}

	/**
	 * Handler for posting a search request
	 * 
	 * @param search
	 * @param bindingResult
	 * @param uiModel
	 * @param httpServletRequest
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(params = "search", method = RequestMethod.POST)
	public String doSearch(@Valid Search search, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors() || search.isNotValid()) {

			uiModel.asMap().clear();

			uiModel.addAttribute("search", search);
			uiModel.addAttribute("searchOptions", RegularUser.getSearchCriteria()); // added
																					// on
			List<RegularUser> lru = RegularUser.findAllNotDeletedRegularUsers();
			evaluateDeletable(lru);
			uiModel.addAttribute("regularusers", lru); // both
			// branches
			addDateTimeFormatPatterns(uiModel);
			return "regularusers/search";
		}
		uiModel.asMap().clear();
		uiModel.addAttribute("search", search);
		List<RegularUser> lru = RegularUser.findAllNotDeletedUsersLike(search);
		evaluateDeletable(lru);
		uiModel.addAttribute("regularusers", lru);
		uiModel.addAttribute("searchOptions", RegularUser.getSearchCriteria());
		addDateTimeFormatPatterns(uiModel);
		// new MailUtils().send("flo.macicasan@gmail.com", "sa nu ana are mere",
		// "Te rog sa nu te duci dupa paine.Sper sa nu ajungem tarziu.Florin");
		return "regularusers/search";
	}

	/***********************************************************************/
	/**
	 * 
	 * @osuciu
	 * 
	 *         Handler for retrieving the history form
	 * 
	 * @param uiModel
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(params = "history", method = RequestMethod.GET)
	public String showHistory(Model uiModel) {
		// uiModel.addAttribute("regularUser", new RegularUser());
		// addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("search", new Search());
		uiModel.addAttribute("searchOptions", RegularUser.getSearchCriteria());
		List<RegularUser> lru = RegularUser.findAllRegularUsers();
		evaluateDeletable(lru);
		uiModel.addAttribute("regularusers", lru);
		addDateTimeFormatPatterns(uiModel);
		return "regularusers/history";
	}

	/**
	 * 
	 * @osuciu
	 * 
	 *         Handler for posting a search request for history
	 * 
	 * @param search
	 * @param bindingResult
	 * @param uiModel
	 * @param httpServletRequest
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(params = "history", method = RequestMethod.POST)
	public String doSearchHistory(@Valid Search search, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors() || search.isNotValid()) {
			uiModel.addAttribute("search", search);
			uiModel.addAttribute("searchOptions", RegularUser.getSearchCriteria()); // added
																					// on
																					// both
																					// branches
			addDateTimeFormatPatterns(uiModel);
			return "regularusers/history";
		}
		uiModel.asMap().clear();
		uiModel.addAttribute("search", search);
		List<RegularUser> lru = RegularUser.findAllRegularUsersLike(search);
		evaluateDeletable(lru);
		uiModel.addAttribute("regularusers", lru);
		uiModel.addAttribute("searchOptions", RegularUser.getSearchCriteria());
		addDateTimeFormatPatterns(uiModel);
		// new MailUtils().send("flo.macicasan@gmail.com", "sa nu ana are mere",
		// "Te rog sa nu te duci dupa paine.Sper sa nu ajungem tarziu.Florin");
		return "regularusers/history";
	}

	// @RequestMapping(value = "/{id}", params = "search", method =
	// RequestMethod.GET)
	// public String updateSearch(@PathVariable("id") Long id, Model uiModel) {
	// //uiModel.addAttribute("regularUser", RegularUser.findRegularUser(id));
	// //addDateTimeFormatPatterns(uiModel);
	// return "regularusers/search";
	// }

	// TODO put date format in a constants file
	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute("regularUser_lastmodified_date_format", "hh:mm:ss dd-MM-yyyy");
		uiModel.addAttribute("regularUser_creationdate_date_format", "hh:mm:ss dd-MM-yyyy");
	}

	/**
	 * Mark user as <i>deleted</i> without physical removal
	 * 
	 * @param id
	 * @param page
	 * @param size
	 * @param uiModel
	 * @return redirection to user listing
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') and !hasPermission(#id,'RegularUser', 'own')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		RegularUser.deleteRegularUser(id);
		uiModel.asMap().clear();
		uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
		uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
		return "redirect:/regularusers";
	}

	/**
	 * Handler for creating a new regular user (by a admnistrator)
	 * 
	 * @param regularUser
	 * @param bindingResult
	 * @param uiModel
	 * @param httpServletRequest
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Valid RegularUser regularUser, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("regularUser", regularUser);
			addDateTimeFormatPatterns(uiModel);
			return "regularusers/create";
		}
		uiModel.asMap().clear();
		// Principal p = httpServletRequest.getUserPrincipal();
		// regularUser.setUsermodifier((p==null)?regularUser.getUsername():p.getName());

		if (RegularUserValidator.validate(regularUser)) {
			String password = regularUser.getPassword();
			String encodedpass = messageDigestPasswordEncoder.encodePassword(password, null);
			regularUser.setPassword(encodedpass);
			regularUser.persist();
			// return "redirect:/regularusers/" +
			// encodeUrlPathSegment(regularUser.getId().toString(),
			// httpServletRequest);
			uiModel.addAttribute("regularuser", regularUser);
			uiModel.addAttribute("itemId", regularUser.getId());
			return "regularusers/show";
		} else {
			httpServletRequest.setAttribute("errorMessage", "Email already taken.");
			uiModel.addAttribute("regularUser", regularUser);
			addDateTimeFormatPatterns(uiModel);
			return "regularusers/create";
		}

	}

	/**
	 * Handler for updating the information associated with a regular user
	 * 
	 * @param regularUser
	 * @param bindingResult
	 * @param uiModel
	 * @param httpServletRequest
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#regularUser,'own')")
	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid AdminRegUserUpdate regularUser, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
		if (bindingResult.hasErrors()) {
			uiModel.addAttribute("regularUser", regularUser);
			addDateTimeFormatPatterns(uiModel);
			return "regularusers/update";
		}
		// TODO: should also check the validity of the RegularUser
		uiModel.asMap().clear();
		RegularUser regu = RegularUser.updateRegUser(regularUser);

		return "redirect:/regularusers/" + encodeUrlPathSegment(regu.getId().toString(), httpServletRequest);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
	public String updateFormRU(@PathVariable("id") Long id, Model uiModel) {

		RegularUser regUser = RegularUser.findRegularUser(id);

		uiModel.addAttribute("regularUser", regUser);

		addDateTimeFormatPatterns(uiModel);
		return "regularusers/update";
	}

	/**
	 * Handler for displaying a regular user based on his identifier
	 * 
	 * @param id
	 * @param uiModel
	 * @return
	 */
	@PostAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#id,'RegularUser', 'own')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String show(@PathVariable("id") Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("regularuser", RegularUser.findRegularUser(id));
		uiModel.addAttribute("itemId", id);
		return "regularusers/show";
	}

	/**
	 * 
	 * @osuciu modified for not deleted users only
	 * 
	 * List all regular users
	 * 
	 * 
	 * 
	 * @param page
	 * @param size
	 * @param uiModel
	 * @return
	 */

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_FDADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
		List<RegularUser> lru;
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			lru = RegularUser.findRegularNotDeletedUserEntries(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo);

			float nrOfPages = (float) RegularUser.countRegularNotDeletedUsers() / sizeNo;
			uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
		} else {
			lru = RegularUser.findAllNotDeletedRegularUsers();
		}

		/*
		 * // process deletable for (RegularUser ru : lru) { if
		 * (!ru.getUsername().equals(this.userContextService.getCurrentUser()))
		 * { ru.setDeletable(true); } }
		 */

		evaluateDeletable(lru);

		uiModel.addAttribute("regularusers", lru);
		addDateTimeFormatPatterns(uiModel);

		return "regularusers/list";
	}
}
