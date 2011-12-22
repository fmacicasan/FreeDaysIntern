package freedays.app.form;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Component;

import freedays.app.FDUser;
import freedays.app.FreeDay;
import freedays.app.FreeDay.FreeDayStatus;
import freedays.app.FreeDayRL;
import freedays.app.FreeDayVacation;
import freedays.app.form.FreeDayRequest.RequestType;
import freedays.security.UserContextService;
import freedays.util.DateUtils;
import freedays.util.PropertiesUtil;
import freedays.util.ValidationUtils;

/**
 * Wrapper class for report generation.
 */

@Component
@RooJavaBean
@Configurable
public class FreeDayUserList {
	private static final Logger log = Logger.getLogger(FreeDayUserList.class);
	private String user;
	private String jobrole;
	private Long remainingdays;
	private Long totaldaysleft;
	private List<String> freedays;
	private List<Integer> vacations;
	private List<FreeDayReportWrapper> combined;
	
	
	@Autowired
	private UserContextService userContextService;
	
	/**
	 * Generates a list of granted FreeDays requests based on the provided username
	 */
	@Deprecated
	public static FreeDayUserList generateFreeDaysList(String username){
		FDUser fdu = FDUser.findFDUserByUsername(username);
		return FreeDayUserList.generateFreeDaysList(fdu);
	}
	
	/**
	 * Generates a list of granted FreeDays requests for the provided FDUser
	 */
	@Deprecated
	public static FreeDayUserList generateFreeDaysList(FDUser fdu){
		FreeDayUserList fdul = new FreeDayUserList();
		fdul.setUser(fdu.getRegularUser().getReportName());
		fdul.setJobrole(fdu.getJobrole().toString());
		fdul.setRemainingdays(fdu.computeAvailableFreeDays());
		fdul.setTotaldaysleft(fdu.computeteAvailableFreeDaysTotal());
		fdul.setFreedays(transformFreeDay2String4Report(FreeDay.getAllGrantedFreeDayByUsername(fdu.getRegularUser().getUsername())));
		return fdul;
	}

	/**
	 * Prepares the list of generated FreeDay-s for report printing
	 */
	@Deprecated
	private static List<String> transformFreeDay2String4Report(List<FreeDay> allGrantedFreeDayByUsername) {
		List<String> ls = new ArrayList<String>();
		for (FreeDay fd : allGrantedFreeDayByUsername) {
			// ugly hack... think of some better solution for the addition of the list
			//return list all the time and use addAll
				ls.addAll(fd.reportPrint());
			
		}
		return ls;
	}

	/**
	 * Computes the maximum width of the free day report based on the maximum
	 * number of freedays associated with fduser from the report.
	 */
	@Deprecated
	public static Long computeTableWidth(List<FreeDayUserList> lfd) {
		long max = -1;
		for (FreeDayUserList fdul : lfd) {
			long size = fdul.getFreedays().size();
			if(max < size)max=size;
		}
		return max;
	}
	
	/**
	 * Generates a list of granted vacation requests for the provided FDUser
	 */
	public static FreeDayUserList generateVacationList(FDUser fdu,Calendar start, Calendar end) {
		FreeDayUserList fdul = new FreeDayUserList();
		fdul.setUser(fdu.getRegularUser().getReportName());
		fdul.setJobrole(fdu.getJobrole().toString());
		fdul.setRemainingdays(fdu.computeAvailableFreeDays());
		fdul.setTotaldaysleft(fdu.computeteAvailableFreeDaysTotal());
		//TODO: get only between the two values not all vacations
		fdul.setVacations(transformVacation2Integer4Report(FreeDayVacation.getAllGrantedVacationsByUsername(fdu.getRegularUser().getUsername()),start,end));
		return fdul;
	}

	
	/**
	 * Prepares the list of generated vacations for report printing
	 */
	private static List<Integer> transformVacation2Integer4Report(List<FreeDayVacation> allgrantvac, Calendar start, Calendar end) {
		long span = DateUtils.dateDifferenceInDays(start, end);
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(long i=0;i<span;i++){
			list.add(0);
		}
		for (FreeDayVacation vacation : allgrantvac) {
			if(vacation.getDate().after(start)){
				long before = DateUtils.dateDifferenceInDays(start, vacation.getDate());
				long during = (before+vacation.getSpan()<span)?before+vacation.getSpan():span;
				for(Long i=before;i<=during;i++){
					list.set(i.intValue(),vacation.getConfidence().ordinal()+1);
				}
			}
		}
		return list;
	}

