package freedays.timesheet;

import java.io.File;

import org.apache.poi.ss.usermodel.Sheet;
public interface TimesheetGenerator {

	public File generateDoc(String wbname, int year, int month);
	
	public void generateDocHeader(Sheet sheet1, int month);
}
