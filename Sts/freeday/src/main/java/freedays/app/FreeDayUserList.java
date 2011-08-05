package freedays.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.app.FreeDayRequest.RequestType;
import freedays.domain.RegularUser;
import freedays.util.DateUtils;

@RooJavaBean
public class FreeDayUserList {
	
	private String user;
	private String jobrole;
	private List<String> freedays;
	
	public static FreeDayUserList generateFreeDaysList(String username){
		FDUser fdu = FDUser.findFDUserByUsername(username);
		return FreeDayUserList.generateFreeDaysList(fdu);
	}
	
	public static FreeDayUserList generateFreeDaysList(FDUser fdu){
		FreeDayUserList fdul = new FreeDayUserList();
		fdul.setUser(fdu.getRegularUser().getFullName());
		fdul.setJobrole(fdu.getJobrole().toString());
		fdul.setFreedays(transformFreeDay2String4Report(FreeDay.getAllGrantedFreeDayByUsername(fdu.getRegularUser().getUsername())));
		return fdul;
	}

	private static List<String> transformFreeDay2String4Report(List<FreeDay> allGrantedFreeDayByUsername) {
		List<String> ls = new ArrayList<String>();
		for (FreeDay fd : allGrantedFreeDayByUsername) {
			// ugly hack... think of some better solution for the addition of the list
			//return list all the time and use addAll
				ls.addAll(fd.reportPrint());
			
		}
		return ls;
	}

	public static Long computeTableWidth(List<FreeDayUserList> lfd) {
		long max = -1;
		for (FreeDayUserList fdul : lfd) {
			long size = fdul.getFreedays().size();
			if(max < size)max=size;
		}
		return max;
	}
	

	public static FreeDayUserList generateVacationList(FDUser fdu,Calendar start, Calendar end) {
		FreeDayUserList fdul = new FreeDayUserList();
		fdul.setUser(fdu.getRegularUser().getFullName());
		fdul.setJobrole(fdu.getJobrole().toString());
		//TODO: get only between the two values not all vacations
		fdul.setFreedays(transformVacation2String4Report(FreeDayVacation.getAllGrantedVacationsByUsername(fdu.getRegularUser().getUsername()),start,end));
		return fdul;
	}

	private static List<String> transformVacation2String4Report(List<FreeDayVacation> allgrantvac, Calendar start, Calendar end) {
		long span = DateUtils.dateDifferenceInDays(start, end);
		List<String> list = new LinkedList<String>();
		for (FreeDayVacation vacation : allgrantvac) {
			if(vacation.getDate().after(start)){
				long before = DateUtils.dateDifferenceInDays(start, vacation.getDate());
				for(long i=0;i<before;i++){
					list.add("no");
				}
				long during = (before+vacation.getSpan()<span)?before+vacation.getSpan():span;
				for(long i=before;i<during;i++){
					list.add("yes");
				}
				for(long i=during;i<span;i++){
					list.add("no");
				}
			}
		}
		return list;
	}
	
	
	

}
