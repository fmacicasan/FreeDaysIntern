package freedays.timesheet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.app.FreeDay;
import freedays.app.FreeDayL;
import freedays.app.FreeDayM;
import freedays.app.FreeDayRL;
import freedays.app.FreeDayVacation;
import freedays.util.DateUtils;

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
	private String legendCode;
	
	public String getLegendCode() {
        return legendCode;
    }

    public void setLegendCode(String legendCode) {
        this.legendCode = legendCode;
    }

    public Schedule getSchedule(){
		Schedule b = new Schedule();
		b.setStartDate(this.getStart());
		b.setEndDate(this.getEnd());
		b.setPattern(this.getPattern());
		b.setEmployee(TimesheetUser.findTimesheetUserByUsername(this.getUsername()));
		return b;
	}
	
	public boolean isBetweenFilter(Calendar left, Calendar right){
	    return DateUtils.isIntervalOverlapFilter(start, end, left, right);
	}
	
	// precondition: need to overlap (isBeteenFilter need to be true)
	public FreeDayAbstraction createBetweenFilter(Calendar left, Calendar right){
	    FreeDayAbstraction filtered = new FreeDayAbstraction();
	    filtered.setPattern(this.pattern);
	    filtered.setUsername(this.username);
	    filtered.setLegendCode(this.legendCode);
	    
	    Calendar filteredLeft = (DateUtils.isDateBetween(start, left, right))? start : left;
	    filtered.setStart(filteredLeft);
	    Calendar filteredRight = (DateUtils.isDateBetween(end, left, right))? end : right;
	    filtered.setEnd(filteredRight);
	    
	    return filtered;
	}
	
	/**
	 * 
	 * @param month
	 * @param year
	 * @param username
	 * @return Sorted list of abstractions that are between the filter interval
	 */
	public static List<FreeDayAbstraction> getAllGrantedFreeDayAbstractionsFilteredByMonth(Integer month, Integer year, String username){
	    Calendar left = DateUtils.createFirstDayOfMonth(month, year);
	    Calendar right = DateUtils.createLastDayOfMonth(month, year);
	    
//	    System.out.println(DateUtils.printLong(left));
//	    System.out.println(DateUtils.printLong(right));
	    
	    List<FreeDayAbstraction> freeDayAbstractions = getAllGrantedFreeDayAbstractions(username);
	    List<FreeDayAbstraction> filteredFreeDayAbstractions = new ArrayList<FreeDayAbstraction>();
	    for(FreeDayAbstraction abstraction : freeDayAbstractions){
	        if(abstraction.isBetweenFilter(left, right)){
	            filteredFreeDayAbstractions.add(abstraction.createBetweenFilter(left, right));
	        }
	    }
	    
	    return filteredFreeDayAbstractions;
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
			fda.setLegendCode(fdv.getReportLegendCode());
			lfda.add(fda);
		}
		Collections.sort(lfda, new FreeDayAbstractionComparator());
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
			fda.setLegendCode(fd.getReportLegendCode());
			lfda.add(fda);
		}
		Collections.sort(lfda, new FreeDayAbstractionComparator());
		return lfda;
	}
	
	private static List<FreeDayAbstraction> processAllGrantedMedicals(String username) {
		List<FreeDayM> lfd = FreeDayM.getAllGrantedFreeDayMByUsername(username);
		List<FreeDayAbstraction> lfda = new ArrayList<FreeDayAbstraction>();
		for(FreeDayM fd : lfd){
			FreeDayAbstraction fda = new FreeDayAbstraction();
			fda.setStart(fd.getDate());
			fda.setEnd(fd.getEnd());
			fda.setPattern(Pattern.getSpecialPattern(DEFAULT_PHASE_CODE_NOTAPPLICABLE,DEFAULT_PROJECT_CODE_MEDICAL,DEFAULT_LABORBILLING_CODE_NOTAPPLICABLE));
			fda.setUsername(username);
			fda.setLegendCode(fd.getReportLegendCode());
			lfda.add(fda);
		}
		Collections.sort(lfda, new FreeDayAbstractionComparator());
		return lfda;
	}
	
	private static List<FreeDayAbstraction> processAllGrantedRomanianLegislation(String username) {
		List<Calendar> lfd = FreeDayRL.getAllHolidays();
		List<FreeDayAbstraction> lfda = new ArrayList<FreeDayAbstraction>();
		for(Calendar fd : lfd){
			FreeDayAbstraction fda = new FreeDayAbstraction();
			fda.setStart((Calendar)fd.clone());
			fda.setEnd((Calendar)fd.clone());
			fda.setPattern(Pattern.getSpecialPattern(DEFAULT_PHASE_CODE_NOTAPPLICABLE,DEFAULT_PROJECT_CODE_ROMANIANHOLIDAY,DEFAULT_LABORBILLING_CODE_NOTAPPLICABLE));
			fda.setUsername(username);
			fda.setLegendCode(ReportLegend.SPECIAL.getTerm());
			lfda.add(fda);
		}
		Collections.sort(lfda, new FreeDayAbstractionComparator());
		return lfda;
	}
	
}
