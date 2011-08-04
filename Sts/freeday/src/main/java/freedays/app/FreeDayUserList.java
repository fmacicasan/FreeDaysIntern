package freedays.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.domain.RegularUser;

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

	private static List<String> transformFreeDay2String4Report(
			List<FreeDay> allGrantedFreeDayByUsername) {
		List<String> ls = new ArrayList<String>();
		for (FreeDay fd : allGrantedFreeDayByUsername) {
			ls.add(fd.reportPrint());
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
	

}
