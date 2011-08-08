package freedays.controller;

import java.util.Calendar;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import freedays.app.FreeDay;
import freedays.app.FreeDayUserList;
import freedays.domain.Request;
import freedays.util.DateUtils;

@RequestMapping("/report")
@Controller
public class ReportController {
	
	@PreAuthorize("hasRole('ROLE_HRMANAGEMENT')")
	@RequestMapping(method=RequestMethod.GET)
	public String reportFreeDays(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size,Model uiModel){
		//TODO: change get all to get jst the needed ones and return them
		List<FreeDayUserList> lfd = FreeDay.getAllUserFreeDays();
//		if (page != null || size != null) {
//            int sizeNo = size == null ? 10 : size.intValue();
//            uiModel.addAttribute("display", FreeDay.getAllUserFreeDays(page == null ? 0 : (page.intValue() - 1) * sizeNo, sizeNo));
//            uiModel.addAttribute("tablewidth",sizeNo);
//            float nrOfPages = (float) FreeDayUserList.computeTableWidth(lfd) / sizeNo;
//            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
//        } else {
        	uiModel.addAttribute("display", lfd);
        	uiModel.addAttribute("tablewidth",FreeDayUserList.computeTableWidth(lfd));
//        }
		return "report/freedays";
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/vacation", method=RequestMethod.GET)
	public String reportVacationPlans(Model uiModel){
		Calendar start = DateUtils.convString2Calendar("6/20/2011");
		Calendar end = DateUtils.convString2Calendar("9/09/2011");
		long span = DateUtils.dateDifferenceInDays(start, end);
		List<FreeDayUserList> lfd = FreeDay.getAllUserVacations(start,end);
		uiModel.addAttribute("vacations",lfd);
		uiModel.addAttribute("length", span);
		uiModel.addAttribute("daysDateList",DateUtils.getShortDateList(start,end));
		return "report/vacation";
	}

}
