package freedays.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import freedays.timesheet.*;
import freedays.security.UserContextService;

import freedays.timesheet.MainClass;

@RequestMapping("/timesheet")
@Controller
public class TimesheetController {

	@Autowired
	private UserContextService userContextService;
	public static void initTimesheetGeneration(String username) {
		
		TimesheetUser us = TimesheetUser.findTimesheetUserByUsername(username);
		TimesheetGenerator x = new POIGenerator(us);
		for (int i = 0; i < 12; i++) {
			x.generateDoc("..\\..\\..\\timesheets\\Timesheet" + " " + us.getRegularUser().getFullName() + " " + WeekConstants.monthStrings[i] + ".xls", i, 2011);
		}
	}
	@RequestMapping(method=RequestMethod.GET)
	public String asd(Model uiModel){
		MainClass mc = new MainClass();
		TimesheetUser us = TimesheetUser.findTimesheetUserByUsername(userContextService.getCurrentUser());
		mc.doMain(us);
		return "registerty";
	}
}