	/**
	 * Sublist based on the retrieved FreeDayUserList for the given interval of pagination
	 * @param lfd
	 * @param i
	 * @param j
	 * @return
	 */
	@Deprecated
	public static List<FreeDayUserList> subListFreedays(List<FreeDayUserList> lfd, int i, int j) {
		if(i>=j) throw new IllegalArgumentException("The i parameter should be smaller than j");
		
		List<FreeDayUserList> lfdul2 = new ArrayList<FreeDayUserList>();
		for (FreeDayUserList fdul : lfd) {
			List<String> lst = fdul.getFreedays();
			try{
				fdul.setFreedays(lst.subList(i, j));
			}catch(IndexOutOfBoundsException e){
				if(i < lst.size()){
					fdul.setFreedays(lst.subList(i, lst.size()));
				} else {
					fdul.setFreedays(new ArrayList<String>());
				}
					
			}
			lfdul2.add(fdul);
		}
		
		return lfdul2;
	}
	
	/**
	 * Retrieves all the free days report representations for the given FDUser in the given month
	 * @param fdu
	 * @param month
	 * @return
	 */
	public static FreeDayUserList generateAllFreeDays(FDUser fdu,int month){
		FreeDayUserList fdul = new FreeDayUserList();
		fdul.setUser(fdu.getRegularUser().getReportName());
		fdul.setJobrole(fdu.getJobrole().toString());
		
		fdul.setTotaldaysleft(fdu.computeteAvailableFreeDaysTotal());
		//TODO: get only between the two values not all vacations
		List<FreeDay> lfdu;
		UserContextService ucs = fdul.getUserContextService();
		if(ucs.isHR()){
			lfdu = FreeDay.getAllGrantedFreeDayByUsername(fdu.getRegularUser().getUsername());
		} else {
			//fdul.setRemainingdays(fdu.computeAvailableFreeDays());//add covered by work days
			String username = fdu.getRegularUser().getUsername();
			fdul.setRemainingdays(FDUser.computeAvailableLWSpecificDays(username));//days, depth to LW
			lfdu =FreeDay.getAllNotFailedRequestsByUsername(username);
			log.info("found alot of requiests for you!!!"+username+" like alotzzz"+lfdu.size());
		}
		fdul.setCombined(tranformFreeDay2Integer4Report(lfdu,month));
		return fdul;
	}
	
