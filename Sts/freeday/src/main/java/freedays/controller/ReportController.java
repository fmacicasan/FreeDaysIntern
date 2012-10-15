package freedays.controller;

import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import freedays.app.FreeDay;
import freedays.app.form.FreeDayUserList;
import freedays.domain.Request;
import freedays.mongo.MongoDBApp;
import freedays.schedule.FreeDayScheduleServiceImpl;
import freedays.security.UserContextService;
import freedays.util.DateUtils;
import freedays.util.ResourceDemo;

/**
 * Controler used to intercept report related requests.
 * 
 * @author fmacicasan
 * 
 */
@RequestMapping("/report")
@Controller
public class ReportController {

	private static final Logger log = Logger.getLogger(ReportController.class);

	@Autowired
	private UserContextService userContextService;

	// @Autowired
	// private ResourceDemo resourceService;

	/**
	 * Handler for retrieving the free days report in the day-by-day
	 * representation
	 * 
	 * @param page
	 * @param size
	 * @param uiModel
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_HRMANAGEMENT')")
	@RequestMapping(method = RequestMethod.GET)
	public String reportFreeDays(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "size", required = false) Integer size,
			Model uiModel) {
		// TODO: change get all to get jst the needed ones and return them
		List<FreeDayUserList> lfd = FreeDay.getAllUserFreeDays();
		Long tablewidth = FreeDayUserList.computeTableWidth(lfd);
		if (page != null || size != null) {
			int sizeNo = size == null ? 10 : size.intValue();
			sizeNo = sizeNo < tablewidth.intValue() ? sizeNo : tablewidth
					.intValue();
			page = page == null ? 0 : (page.intValue() - 1);
			page = page > tablewidth.intValue() / sizeNo ? tablewidth
					.intValue() / sizeNo : page;
			uiModel.addAttribute("display", FreeDayUserList.subListFreedays(
					lfd, page * sizeNo, (page + 1) * sizeNo));
			uiModel.addAttribute("tablewidth", sizeNo);
			float nrOfPages = (float) tablewidth / sizeNo;
			uiModel.addAttribute(
					"maxPages",
					(int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1
							: nrOfPages));
		} else {
			uiModel.addAttribute("display", lfd);
			uiModel.addAttribute("tablewidth", tablewidth);
		}

		return "report/freedays";
	}

	/**
	 * Handler for retrieving the updated version of the report organized by
	 * months
	 * 
	 * @param m
	 * @param uiModel
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/vacation", method = RequestMethod.GET)
	public String reportVacationPlans(
			@RequestParam(value = "m", required = false) Integer m,
			Model uiModel) {
		if (!DateUtils.isValidMonth(m)) {
			m = DateUtils.getCurrentMonth();
			return "redirect:/report/vacation?m=" + m;
		}
		int month = DateUtils.transformMonth(m);
		List<FreeDayUserList> lfd = FreeDay.getAllUserFreeDays(month);
		// log.info("AAAAAAAAAAAAAAAAAAAZZZZZZZZZZYYYYYY"+lfd.size());
		uiModel.addAttribute("vacations", lfd);
		uiModel.addAttribute("length", DateUtils.getDaysInMonth(month));
		uiModel.addAttribute("daysDateList", DateUtils.getShortDateList(month));
		uiModel.addAttribute("daysWeekdayList",
				DateUtils.getWeekdayInitialsList(month));
		// modifyed to accustom january/december of adjacent years
		// uiModel.addAttribute("fullMonthNames", DateUtils.getMonthNames());
		uiModel.addAttribute("fullMonthNames",
				DateUtils.getMonthNamesExtended());
		uiModel.addAttribute("employeeCount", lfd.size());
		// log.info("MONTH NAMES!!!!!!!!!!!!!!!!!!!!!!"+DateUtils.getMonthNamesExtended());
		log.info("Finish report creation!");

		// test properties finder
		// PropertiesUtil.getProperty("testing");

		// test super aproval computation
		// Request.findAllPendingSuperApprovalsByUsername(userContextService.getCurrentUser());

		// test daily report
		// FreeDayScheduleServiceImpl fdusil = new FreeDayScheduleServiceImpl();
		// fdusil.reportFreeDays();

		// test yearly update
		// FreeDayScheduleServiceImpl fdusil = new FreeDayScheduleServiceImpl();
		// fdusil.updateNewYearFreeDays();

		// test resource opener
		// System.out.println("will open");
		// resourceService.openResource();
		// System.out.println("will close");

		// test mongo
		// System.out.println("mongo");
		// MongoDBApp mdba = new MongoDBApp();
		// mdba.execute();
		// System.out.println("mongoend");

		// test timesheets
		// FreeDayScheduleServiceImpl fdusil = new FreeDayScheduleServiceImpl();
		// fdusil.generateTimesheets();

		// test report
		// FreeDayScheduleServiceImpl fdusil = new FreeDayScheduleServiceImpl();
		// fdusil.reportFreeDays();

		return "report/vacation";
	}

	/**
	 * Handler for retrieving the updated version of the report organized by
	 * months of the team
	 * 
	 * @param m
	 * @param uiModel
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_REQUESTGRANTER') OR hasPermission(5, 'Menu', 'team')")
	@RequestMapping(value = "/vacation/team", method = RequestMethod.GET)
	public String reportVacationPlansTeam(
			@RequestParam(value = "m", required = false) Integer m,
			Model uiModel) {
		if (!DateUtils.isValidMonth(m)) {
			m = DateUtils.getCurrentMonth();
			return "redirect:/report/vacation/team?m=" + m;
		}
		int month = DateUtils.transformMonth(m);
		List<FreeDayUserList> lfd = FreeDay.getAllTeamUserFreeDays(month,
				userContextService.getCurrentUser());
		// log.info("AAAAAAAAAAAAAAAAAAAZZZZZZZZZZYYYYYY"+lfd.size());
		uiModel.addAttribute("vacations", lfd);
		uiModel.addAttribute("length", DateUtils.getDaysInMonth(month));
		uiModel.addAttribute("daysDateList", DateUtils.getShortDateList(month));
		uiModel.addAttribute("daysWeekdayList",
				DateUtils.getWeekdayInitialsList(month));
		// modifyed to accustom january/december of adjacent years
		// uiModel.addAttribute("fullMonthNames", DateUtils.getMonthNames());
		uiModel.addAttribute("fullMonthNames",
				DateUtils.getMonthNamesExtended());
		uiModel.addAttribute("employeeCount", lfd.size());
		log.info("Finish report creation!");

		return "report/vacation";
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/profile/{id}", method = RequestMethod.GET)
	public String goToProfile(@PathVariable("id") Long id, Model uiModel) {
		return "redirect:/profile/" + id;
	}

}
