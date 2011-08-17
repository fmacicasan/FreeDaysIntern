package freedays.schedule;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import freedays.app.form.FreeDayUserList;
import freedays.controller.ReportController;
import freedays.util.DateUtils;
import freedays.util.MailUtils;

@Service("scheduleService")
@Configurable
@RooJavaBean
public class FreeDayScheduleServiceImpl implements FreeDayScheduleService {

	public static final String SUBJECT = "DailyReport";
//	@Autowired
//	ApplicationContext applicationContext;
	
	@Autowired
	private String reportDestinationAddress;
	
	//don't use annotation due to double discovery by both the web and the app context
	//@Scheduled(cron="0 * * * * MON-FRI")
	@Override
	public void reportFreeDays() {
//		MockHttpServletRequest request = new MockHttpServletRequest();
//		MockHttpServletResponse response = new MockHttpServletResponse();
//		HandlerAdapter handlerAdapter = new AnnotationMethodHandlerAdapter();
//		request.setMethod("GET");
//		request.setRequestURI("/report/vacation");
//		request.setParameter("m", DateUtils.getCurrentMonth().toString());
//		ReportController rc = applicationContext.getBean(ReportController.class);
//		Map<String,Object> uiModel;
//		try {
//			ModelAndView mav = handlerAdapter.handle(request, response, rc);
//			uiModel = mav.getModel();
//		String view = mav.getViewName();
//		View v = new InternalResourceView("views/report/vacation.jspx");
//		View vv = new InternalResourceView("WEB-INF/views/report/vacation.jspx");
//		View vvv = new InternalResourceView("webapp/WEB-INF/views/report/vacation.jspx");
//		View vvvv = new InternalResourceView("report/vacation.jspx");
//		
//		View vw = new InternalResourceView("/WEB-INF/views/report/vacation.jspx");
//		
//		
//		//UrlBasedViewResolver irvr = new UrlBasedViewResolver();
//		UrlBasedViewResolver irvr = (UrlBasedViewResolver) applicationContext.getBean("tilesViewResolver2");
////		irvr.setViewClass(JstlView.class);
////		irvr.setPrefix("/WEB-INF/views/");
////		irvr.setSuffix(".jspx");
////		irvr.setApplicationContext(applicationContext);
////		irvr.setAttributesMap(uiModel);
////		irvr.setServletContext(servletContext);
//		try {
//			View asd = irvr.resolveViewName(view, LocaleContextHolder.getLocale());
//			System.out.println(asd.getContentType());
//		
//		
//			v.getContentType();
//			System.out.println(v.getContentType());
//			Map<String,Object> m = uiModel;
//			for (Entry<String, Object> ie : m.entrySet()) {
//				request.setAttribute(ie.getKey(), ie.getValue());
//			}
//			
//				asd.render(null, request, response);
//				System.out.println(response.getContentAsString());
//				System.out.println(response.getContentType());
//				System.out.println(response.getContentAsByteArray());
//				System.out.println(response.getHeaderNames());
//				System.out.println(response.getStatus());
//				System.out.println(response.getContentLength());
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		int m = DateUtils.getCurrentMonth();
		String month1 = FreeDayUserList.generateHtmlReport(m);
		String month2 = FreeDayUserList.generateHtmlReport(m+1);
		
		
		
		MailUtils.sendHtml(this.getReportDestinationAddress(), FreeDayScheduleServiceImpl.SUBJECT, month1+"<br/>"+month2);
		
	}

	@Override
	public void denyLateRequestsStillUnderApproval() {
		// TODO Auto-generated method stub
		
	}

}
