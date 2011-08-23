package freedays.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import freedays.timesheet.MainClass;

@RequestMapping("/timesheet")
@Controller
public class TimesheetController {

	@RequestMapping(method=RequestMethod.GET)
	public String asd(Model uiModel){
		MainClass mc = new MainClass();
		mc.doMain();
		return "registerty";
	}
}