	/**
	 * Retrieves the necessary info from the created list of report representations.
	 * @param freedays
	 * @param month
	 * @return
	 */
	private static List<FreeDayReportWrapper> tranformFreeDay2Integer4Report(List<FreeDay> freedays,int month){
		//month can be -1 if previous December, 0-11 if current year, 12 if next January
//		log.info("!!!!!!!!!!!!!!!!transforming with mont"+month);
		int days = DateUtils.getDaysInMonth(month);
		ArrayList<FreeDayReportWrapper> list = new ArrayList<FreeDayReportWrapper>();
		for(long i=0;i<days;i++){
			FreeDayReportWrapper fdrw = new FreeDayReportWrapper();
			fdrw.setType(0);
			list.add(fdrw);
		}
		for (FreeDay fd : freedays) {
			if(fd instanceof FreeDayVacation){
				
				FreeDayVacation fdv = (FreeDayVacation)fd;
				
				Calendar start = fdv.getDate();
//				if(start.get(Calendar.YEAR) == 2012){
//					log.info("@@@@@@@@@@@@@@@@@@@2012"+DateUtils.printShortDate(start));
//				}
				//Calendar end = DateUtils.dateAddRomanianBusinessDay(start, fdv.getSpan());
				Calendar end = fdv.getEnd();
				//replaced before by compareTo to test equality
				for(Calendar c = (Calendar)start.clone();c.compareTo(end)<=0;c.add(Calendar.DAY_OF_YEAR, 1)){
//					if(start.get(Calendar.YEAR) == 2012){
//						log.info("@@@@@@@@@@@@@@@@@@@ccccccccc2012"+month+"zz"+DateUtils.printShortDate(start));
//					}
					if(
							//DateUtils.isSameMonth(c, month) && DateUtils.isCurrentYear(c)
							
							DateUtils.isReportableDate(month, c)
							&& !ValidationUtils.checkRomanianLegalHoliday(c)){
//						if(start.get(Calendar.YEAR) == 2012){
//							log.info("@@@@@@@@@@@@@@@@@@@zzzzzzzz2012"+DateUtils.printShortDate(start));
//						}
						FreeDayReportWrapper fdrw = new FreeDayReportWrapper();
						fdrw.setType(fd.getType().ordinal()+1);
						fdrw.setStatus(evaluateReportWrapperStatus(fd.getStatus()));
						list.set(DateUtils.getDay(c)-1,fdrw );
					}
				}
			} else {
				if(
						//DateUtils.isSameMonth(fd.getDate(),month) && DateUtils.isCurrentYear(fd.getDate())
						DateUtils.isReportableDate(month, fd.getDate())
						&& !ValidationUtils.checkRomanianLegalHoliday(fd.getDate())){//remove this if you get the freedays already filtered by month
					FreeDayReportWrapper fdrw = new FreeDayReportWrapper();
					fdrw.setType(fd.getType().ordinal()+1);
					fdrw.setStatus(evaluateReportWrapperStatus(fd.getStatus()));
					list.set(DateUtils.getDay(fd.getDate())-1,fdrw );
//					if(fd.getDate().get(Calendar.YEAR) == 2012){
//						log.info("||||||||||||||2012");
//					}
				}
			}
		}
		List<FreeDayRL> lfdrl = FreeDayRL.findAllFreeDayRLs();
		for(FreeDayRL fdrl : lfdrl){
			Calendar holidayDay = fdrl.getRomanianHoliday();
			if(
					//DateUtils.isSameMonth(holidayDay, month) && DateUtils.isCurrentYear(holidayDay)
					DateUtils.isReportableDate(month, holidayDay)
					){
				FreeDayReportWrapper fdrw = new FreeDayReportWrapper();
				fdrw.setType(RequestType.values().length+1);//this will be the last of the free day status in any case
				fdrw.setStatus(evaluateReportWrapperStatus(FreeDayStatus.getSuccessStatus()));
				list.set(DateUtils.getDay(holidayDay)-1, fdrw);
				if(holidayDay.get(Calendar.YEAR) == 2012){
					log.info("------------2012");
				}
			}
		}
		log.info("processed alot of info and got"+list.size());
		for(int i=0;i<list.size();i++){
			if(list.get(i).getStatus() != null){
				log.info("alottt status"+list.get(i).getStatus()+"alot type"+list.get(i).getType());
			}
			
		}
		return list;
	}
	
	private static String evaluateReportWrapperStatus(FreeDayStatus fds){
		return fds.toString().substring(0, 1);
	}
	
