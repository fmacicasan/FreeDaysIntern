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

import freedays.controller.ReportController;
import freedays.util.DateUtils;
import freedays.util.MailUtils;

@Service("scheduleService")
@Configurable
@RooJavaBean
public class FreeDayScheduleServiceImpl implements FreeDayScheduleService {

	@Autowired
	ApplicationContext applicationContext;
	
	//@Scheduled(cron="0 * * * * MON-FRI")
	@Override
	public void reportFreeDays() {
//		MockHttpServletRequest request = new MockHttpServletRequest();
//		MockHttpServletResponse response = new MockHttpServletResponse();
//		HandlerAdapter handlerAdapter = applicationContext.getBean(SimpleControllerHandlerAdapter.class);
		ReportController rc = applicationContext.getBean(ReportController.class);
		Model uiModel = new BindingAwareModelMap();
		String view = rc.reportVacationPlans(DateUtils.getCurrentMonth(), uiModel);
		ModelAndView mav = new ModelAndView(view,uiModel.asMap());
		ViewResolver vr = applicationContext.getBean(UrlBasedViewResolver.class);
		try {
			View resolvedView = vr.resolveViewName(view, LocaleContextHolder.getLocale());
			System.out.println(resolvedView.getContentType());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		try {
//			final ModelAndView mav = handlerAdapter.handle(request, response, rc);
//		} catch (Exception e) {
//			System.out.println("naspa");
//			e.printStackTrace();
//		}
		
		
		
		MailUtils.send("fmacicasan@sdl.com", "TestSchedule", "You will be scheduleed");
		MailUtils.send("mcodrea@sdl.com", "TestSchedule", "You will be scheduleed");
		
	}

	@Override
	public void denyLateRequestsStillUnderApproval() {
		// TODO Auto-generated method stub
		
	}

}
