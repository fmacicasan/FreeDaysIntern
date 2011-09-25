package freedays.timesheet;

import java.io.File;

public interface TimesheetGenerator {
	public File generateDoc(String wbname, int year, int month);
}
