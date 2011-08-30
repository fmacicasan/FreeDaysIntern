package freedays.controller;

import freedays.security.UserContextService;
import freedays.timesheet.Schedule;
import freedays.timesheet.TimesheetUser;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RooWebScaffold(path = "schedules", formBackingObject = Schedule.class)
@RequestMapping("/schedules")
@Controller
public class ScheduleController {

	@Autowired
	private UserContextService userContextService;
	
	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid Schedule schedule, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("schedule", schedule);
            addDateTimeFormatPatterns(uiModel);
            return "schedules/create";
        }
        uiModel.asMap().clear();
        TimesheetUser tu = TimesheetUser.findTimesheetUserByUsername(userContextService.getCurrentUser());
        schedule.setEmployee(tu);
        schedule.persist();
        return "redirect:/schedules/" + encodeUrlPathSegment(schedule.getId().toString(), httpServletRequest);
    }
}
