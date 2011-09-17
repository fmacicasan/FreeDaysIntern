package freedays.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import freedays.app.form.FreeDayUserList;
import freedays.controller.TimesheetController;
import freedays.domain.Request;
import freedays.security.InfoChanger;
import freedays.security.UserContextService;
import freedays.util.DateUtils;
import freedays.util.MailUtils;
import freedays.util.PropertiesUtil;

@Service("scheduleService")
@Configurable
@RooJavaBean
public class FreeDayScheduleServiceImpl implements FreeDayScheduleService {

	public static final String SUBJECT = "DailyReport";
//	@Autowired
//	ApplicationContext applicationContext;
	
	@Autowired
	private UserContextService userContextService;
	
	
	//don't use annotation due to double discovery by both the web and the app context
	//@Scheduled(cron="0 * * * * MON-FRI")
	@Override
	public void reportFreeDays() {
		
		int m = DateUtils.getCurrentMonth();
		String month1 = FreeDayUserList.generateHtmlReport(m);
		String month2 = FreeDayUserList.generateHtmlReport(m+1);

		MailUtils.sendHtml(PropertiesUtil.getProperty("reportDestinationAddress"), FreeDayScheduleServiceImpl.SUBJECT, month1+"<br/>"+month2);
	}

	@Override
	public void denyLateRequestsStillUnderApproval() {
		//Request.DEBUG=true;
		List<Request> lr = Request.findAllPendingApprovals();
		for (Request request : lr) {
			if(!request.getRequestable().isCancelable()){
				request.autoDeny();
				request.getRequestable().merge();
				request.merge();
			}
		}	
		//Request.DEBUG=false;
	}
	

	
	@Override
	public void generateTimesheets() {
		TimesheetController.initTimesheetGeneration("fmacicasan@sdl.com");	
	}

	@Override
	public void verifyExpiredPasswordRequests() {
		List<InfoChanger> lic = InfoChanger.findAllActiveChangePasswordRequests();
		for(InfoChanger ic : lic){
			if(ic.isJustExpired()){
				System.out.println(DateUtils.printLong(ic.getExpdate()));
				ic.setExpired(true);
				ic.merge();
			} else {
				System.out.println("not"+DateUtils.printLong(ic.getExpdate()));
			}
		}
	}

}
