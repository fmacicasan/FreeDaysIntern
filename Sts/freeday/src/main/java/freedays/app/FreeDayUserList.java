package freedays.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Component;

import freedays.security.UserContextService;
import freedays.util.DateUtils;

/**
 * Wrapper class for report generation.
 */

@Component
@RooJavaBean
@Configurable
public class FreeDayUserList {
	
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
	public static FreeDayUserList generateFreeDaysList(String username){
		FDUser fdu = FDUser.findFDUserByUsername(username);
		return FreeDayUserList.generateFreeDaysList(fdu);
	}
	
	/**
	 * Generates a list of granted FreeDays requests for the provided FDUser
	 */
	public static FreeDayUserList generateFreeDaysList(FDUser fdu){
		FreeDayUserList fdul = new FreeDayUserList();
		fdul.setUser(fdu.getRegularUser().getFullName());
		fdul.setJobrole(fdu.getJobrole().toString());
		fdul.setRemainingdays(fdu.computeAvailableFreeDays());
		fdul.setTotaldaysleft(fdu.computeteAvailableFreeDaysTotal());
		fdul.setFreedays(transformFreeDay2String4Report(FreeDay.getAllGrantedFreeDayByUsername(fdu.getRegularUser().getUsername())));
		return fdul;
	}

	/**
	 * Prepares the list of generated FreeDay-s for report printing
	 */
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
		fdul.setUser(fdu.getRegularUser().getFullName());
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
	
	public static FreeDayUserList generateAllFreeDays(FDUser fdu,int month){
		FreeDayUserList fdul = new FreeDayUserList();
		fdul.setUser(fdu.getRegularUser().getFullName());
		fdul.setJobrole(fdu.getJobrole().toString());
		
		fdul.setTotaldaysleft(fdu.computeteAvailableFreeDaysTotal());
		//TODO: get only between the two values not all vacations
		List<FreeDay> lfdu;
		UserContextService ucs = fdul.getUserContextService();
		if(ucs.isHR()){
			lfdu = FreeDay.getAllGrantedFreeDayByUsername(fdu.getRegularUser().getUsername());
		} else {
			fdul.setRemainingdays(fdu.computeAvailableFreeDays());
			lfdu =FreeDay.getAllNotFailedRequestsByUsername(fdu.getRegularUser().getUsername());
		}
		fdul.setCombined(tranformFreeDay2Integer4Report(lfdu,month));
		return fdul;
	}
	
	private static List<FreeDayReportWrapper> tranformFreeDay2Integer4Report(List<FreeDay> freedays,int month){
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
				Calendar start = fdv.getBeginning();
				Calendar end = DateUtils.dateAddBusinessDay(start, fdv.getSpan());
				//replaced before by compareTo to test equality
				for(Calendar c = (Calendar)start.clone();c.compareTo(end)<=0;c.add(Calendar.DAY_OF_YEAR, 1)){
					if(DateUtils.isSameMonth(c, month)){
						FreeDayReportWrapper fdrw = new FreeDayReportWrapper();
						fdrw.setType(fd.getType().ordinal()+1);
						fdrw.setStatus(fd.getStatus().toString().substring(0,1));
						list.set(DateUtils.getDay(c)-1,fdrw );
					}
				}
			} else {
				if(DateUtils.isSameMonth(fd.getDate(),month)){//remove this if you get the freedays already filtered by month
					FreeDayReportWrapper fdrw = new FreeDayReportWrapper();
					fdrw.setType(fd.getType().ordinal()+1);
					fdrw.setStatus(fd.getStatus().toString().substring(0,1));
					list.set(DateUtils.getDay(fd.getDate())-1,fdrw );
				}
			}
		}
		
		return list;
	}
	
	
	
	

}
