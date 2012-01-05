package freedays.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import freedays.timesheet.*;
import freedays.schedule.FreeDayScheduleServiceImpl;
import freedays.security.UserContextService;

import freedays.timesheet.MainClass;
import freedays.util.DateUtils;

@RequestMapping("/timesheet")
@Controller
public class TimesheetController {

	@Autowired
	private UserContextService userContextService;
	public static void initTimesheetGeneration(String username) {
		
		TimesheetUser us = TimesheetUser.findTimesheetUserByUsername(username);
		TimesheetGenerator x = new POIGenerator(us);
		for (int i = 0; i < 12; i++) {
			x.generateDoc("..\\timesheets\\Timesheet" + " " + us.getRegularUser().getFullName() + " " + WeekConstants.monthStrings[i] + ".xls", i, 2011);
		}
	}
	@RequestMapping(method=RequestMethod.GET)
	public String timesheetReportGeneration(Model uiModel){
		MainClass mc = new MainClass();
		//TimesheetUser us = TimesheetUser.findTimesheetUserByUsername(userContextService.getCurrentUser());
//		List<TimesheetUser> lus = TimesheetUser.findAllTimesheetUsers();
//		for(TimesheetUser tu : lus){
//			try{
//			System.out.println("====================generationg for:"+tu.getRegularUser().getFullName());
//				if(tu.getId() != 188L && tu.getId() != 189L){
//					mc.doMain(tu,DateUtils.getCurrentMonth()-1);
//				}
//			}catch(Exception e){
//				System.out.println("FAULURE!!!!!!!!!!!!!!!!!!!!"+tu.getFduser().getRegularUser().getFullName());
//			}
//		}
//		System.out.println("test");
		
//		
//		TimesheetUser tu = TimesheetUser.findTimesheetUser(188L);
//		mc.doMain(tu,DateUtils.getCurrentMonth()-1);//DateUtils.getCurrentMonth()
//		 tu = TimesheetUser.findTimesheetUser(189L);
//		mc.doMain(tu,DateUtils.getCurrentMonth()-1);//DateUtils.getCurrentMonth()
		
		
//		tu = TimesheetUser.findTimesheetUser(138L);
//		mc.doMain(tu,DateUtils.getCurrentMonth()-1);//DateUtils.getCurrentMonth()
//		tu = TimesheetUser.findTimesheetUser(143L);
//		mc.doMain(tu,DateUtils.getCurrentMonth()-1);//DateUtils.getCurrentMonth()
		
		//test yearly update
//		FreeDayScheduleServiceImpl fdusil = new FreeDayScheduleServiceImpl();
//		fdusil.updateNewYearFreeDays();
		
		
		return "registerty";
	}
}
