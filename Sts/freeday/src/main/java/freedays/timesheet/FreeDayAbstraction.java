package freedays.timesheet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.app.FreeDay;
import freedays.app.FreeDayL;
import freedays.app.FreeDayM;
import freedays.app.FreeDayRL;
import freedays.app.FreeDayVacation;

@RooJavaBean
public class FreeDayAbstraction {

	private static final String DEFAULT_PROJECT_CODE_ROMANIANHOLIDAY = "99992";
	private static final String DEFAULT_PROJECT_CODE_MEDICAL = "99993";
	private static final String DEFAULT_PROJECT_CODE_VACATION = "99991";
	private static final String DEFAULT_LABORBILLING_CODE_NOTAPPLICABLE = "1001";
	private static final String DEFAULT_PHASE_CODE_NOTAPPLICABLE = "999";
	private Calendar start;
	private Calendar end;
	
	private Pattern pattern;
	private String username;
	
	public Schedule getSchedule(){
		Schedule b = new Schedule();
		b.setStartDate(this.getStart());
		b.setEndDate(this.getEnd());
		b.setPattern(this.getPattern());
		b.setEmployee(TimesheetUser.findTimesheetUserByUsername(this.getUsername()));
		return b;
	}
	
	public static List<FreeDayAbstraction> getAllGrantedFreeDayAbstractions(String username){
		if(username == null || username.length() == 0) throw new IllegalArgumentException("the username argument is required");
		List<FreeDayAbstraction> lfda = new ArrayList<FreeDayAbstraction>();
		
		//dont add this ones for the ones that did not take the 10 days
		lfda.addAll(processAllGrantedVacations(username));
		lfda.addAll(processAllGrantedLegals(username));
		
		
		lfda.addAll(processAllGrantedMedicals(username));
		lfda.addAll(processAllGrantedRomanianLegislation(username));
		
		return lfda;
	}

	private static List<FreeDayAbstraction> processAllGrantedVacations(String username) {
		List<FreeDayVacation> lfdv = FreeDayVacation.getAllGrantedVacationsByUsername(username);
		List<FreeDayAbstraction> lfda = new ArrayList<FreeDayAbstraction>();
		for(FreeDayVacation fdv : lfdv){
			FreeDayAbstraction fda = new FreeDayAbstraction();
			fda.setStart(fdv.getDate());
			fda.setEnd(fdv.getEnd());
			fda.setPattern(Pattern.getSpecialPattern(DEFAULT_PHASE_CODE_NOTAPPLICABLE,DEFAULT_PROJECT_CODE_VACATION,DEFAULT_LABORBILLING_CODE_NOTAPPLICABLE));
			fda.setUsername(username);
			lfda.add(fda);
		}
		return lfda;
	}
	
	private static List<FreeDayAbstraction> processAllGrantedLegals(String username) {
		List<FreeDayL> lfd = FreeDayL.getAllGrantedFreeDayLByUsername(username);
		List<FreeDayAbstraction> lfda = new ArrayList<FreeDayAbstraction>();
		for(FreeDayL fd : lfd){
			FreeDayAbstraction fda = new FreeDayAbstraction();
			fda.setStart(fd.getDate());
			fda.setEnd(fd.getDate());
			fda.setPattern(Pattern.getSpecialPattern(DEFAULT_PHASE_CODE_NOTAPPLICABLE,DEFAULT_PROJECT_CODE_VACATION,DEFAULT_LABORBILLING_CODE_NOTAPPLICABLE));
			fda.setUsername(username);
			lfda.add(fda);
		}
		return lfda;
	}
	
	private static List<FreeDayAbstraction> processAllGrantedMedicals(String username) {
		List<FreeDayM> lfd = FreeDayM.getAllGrantedFreeDayMByUsername(username);
		List<FreeDayAbstraction> lfda = new ArrayList<FreeDayAbstraction>();
		for(FreeDayM fd : lfd){
			FreeDayAbstraction fda = new FreeDayAbstraction();
			fda.setStart(fd.getDate());
			fda.setEnd(fd.getDate());
			fda.setPattern(Pattern.getSpecialPattern(DEFAULT_PHASE_CODE_NOTAPPLICABLE,DEFAULT_PROJECT_CODE_MEDICAL,DEFAULT_LABORBILLING_CODE_NOTAPPLICABLE));
			fda.setUsername(username);
			lfda.add(fda);
		}
		return lfda;
	}
	
	private static List<FreeDayAbstraction> processAllGrantedRomanianLegislation(String username) {
		List<Calendar> lfd = FreeDayRL.getAllHolidays();
		List<FreeDayAbstraction> lfda = new ArrayList<FreeDayAbstraction>();
		for(Calendar fd : lfd){
			FreeDayAbstraction fda = new FreeDayAbstraction();
			fda.setStart(fd);
			fda.setEnd(fd);
			fda.setPattern(Pattern.getSpecialPattern(DEFAULT_PHASE_CODE_NOTAPPLICABLE,DEFAULT_PROJECT_CODE_ROMANIANHOLIDAY,DEFAULT_LABORBILLING_CODE_NOTAPPLICABLE));
			fda.setUsername(username);
			lfda.add(fda);
		}
		return lfda;
	}
	


	
}
