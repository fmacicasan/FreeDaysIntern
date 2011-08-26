package freedays.timesheet;
import java.util.Calendar;
import java.util.Date;

public class Schedule {
	private Integer id;
	private java.util.Date start_date;
	private java.util.Date end_date;
	private Pattern p;
	private Employee e;
	public Schedule(Date a, Date b, Pattern sablon, Employee k) {
		// TODO Auto-generated constructor stub
		start_date = a;
		end_date = b;
		p = sablon;
		e = k;
	}
	public Calendar getStartDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(start_date);
		return cal;
	}
	public Calendar getEndDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(end_date);
		return cal;
	}
	public Pattern getPattern() {
		return p;
	}
	public Employee getEmployee() {
		return e;
	}
}
