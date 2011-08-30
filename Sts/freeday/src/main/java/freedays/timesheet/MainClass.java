package freedays.timesheet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainClass {

	/**
	 * @param args
	 */
	public static Date getDateFrom(int year, int month, int day) {
		Date a = null;
		String date = year + "/" + month + "/" + day;
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/M/d");
	    try {
			a = formatter.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;		
	}
	public void doMain(TimesheetUser k) {
//		TimesheetUser k = new TimesheetUser();
//		k.setName("Marcel Codrea");
//		k.getRegularUser().setFirstname("Marcel");
		//k.getRegularUser().setLastname("bun");
//		k.setPosition("Trainee");
//		k.setId(1);
//		
//		Project p1 = new Project();
//		p1.setCode("09037");
//		p1.setName("Hummingbird IV(BBN)");
//		
//		Project p2 = new Project();
//		p2.setCode("03007");
//		p2.setName("LW Engineering");
//		
//		Phase ph1 = new Phase();
//		ph1.setCode("809");
//		ph1.setName("Whitney");
//		
//		Phase ph2 = new Phase();
//		ph2.setCode("810");
//		ph2.setName("Whitney");
//		
//		Phase ph3 = new Phase();
//		ph3.setCode("801");
//		ph3.setName();
//		Phase ph4 = new Phase();
//		Phase ph5 = new Phase();
		
//		Phase ph1 = new Phase("Whitney","809",p1);
//		Phase ph2 = new Phase("Iceberg","810",p1);
//		Phase ph3 = new Phase("Core Products SMTS","801",p2);
//		Phase ph4 = new Phase("Customer Support","808",p2);
//		Phase ph5 = new Phase("Application Engineering","308",p2);
//		
//		LaborBilling lb1 = new LaborBilling("1002","Project Management");
//		LaborBilling lb2 = new LaborBilling("1003","Development Engineer");
//		
//		PhaseLabor plab1 = new PhaseLabor("1", lb1, ph1, (float)40);
//		PhaseLabor plab2 = new PhaseLabor("2", lb1, ph2, (float)10);
//		PhaseLabor plab3 = new PhaseLabor("3", lb1, ph3, (float)10);
//		PhaseLabor plab4 = new PhaseLabor("4", lb1, ph4, (float)10);
//		PhaseLabor plab5 = new PhaseLabor("5", lb1, ph5, (float)30);
//		
//		PhaseLabor plab6 = new PhaseLabor("6", lb2, ph2, (float)40);
//		PhaseLabor plab7 = new PhaseLabor("7", lb1, ph3, (float)40);
//		PhaseLabor plab8 = new PhaseLabor("8", lb2, ph3, (float)20);
//		
//		Pattern sablon = new Pattern(100);
//		sablon.addPhL(plab1);
//		sablon.addPhL(plab2);
//		sablon.addPhL(plab3);
//		sablon.addPhL(plab4);
//		sablon.addPhL(plab5);
//		
//		Pattern sablon2 = new Pattern(8);
//		sablon2.addPhL(plab6);
//		sablon2.addPhL(plab7);
//		sablon2.addPhL(plab8);
//		
//		int year1 = 2011;
//	    int month1 = 1;
//	    int day1 = 1;
//	    Date a = getDateFrom(year1,month1,day1);	 
//	    int year2 = 2011;
//	    int month2 = 2;
//	    int day2 = 15;
//	    Date b = getDateFrom(year2,month2,day2);
//	    int year3 = 2011;
//	    int month3 = 2;
//	    int day3 = 16;
//	    Date c = getDateFrom(year3,month3,day3);
//	    int year4 = 2011;
//	    int month4 = 2;
//	    int day4 = 17;
//	    Date d = getDateFrom(year4,month4,day4);
//	    int year5 = 2011;
//	    int month5 = 2;
//	    int day5 = 18;
//	    Date f = getDateFrom(year5,month5,day5);	  
//	    int year6 = 2013;
//	    int month6 = 12;
//	    int day6 = 31;
//	    Date g = getDateFrom(year6, month6, day6);		
//		Schedule test = new Schedule(a, b, sablon, k);
//		Schedule test2 = new Schedule(c, d, sablon2, k);
//		Schedule test3 = new Schedule(f,g,sablon,k);
//		k.addSchedule(test);
//		k.addSchedule(test2);
//		k.addSchedule(test3);
//		
		for (int i = 0; i < 12; i++) {
			TimesheetGenerator x = new POIGenerator(k);
			x.generateDoc("..\\..\\..\\timesheets\\TimeSheet" + " " + k.getRegularUser().getFirstname() + " " + WeekConstants.monthStrings[i] + ".xls", i, 2012);
		}
	}
}
	