	/**
	 * 
	 * @param m between 1 and 12
	 * @return
	 */
	public static String generateHtmlReport(int m){
		int month = DateUtils.transformMonth(m);
		List<FreeDayUserList> lfd = FreeDay.getAllUserFreeDays(month);
		int daysinmonth = DateUtils.getDaysInMonth(month);
		List<String> shortDateList = DateUtils.getShortDateList(month);
		List<String> weekdayinitials = DateUtils.getWeekdayInitialsList(month);
		//List<String> monthnames = DateUtils.getMonthNames();
		List<String> monthnames = DateUtils.getMonthNamesExtended();
		
		StringBuilder sb = new StringBuilder();
		sb.append("<style>")
		.append(".legr,.legy,.legg,.lego{display: inline;margin: 0.5%;}")
		.append(".colr,.legr{background-color: red;}")
		.append(".coly,.legy{background-color: yellow;}")
		.append(".colg,.legg{background-color: aqua;}")
		.append(".colo,.lego{background-color: orange;}")
		.append(".report{text-align: center;}")
		.append("tr:nth-child(odd) {background-color: #FFFFFF;}")
		.append("tr:nth-child(even) {background-color: #EFEFEF;}")
		.append("table {background: #EEEEEE;margin: 2px 0 0 0;border: 1px solid #BBBBBB;border-collapse: collapse;width: 100%}")
		.append("table table {margin: -5px 0;border: 0px solid #e0e7d3;width: 100%;}")
		.append("table td,table th {padding: 2px;border: 1px solid #CCCCCC;}")
		.append("table {background: #EEEEEE;margin: 2px 0 0 0;border: 1px solid #BBBBBB;border-collapse: collapse;width: 100%}")
		.append("#footer {background:#fff;border:none;margin-top:15px;border-top:1px solid #999999;}")
		.append(".monthfooter{width : 8%;display: inline;text-align: center;float : left;}")
		.append("</style>");
		sb.append("<table class='report'>");
			sb.append("<thead>");
				sb.append("<tr>");
					sb.append("<th rowspan='2'>").append(PropertiesUtil.getProperty("freedaysreport_name")).append("</th>");
					sb.append("<th rowspan='2'>").append(PropertiesUtil.getProperty("freedaysreport_role")).append("</th>");
					sb.append("<th colspan='2'>").append(PropertiesUtil.getProperty("freedaysreport_remainingdays")).append("</th>");
					for(int i=0;i<daysinmonth;i++){
						sb.append("<th>").append(weekdayinitials.get(i)).append("</th>");
					}
	   			sb.append("</tr>");
	   			sb.append("<tr>");
	   				sb.append("<th>").append(PropertiesUtil.getProperty("freedaysreport_remainingdayscovered")).append("</th>");
	   				sb.append("<th>").append(PropertiesUtil.getProperty("freedaysreport_remainingdayslegal")).append("</th>");
	   				for(int i=0;i<daysinmonth;i++){
	   					//consider also split('.') rather than substring
	   					sb.append("<th>").append(shortDateList.get(i).substring(0, 2)).append("</th>");
	   				}
       			sb.append("</tr>");	
			sb.append("</thead>");
			for (FreeDayUserList fdul : lfd) {
				sb.append("<tr>");
				sb.append("<td>").append(fdul.getUser()).append("</td>");
				sb.append("<td>").append(fdul.getJobrole()).append("</td>");
				sb.append("<td>").append(fdul.getRemainingdays()).append("</td>");
				sb.append("<td>").append(fdul.getTotaldaysleft()).append("</td>");
				for(int i=0;i<daysinmonth;i++){
					FreeDayReportWrapper fdrw = fdul.getCombined().get(i);
					if(fdrw.getStatus()!=null){
						String dispclass = "";
	   					switch(fdrw.getType()){
		   					case 1:
		   						dispclass="colg";
		   						break;
		   					case 2:
		   						dispclass="coly";
		   						break;
		   					case 3:
		   						dispclass="colo";
		   						break;
		   					case 4:
		   						dispclass="colr";
		   						break;
		   					default:
		   						dispclass="";
	   					}
	   					sb.append("<td align='center' class='")
	   					.append(dispclass).append("'>")
	   					.append(getImage(fdrw.getStatus())) //modify here based on status to use the image
	   					.append("</td>");
					} else {
						sb.append("<td></td>");
					}
   				}
				sb.append("</tr>");
			}
//			sb.append("<tr class='footer' >");
//				sb.append("<td style='display : inline;' colspan='").append(daysinmonth+4).append("'>");
//					for(int i=0;i<11;i++){
//						sb.append("<div style='width : 8%;text-align: center;float : left;' >");
//							if(i+1==m){
//								sb.append("<b>").append(monthnames.get(i)).append("</b> ");
//							} else {
//								sb.append(monthnames.get(i));
//							}
//		       			sb.append("</div>");
//					}
//				sb.append("</td>");
//	   		sb.append("</tr>");
//			sb.append("<tr class='footer' >");
//				sb.append("<td colspan='").append(daysinmonth+4).append("'>");
//					sb.append("<i><b>Legend:</b></i> C=completed W=waiting I=inProgress <div class='legr'>Vacation</div> <div class='legg'>Legal</div> <div class='legy'>Cerere</div> <div class='lego'>Recover</div>");
//				sb.append("</td>");
//       		sb.append("</tr>");
			sb.append("<tr class='footer' >");
				sb.append("<td align='center' colspan='").append(daysinmonth+4).append("'>");
					sb.append("<b>").append(monthnames.get(m-1)).append("</b> ");
				sb.append("</td>");
	   		sb.append("</tr>");
			sb.append("<tr class='footer' >");
				sb.append("<td colspan='").append(daysinmonth+4).append("'>");
				String reportLegendMatcher=PropertiesUtil.getProperty("freedaysreport_legend_matchings");
					sb.append("<i><b>").append(PropertiesUtil.getProperty("freedaysreport_legend_title")).append(":</b></i> ")
					.append(PropertiesUtil.getProperty("freedays.report.img.alt.check")).append(MessageFormat.format(reportLegendMatcher , PropertiesUtil.getProperty("freedaysreport_legend_matchings_c"))).append(" ")
					.append(PropertiesUtil.getProperty("freedays.report.img.alt.wait")).append(MessageFormat.format(reportLegendMatcher , PropertiesUtil.getProperty("freedaysreport_legend_matchings_w"))).append(" ")
					.append(PropertiesUtil.getProperty("freedays.report.img.alt.work")).append(MessageFormat.format(reportLegendMatcher , PropertiesUtil.getProperty("freedaysreport_legend_matchings_i"))).append(" ")
					.append(PropertiesUtil.getProperty("freedays_report_legend_typev_sub")).append(MessageFormat.format(reportLegendMatcher , PropertiesUtil.getProperty("freedaysreport_legend_typev"))).append(" ")
					.append(PropertiesUtil.getProperty("freedays_report_legend_typel_sub")).append(MessageFormat.format(reportLegendMatcher , PropertiesUtil.getProperty("freedaysreport_legend_typel"))).append(" ")
					.append(PropertiesUtil.getProperty("freedays_report_legend_typec_sub")).append(MessageFormat.format(reportLegendMatcher , PropertiesUtil.getProperty("freedaysreport_legend_typec"))).append(" ")
					.append(PropertiesUtil.getProperty("freedays_report_legend_typer_sub")).append(MessageFormat.format(reportLegendMatcher , PropertiesUtil.getProperty("freedaysreport_legend_typer")))
					.append(PropertiesUtil.getProperty("freedays_report_legend_typem_sub")).append(MessageFormat.format(reportLegendMatcher , PropertiesUtil.getProperty("freedaysreport_legend_typem")))
					.append(PropertiesUtil.getProperty("freedays_report_legend_typenp_sub")).append(MessageFormat.format(reportLegendMatcher , PropertiesUtil.getProperty("freedaysreport_legend_typenp")));
				sb.append("</td>");
	   		sb.append("</tr>");
		sb.append("</table>");
		
		
		return sb.toString();
	}

