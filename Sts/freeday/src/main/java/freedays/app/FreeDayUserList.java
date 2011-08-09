package freedays.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.util.DateUtils;

/**
 * Wrapper class for report generation.
 */
@RooJavaBean
public class FreeDayUserList {
	
	private String user;
	private String jobrole;
	private List<String> freedays;
	private List<Integer> vacations;
	
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
		//TODO: get only between the two values not all vacations
		fdul.setVacations(transformVacation2String4Report(FreeDayVacation.getAllGrantedVacationsByUsername(fdu.getRegularUser().getUsername()),start,end));
		return fdul;
	}

	
	/**
	 * Prepares the list of generated vacations for report printing
	 */
	private static List<Integer> transformVacation2String4Report(List<FreeDayVacation> allgrantvac, Calendar start, Calendar end) {
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
	
	
	

}
