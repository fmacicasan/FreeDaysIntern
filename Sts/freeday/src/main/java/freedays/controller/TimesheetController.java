package freedays.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import freedays.timesheet.*;
import freedays.schedule.FreeDayScheduleServiceImpl;
import freedays.security.UserContextService;

import freedays.timesheet.MainClass;
import freedays.util.DateUtils;
import freedays.util.PropertiesUtil;

@RequestMapping("/timesheet")
@Controller
public class TimesheetController {

	private static final Logger log = Logger
			.getLogger(TimesheetController.class);

	@Autowired
	private UserContextService userContextService;

	public static void initTimesheetGeneration(String username) {

		TimesheetUser us = TimesheetUser.findTimesheetUserByUsername(username);
		TimesheetGenerator x = new POIGenerator(us);
		for (int i = 0; i < 12; i++) {
			
			x.generateDoc("Timesheet" + " "
					+ us.getRegularUser().getFullName() + " "
					+ WeekConstants.monthStrings[i] + ".xls", i, 2011);
		}
	}

	@RequestMapping(value = "/generate**", method = RequestMethod.POST)
	public String proceedGeneration(
			@RequestParam(value = "all", required = true) Boolean targetAllUsers,
			Model uiModel, Principal p) {

		ArrayList<String> usernames = new ArrayList<String>();

		if (targetAllUsers) {

			for (TimesheetUser tsu : TimesheetUser.findAllTimesheetUsers()) {
				usernames.add(tsu.getFduser().getRegularUser().getUsername());
			}

		} else {
			usernames.add(p.getName());
		}

		timesheetReportGeneration(usernames);

		return "timesheet/registerty";
	}

	@PreAuthorize("hasAnyRole('ROLE_HRMANAGEMENT','ROLE_FDADMIN')")
	@RequestMapping(params = "all", method = RequestMethod.GET)
	public String timesheetReportGenerationAll(Model uiModel) {

		ArrayList<String> users = new ArrayList<String>();

		for (TimesheetUser tsu : TimesheetUser.findAllTimesheetUsers()) {
			users.add(tsu.getFduser().getRegularUser().getUsername());
		}

		uiModel.addAttribute("allUsers", "true");
		uiModel.addAttribute("users", users);

		return "timesheet/confirm";
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(params = "own", method = RequestMethod.GET)
	public String timesheetReportGenerationOwn(Model uiModel, Principal p) {

		TimesheetUser user = TimesheetUser.findTimesheetUserByUsername(p
				.getName());
		if(user == null){
			uiModel.addAttribute("supportuser",PropertiesUtil.getProperty("timesheet.support.user"));
			return "timesheet/notimesheetuser";
		}
		
		ArrayList<String> users = new ArrayList<String>();
		users.add(user.getRegularUser().getUsername());

		uiModel.addAttribute("allUsers", "false");
		uiModel.addAttribute("users", users);

		return "timesheet/confirm";
	}

	private void timesheetReportGeneration(List<String> users) {
		MainClass mc = new MainClass();

		for (String user : users) {
			TimesheetUser tu = TimesheetUser.findTimesheetUserByUsername(user);
			if (tu != null) {
				try {
					mc.doMain(tu, DateUtils.getCurrentMonth() - 1);
					log.info("Timesheet created for "
							+ tu.getFduser().getRegularUser().getUsername());
				} catch (Exception e) {
					log.error(
							"Issue during timesheet creation for " + tu + ".",
							e);
				}
			} else {
				log.error("Failed to retrieve timesheet user for " + user + ".");
			}
		}
	}
}