	private static final String DEFAULT_REPORT_TEMPLATE_IMAGE = "<img alt='%s' src='%s' />";
	
	private static String getImage(String status) {
		if (status == null || status.length() == 0) throw new IllegalArgumentException("The status argument is required");
		if(status.equals(PropertiesUtil.getProperty("freedays.report.img.alt.check"))){
			return populateImageTemplate(PropertiesUtil.getProperty("freedays.report.img.alt.check"), PropertiesUtil.getProperty("freedays.report.img.link.check.daily"));
		}
		if(status.equals(PropertiesUtil.getProperty("freedays.report.img.alt.wait"))){
			return populateImageTemplate(PropertiesUtil.getProperty("freedays.report.img.alt.wait"), PropertiesUtil.getProperty("freedays.report.img.link.wait.daily"));
		}
		if(status.equals(PropertiesUtil.getProperty("freedays.report.img.alt.work"))){
			return populateImageTemplate(PropertiesUtil.getProperty("freedays.report.img.alt.work"), PropertiesUtil.getProperty("freedays.report.img.link.work.daily"));
		}
		throw new UnsupportedOperationException("no image for such status"); 
	}
	
	private static String populateImageTemplate(String alt, String src){
		Object[] arguments = new Object[2];
		arguments[0] = alt;
		arguments[1]=src;
		String formatted = String.format(DEFAULT_REPORT_TEMPLATE_IMAGE,alt,src);
		return formatted;
	}
	
	
	
	

}
