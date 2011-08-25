package freedays.timesheet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.ss.util.CellRangeAddress;
public class POIGenerator implements TimesheetGenerator{
	Employee pEmp;
	Workbook wb;
	
	Pattern sablon;
	private int month;
	private int year;
	ArrayList<Schedule> schedEmp;
	FileOutputStream fileOut;
	Sheet sheet1;
	
	CreationHelper createHelper;
	
	Font underFont;
	CellStyle style;
	CellStyle tablestyle;
	CellStyle tableheadstyle;
	CellStyle tableweekendstyle;
	CellStyle datestyle;
	ArrayList<PhaseLabor> PhLArray;
	
	
	public POIGenerator(Employee pEmp) {
		this.pEmp = pEmp;
		schedEmp = pEmp.getSchedList();
	}
	private Calendar getNextMonday(Calendar now) {
		Integer x = now.get(Calendar.DAY_OF_WEEK);
		if (now.get(Calendar.DAY_OF_WEEK) == 1) {
			now.add(Calendar.DAY_OF_MONTH, 1);
		} 
		else
		  now.add(Calendar.DAY_OF_MONTH, 7 - x + 2);
		return now;
	}
	private Pattern getPatternForDay(Calendar x) {
		for(int i = 0; i < schedEmp.size(); i++)
			if ((x.compareTo(schedEmp.get(i).getStartDate()) >= 0) && (x.compareTo(schedEmp.get(i).getEndDate()) <= 0)) { 
				return schedEmp.get(i).getPattern();
			}
		return null;
		
	}
	private ArrayList<PhaseLabor> getListOfDistinctPhaseLabor(ArrayList<Pattern> pList) {
		ArrayList<PhaseLabor> PhLArr = new ArrayList<PhaseLabor>();
		for(int i = 0; i < pList.size(); i++) {
			for(int j = 0; j < pList.get(i).getArrayPhaseLabor().size(); j++) {
				PhaseLabor currentPhL = pList.get(i).getArrayPhaseLabor().get(j);
				Integer ok = 1;
				for(int k = 0; k < PhLArr.size(); k++) {
					if (PhLArr.get(k).getLaborBilling().getId().equals(currentPhL.getLaborBilling().getId()))
					 if (PhLArr.get(k).getPhase().getID().equals(currentPhL.getPhase().getID()))
					   if (PhLArr.get(k).getPhase().getProject().getID().equals(currentPhL.getPhase().getProject().getID()))  {
						   ok  = 0;					   
				 	   }
			     }
				if (ok == 1) 
					PhLArr.add(currentPhL);			
	
			}
		}	
		return PhLArr;
	}
	private ArrayList<Pattern> getListOfPatterns(Calendar weekEnd, Integer startingDay, Integer endingDay) {
		Calendar firstDayOfWeek = (Calendar) weekEnd.clone();
		Calendar lastDayOfWeek = (Calendar) weekEnd.clone();
		ArrayList<Pattern> pw = new ArrayList<Pattern>();
		firstDayOfWeek.add(Calendar.DAY_OF_MONTH, -7 + startingDay);
		lastDayOfWeek.add(Calendar.DAY_OF_MONTH, -7 + endingDay);
				
		while(lastDayOfWeek.compareTo(firstDayOfWeek) >= 0) {
			pw.add(getPatternForDay(firstDayOfWeek));
			firstDayOfWeek.add(Calendar.DAY_OF_MONTH, 1);
		}
		return pw;
	}
	private void generateHeader(Integer startingRow) {
		Row z = sheet1.createRow(startingRow);
		for(int j = 0; j < 4; j++) { //12
			 Cell mn = z.createCell(j);
			 mn.setCellValue(WeekConstants.columnStrings[j]);
			 mn.setCellStyle(tableheadstyle);
		}
		for(int j = 4; j <= 8; j++) {
			 Cell mn = z.createCell(j);
			 mn.setCellValue(WeekConstants.columnStrings[j]);
			 mn.setCellStyle(tablestyle);
		}
		for(int j = 9; j <= 10; j++) {
			 Cell mn = z.createCell(j);
			 mn.setCellValue(WeekConstants.columnStrings[j]);
			 mn.setCellStyle(tableweekendstyle);
		}
		for(int j = 11; j <= 11; j++) {
			Cell mg = z.createCell(j);
			mg.setCellValue(WeekConstants.columnStrings[j]);
			mg.setCellStyle(tableheadstyle);
		}
	}
	private Calendar getDateFrom(Integer x, Calendar weekEnd) {
		Calendar m = (Calendar) weekEnd.clone();
		m.add(Calendar.DAY_OF_MONTH, -6 + x);
		return m;
		
	}
	private Float generateTableRow(Integer startingRow, int i, Integer startingDay, Integer endingDay, Calendar weekEnd) {
		Row z = sheet1.createRow(startingRow + i);
		Cell firstCol = z.createCell(0);
		firstCol.setCellValue(i);
		firstCol.setCellStyle(tablestyle);
		
		Cell secondCol = z.createCell(1);
		secondCol.setCellValue(PhLArray.get(i-1).getPhase().getProject().getName());
		secondCol.setCellStyle(tablestyle);
		
		Cell thirdCol = z.createCell(2);
		thirdCol.setCellValue(PhLArray.get(i-1).getPhase().getName());
		thirdCol.setCellStyle(tablestyle);
		
		Cell fourCol = z.createCell(3);
		fourCol.setCellValue(PhLArray.get(i-1).getLaborBilling().getName());
		fourCol.setCellStyle(tablestyle);
		
		for(int j = 0; j < startingDay - 1; j++) {
			Cell k = z.createCell(j + 4);
			k.setCellValue("-");
			k.setCellStyle(tablestyle);
		}

		Float suma = (float) 0;
		for(int j = startingDay - 1; j < endingDay; j++) {  				
			Calendar mDay = getDateFrom(j, weekEnd);
			
			Pattern mPattern = getPatternForDay(mDay);
			PhaseLabor phLX = mPattern.getPhaseLabor(PhLArray.get(i-1).getLaborBilling(), PhLArray.get(i-1).getPhase());
			
			
			Float perc = null;
			if (phLX != null)
				perc= phLX.getPercentage();
			else
				perc = (float) 0.0; 
			Float numberOfHours1= perc * mPattern.getNoOfHours() / 100;
			suma +=  numberOfHours1;
			Cell k = z.createCell(j + 4);
			k.setCellValue(numberOfHours1);
			k.setCellStyle(tablestyle);
		}
		for(int j = endingDay; j < 5; j++) {
			Cell k = z.createCell(j + 4);
			k.setCellValue("-");
			k.setCellStyle(tablestyle);
		}
		for(int j = 0; j < 2; j++) {
			Cell k = z.createCell(j + 9);
			k.setCellValue("-");
			k.setCellStyle(tableweekendstyle);
		}
		
		Cell ts = z.createCell(11);
		ts.setCellValue(suma);
		ts.setCellStyle(tablestyle);
		return suma;
	}
	private void generateWeek(Integer startingRow, Integer startingDay, Integer endingDay, Calendar weekEnd) {
		ArrayList<Pattern> PList = new ArrayList<Pattern>();
		PList = getListOfPatterns(weekEnd, startingDay, endingDay);
		PhLArray = getListOfDistinctPhaseLabor(PList);
		Float bigSum = (float) 0;
	    Row WE = sheet1.createRow(startingRow);
		Cell cWELabel = WE.createCell(9);
		cWELabel.setCellValue("W/E:");
		cWELabel.setCellStyle(style);
		Cell WEValue = WE.createCell(10);
		WEValue.setCellStyle(datestyle);
		
		Calendar cloneCurrent = (Calendar) weekEnd.clone();
		cloneCurrent.add(Calendar.DAY_OF_MONTH, -6);
		Integer daysInMonth = 0;
		if ((cloneCurrent.get(Calendar.MONTH) != weekEnd.get(Calendar.MONTH))) {
			if ((endingDay != 5) || ((endingDay == 5) && (((weekEnd.get(Calendar.DAY_OF_MONTH)) == 1) || ((weekEnd.get(Calendar.DAY_OF_MONTH)) == 2)))) {				
			daysInMonth = cloneCurrent.getActualMaximum(Calendar.DAY_OF_MONTH);
			Integer currentDay = cloneCurrent.get(Calendar.DAY_OF_MONTH);
			cloneCurrent.add(Calendar.DAY_OF_MONTH, daysInMonth - currentDay);
			}
			else
			  cloneCurrent = weekEnd;
		}
		else
			cloneCurrent = weekEnd;
		WEValue.setCellValue((java.util.Date) cloneCurrent.getTime());
		startingRow += 2;
	    for(int i = 0; i <= PhLArray.size(); i++) {
	    	if (i == 0) {
    			generateHeader(startingRow);
	    	}
	    	else {
	    		bigSum += generateTableRow(startingRow, i, startingDay, endingDay, weekEnd);    		
	    	}	   
	    }
    	Row z = sheet1.createRow(startingRow + PhLArray.size() + 1);
    	CellRangeAddress region = new CellRangeAddress(startingRow + PhLArray.size() + 1,startingRow + PhLArray.size() + 1, 0, 3);
    	Cell kFirst = z.createCell(0);
		sheet1.addMergedRegion(region);
    	kFirst.setCellValue("TOTAL HOURS WORKED");
    	kFirst.setCellStyle(tableheadstyle);
    	for(int j = 1; j < 4; j++) {
    		 Cell k = z.createCell(j);
    		 k.setCellValue("");
    		 k.setCellStyle(tableheadstyle);
    	}
    	for(int j = 4; j < 3 + startingDay; j++) {
    		Cell k = z.createCell(j);
	    	k.setCellValue("-");
	    	k.setCellStyle(tableheadstyle);
    	}
	    for(int j = 3 + startingDay; j <= 3 + endingDay; j++) {
	    	Cell k = z.createCell(j);
	    	Calendar mDay = getDateFrom(j - 4, weekEnd);
	    	Float sum = (float) 0;
			Pattern mPattern = getPatternForDay(mDay);
			sum +=  mPattern.getNoOfHours();
	    	k.setCellValue(sum);
	    	k.setCellStyle(tableheadstyle);
	    }
	    for(int j = 3 + endingDay + 1; j < 9; j++) {
	    	Cell k = z.createCell(j);
	    	k.setCellValue("-");
	    	k.setCellStyle(tableheadstyle);
	    }
	    for(int j = 9; j <= 10; j++) {
	    	Cell k = z.createCell(j);
	    	k.setCellValue("-");
	    	k.setCellStyle(tableheadstyle);
	    }
	    Cell k = z.createCell(11);
	    k.setCellValue(bigSum);
	    k.setCellStyle(tableheadstyle);
	}
	private int howManyDays(Calendar t) {
			int K = t.getActualMaximum(Calendar.DAY_OF_MONTH) - t.get(Calendar.DAY_OF_MONTH);
			return Math.min(K + 1, 5);
	}
	private void styleMe() {
		style = wb.createCellStyle();
	    style.setAlignment(style.ALIGN_CENTER);
	    createHelper = wb.getCreationHelper();
	    Font f1 = wb.createFont();
	    f1.setUnderline((byte) 12);
	    
	    datestyle = wb.createCellStyle();
	    datestyle.setAlignment(style.ALIGN_CENTER);
	    datestyle.setDataFormat(createHelper.createDataFormat().getFormat("mm/dd/yy"));
	    datestyle.setFont(f1);
	    
	    tablestyle = wb.createCellStyle();
	    tablestyle.setAlignment(style.ALIGN_CENTER);
	    tablestyle.setBorderBottom((short)1);
	    tablestyle.setBorderLeft((short)1);
	    tablestyle.setBorderRight((short)1);
	    tablestyle.setBorderTop((short)1);
	    
	    tableheadstyle = wb.createCellStyle();
	    tableheadstyle.setAlignment(style.ALIGN_CENTER);
	    tableheadstyle.setBorderBottom((short)1);
	    tableheadstyle.setBorderLeft((short)1);
	    tableheadstyle.setBorderRight((short)1);
	    tableheadstyle.setBorderTop((short)1);	 
	    tableheadstyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	    tableheadstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    
	    tableweekendstyle = wb.createCellStyle();
	    tableweekendstyle.setAlignment(style.ALIGN_CENTER);
	    tableweekendstyle.setBorderBottom((short)1);
	    tableweekendstyle.setBorderLeft((short)1);
	    tableweekendstyle.setBorderRight((short)1);
	    tableweekendstyle.setBorderTop((short)1);	 
	    tableweekendstyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
	    tableweekendstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    
	}
	private void generateDocHeader() {
		CellRangeAddress region = CellRangeAddress.valueOf("A1:B1");
		sheet1.addMergedRegion( region );
	    sheet1.setDisplayGridlines(false);
	    Row row = sheet1.createRow((short)0);	 
	    Cell cellCompany = row.createCell(0);
	    cellCompany.setCellValue("LANGUAGEWEAVER, INC.");
	    Cell cellNameLabel = row.createCell(7);
	    cellNameLabel.setCellValue("EMPLOYEE NAME");
	    Cell cellName = row.createCell(8);
	    cellName.setCellValue(pEmp.getName());
	    row = sheet1.createRow((short)1);
	    Cell cellPositionLabel = row.createCell(7);
	    cellPositionLabel.setCellValue("POSITION");
	    Cell cellPosition = row.createCell(8);
	    cellPosition.setCellValue(pEmp.getPosition());
	}
	public void generateDoc(String workbookname, int genmonth, int genyear) {
		wb = new HSSFWorkbook();
		styleMe();	    
		//PhLArray = xPattern.getArrayPhaseLabor();	
	    fileOut = null;
		try {
			fileOut = new FileOutputStream(new File(workbookname));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    String safeName = WorkbookUtil.createSafeSheetName("TimeSheet");
	    CreationHelper createHelper = wb.getCreationHelper();
	    sheet1 = wb.createSheet(safeName);
		generateDocHeader();
	    year = genyear;
	    month = genmonth;
	    Calendar TimeSheetCalendar = new GregorianCalendar(year, month, 1);
	    Integer dayOfWeek = TimeSheetCalendar.get(Calendar.DAY_OF_WEEK);
	    Integer noOfRows = 5;
	    if ((dayOfWeek > 1) && (dayOfWeek < 7)) { //zi saptamanala
	    	//2-Luni,3-Marti,4-Miercuri,5-Joi,6-Vineri
	    	Calendar auxWE = (Calendar) TimeSheetCalendar.clone();
	    	auxWE.add(Calendar.DAY_OF_MONTH, 8 - dayOfWeek);
	    	generateWeek(noOfRows, dayOfWeek - 1, 5, auxWE);
	    	noOfRows += PhLArray.size() + WeekConstants.rowsBetweenTables;
	    	TimeSheetCalendar = getNextMonday(TimeSheetCalendar);
	    	auxWE = (Calendar) TimeSheetCalendar.clone();
	    	auxWE.add(Calendar.DAY_OF_MONTH, 6);
	    	generateWeek(noOfRows, 1, 5, auxWE);
	    	noOfRows += PhLArray.size() + WeekConstants.rowsBetweenTables;
	    }
	    else {
	    	TimeSheetCalendar = getNextMonday(TimeSheetCalendar);
	    	Calendar auxWE = (Calendar) TimeSheetCalendar.clone();
	    	auxWE.add(Calendar.DAY_OF_MONTH, 6);
	    	generateWeek(noOfRows, 1, 5,auxWE);
	    	noOfRows += PhLArray.size() + WeekConstants.rowsBetweenTables;
	    }
	    Integer noOfDays = TimeSheetCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	    
	    while(TimeSheetCalendar.get(Calendar.DAY_OF_MONTH) + 7 <= noOfDays) { 
	    	TimeSheetCalendar.add(Calendar.DAY_OF_MONTH, 7);
	    	Calendar auxWE = (Calendar) TimeSheetCalendar.clone();
	    	auxWE.add(Calendar.DAY_OF_MONTH, 6);
	    	generateWeek(noOfRows, 1, howManyDays(TimeSheetCalendar),auxWE);
	    	noOfRows += PhLArray.size() + WeekConstants.rowsBetweenTables;
	    }
	    for(short i = 0; i <= 20; i++) {
	    	sheet1.autoSizeColumn(i);
	    }
	    try {
			wb.write(fileOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    try {
			fileOut.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
