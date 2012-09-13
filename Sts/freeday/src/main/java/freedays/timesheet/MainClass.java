package freedays.timesheet;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import freedays.util.MailUtils;

public class MainClass {

	private static final int TIMESHEET_YEAR = 2012;

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
		for (int i = 7; i < 9; i++) {
			TimesheetGenerator x = new POIGenerator(k);
			String fileName = "Timesheet " + k.getRegularUser().getFullName()
					+ " " + WeekConstants.monthStrings[i] + ".xls";
			x.generateDoc(fileName, i, TIMESHEET_YEAR);
		}
	}

	public void doMain(TimesheetUser k, Integer month) {
		TimesheetGenerator x = new POIGenerator(k);
		String fileName = "Timesheet" + " " + k.getRegularUser().getFullName()
				+ " " + WeekConstants.monthStrings[month] + ".xls";
		File f = x.generateDoc(fileName, month,
				TIMESHEET_YEAR);
		MailUtils.sendTimesheet(k.getRegularUser().getEmail(), k
				.getRegularUser().getFullName(), f);
	}
}
