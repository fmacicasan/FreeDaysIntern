package freedays.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Service;

import freedays.app.form.FreeDayUserList;
import freedays.controller.TimesheetController;
import freedays.domain.Request;
import freedays.mongo.DailyReportRepository;
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
	
	@Autowired
	private DailyReportRepository dailyReportRepository;
	
	
	//don't use annotation due to double discovery by both the web and the app context
	//@Scheduled(cron="0 * * * * MON-FRI")
	@Override
	public void reportFreeDays() {
		
		int m = DateUtils.getCurrentMonth();
		
		StringBuilder sb = new StringBuilder();
		sb.append(FreeDayUserList.generateHtmlReport(m))
			.append("<br/>")
			.append(FreeDayUserList.generateHtmlReport((m==12)?1:m+1));
		
		String content = sb.toString();
		String email = PropertiesUtil.getProperty("reportDestinationAddress");
		
		dailyReportRepository.insertDailyReport(email, FreeDayScheduleServiceImpl.SUBJECT, content);
		MailUtils.sendHtml(email, FreeDayScheduleServiceImpl.SUBJECT, content);
	}

//	@Override
//	public void denyLateRequestsStillUnderApproval() {
//		//Request.DEBUG=true;
//		List<Request> lr = Request.findAllPendingApprovals();
//		for (Request request : lr) {
//			if(!request.getRequestable().isCancelable()){
//				request.autoDeny();
//				request.getRequestable().merge();
//				request.merge();
//			}
//		}	
//		//Request.DEBUG=false;
//	}
	

	
	@Override
	public void generateTimesheets() {
		TimesheetController.initTimesheetGeneration("fmacicasan@sdl.com");	
	}

	@Override
	public void verifyExpiredPasswordRequests() {
		List<InfoChanger> lic = InfoChanger.findAllActiveChangePasswordRequests();
		for(InfoChanger ic : lic){
			if(ic.isJustExpired()){
				ic.setExpired(true);
				ic.merge();
			}
		}
	}

}
