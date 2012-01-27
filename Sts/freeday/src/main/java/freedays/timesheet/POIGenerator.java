package freedays.timesheet;
import java.io.File;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.WorkbookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import freedays.util.DateUtils;


@Configurable
public class POIGenerator implements TimesheetGenerator{

	
	
	private static final String DEFAULT_TIMESHEET_WEEKLY_TOTAL_HOURS_WORK = "TOTAL HOURS WORKED";
	private static final String DEFAULT_TIMESHEET_HEADER_LABEL_DEPARTMENT = "DEPARTMENT";
	private static final String DEFAULT_TIMESHEET_HEADER_LABEL_POSITION = "POSITION";
	private static final String DEFAULT_TIMESHEET_HEADER_LABEL_EMPLOYEE = "EMPLOYEE NAME";
	private static final String DEFAULT_TIMESHEET_HEADER_LABEL_COMPANY = "LANGUAGEWEAVER, INC.";
	

	
	TimesheetUser pEmp;
	Workbook wb;
	
	//Pattern sablon;
	//private int month;
	//private int year;
	/**
	 *  Employee's schedules
	 */
	List<Schedule> schedEmp;
	FileOutputStream fileOut;
	Sheet sheet1;
	
	private int noOfRows;
	
	CreationHelper createHelper;
	
	Font underFont;
	CellStyle underlinedStyle;
	CellStyle style;
	CellStyle tablestyle;
	CellStyle tableheadstyle;
	CellStyle tableweekendstyle;
	CellStyle datestyle;
	/**
	 *   Array of PhaseLabors specific to a certain week(these constitute the table header
	 */
	ArrayList<PhaseLabor> PhLArray;
	
	@Autowired
	DecimalFormat decimalFormat;
	
	
	public POIGenerator(TimesheetUser pEmp) {
		this.pEmp = pEmp;
		schedEmp = pEmp.getScheduleLst();		
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
		//System.out.println("getPatternForDay "+DateUtils.printShortDateYear(x));
		for(int i = 0; i < schedEmp.size(); i++){
			//System.out.println("schedEmp start"+DateUtils.printShortDateYear(schedEmp.get(i).getStartDate()) +" end "+DateUtils.printShortDateYear(schedEmp.get(i).getEndDate()));
		
			if ((x.compareTo(schedEmp.get(i).getStartDate()) >= 0) && (x.compareTo(schedEmp.get(i).getEndDate()) <= 0)) { 
				return schedEmp.get(i).getPattern();
			}
		}
		return null;
		
	}
	
	/**
	 * "I think" it gets the week's table header list of labour codes
	 * @param pList
	 * @return
	 */
	private ArrayList<PhaseLabor> getListOfDistinctPhaseLabor(ArrayList<Pattern> pList) {
		////System.out.println("line 104"+pList);
		ArrayList<PhaseLabor> PhLArr = new ArrayList<PhaseLabor>();
		for(int i = 0; i < pList.size(); i++) {
			//System.out.println("test");
			Pattern p = pList.get(i);
			//System.out.println("el pattern is"+p);
			List<PhaseLabor> lph = p.getPhaseLaborLst();
			//System.out.println("it is"+lph);
			for(int j = 0; j < lph.size(); j++) {
				//System.out.println("current"+i+"asd"+j);
				
				PhaseLabor currentPhL = pList.get(i).getPhaseLaborLst().get(j);
				//System.out.println("current"+currentPhL);
				Integer ok = 1;
				for(int k = 0; k < PhLArr.size(); k++) {
					if (PhLArr.get(k).getLaborbilling().getId().equals(currentPhL.getLaborbilling().getId()))
					 if (PhLArr.get(k).getPhase().getId().equals(currentPhL.getPhase().getId()))
					   if (PhLArr.get(k).getProject().getId().equals(currentPhL.getProject().getId()))  {
						   ok  = 0;					   
				 	   }
			     }
				if (ok == 1)
					PhLArr.add(currentPhL);			
	
			}
		}
		//System.out.println("line done");
		return PhLArr;
	}
	private ArrayList<Pattern> getListOfPatterns(Calendar weekEnd, Integer startingDay, Integer endingDay) {
		//System.out.println("Entry getListOfPatterns"+DateUtils.printShortDateYear(weekEnd)+"start"+startingDay+"end"+endingDay);
		Calendar firstDayOfWeek = (Calendar) weekEnd.clone();
		Calendar lastDayOfWeek = (Calendar) weekEnd.clone();
		ArrayList<Pattern> pw = new ArrayList<Pattern>();
		firstDayOfWeek.add(Calendar.DAY_OF_MONTH, -7 + startingDay);
		lastDayOfWeek.add(Calendar.DAY_OF_MONTH, -7 + endingDay);
				
		while(lastDayOfWeek.compareTo(firstDayOfWeek) >= 0) {
			Pattern p = getPatternForDay(firstDayOfWeek);
			//System.out.println("the retrieved pattenrn"+p+" for "+DateUtils.printShortDateYear(firstDayOfWeek));
			pw.add(p);
			firstDayOfWeek.add(Calendar.DAY_OF_MONTH, 1);
		}
		//System.out.println("getListOfPattenrs "+pw);
		
		return pw;
	}
	/**
	 * The table's header.
	 * @param startingRow
	 */
	private void generateHeader(Integer startingRow) {
		Row z = sheet1.createRow(startingRow);
		for(int j = 0; j < 4; j++) {
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
	
	/**
	 * the actual footer of the table
	 * @param startingRow
	 * @param startingDay
	 * @param endingDay
	 * @param weekEnd
	 * @param bigSum
	 */
	private void generateTableFooter(int startingRow, int startingDay, int endingDay, Calendar weekEnd, float bigSum) {
		Row z = sheet1.createRow(startingRow + PhLArray.size() + 1);
    	CellRangeAddress region = new CellRangeAddress(startingRow + PhLArray.size() + 1,startingRow + PhLArray.size() + 1, 0, 3);
    	Cell kFirst = z.createCell(0);
		sheet1.addMergedRegion(region);
    	kFirst.setCellValue(DEFAULT_TIMESHEET_WEEKLY_TOTAL_HOURS_WORK);
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
	    	k.setCellValue(decimalFormat.format(sum));
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
	    k.setCellValue(decimalFormat.format(bigSum));
	    k.setCellStyle(tableheadstyle);
	}
//	public Schedule getScheduleFromFreeDayItem(FreeDayVacation x) {
//		Schedule b = new Schedule();
//		b.setStartDate(x.getDate());
//		b.setEndDate(x.getEnd());
//		b.setPattern(Pattern.getPatternForVacation());
//		b.setEmployee(pEmp);
//		return b;
//		
//	}
	private Float generateTableRow(Integer startingRow, int i, Integer startingDay, Integer endingDay, Calendar weekEnd) {		
		Row z = sheet1.createRow(startingRow + i);
		Cell firstCol = z.createCell(0);
		firstCol.setCellValue(i);
		firstCol.setCellStyle(tablestyle);
		
		Cell secondCol = z.createCell(1);
		secondCol.setCellValue(PhLArray.get(i-1).getProject().getCode());
		secondCol.setCellStyle(tablestyle);
		
		Cell thirdCol = z.createCell(2);
		thirdCol.setCellValue(PhLArray.get(i-1).getPhase().getCode());
		thirdCol.setCellStyle(tablestyle);
		
		Cell fourCol = z.createCell(3);
		fourCol.setCellValue(PhLArray.get(i-1).getLaborbilling().getCode());
		fourCol.setCellStyle(tablestyle);
		
		for(int j = 0; j < startingDay - 1; j++) {
			Cell k = z.createCell(j + 4);
			k.setCellValue("-");
			k.setCellStyle(tablestyle);
		}

		//legatura intre phase labour si zi calendaristica
		Float suma = (float) 0;
		for(int j = startingDay - 1; j < endingDay; j++) {  				
			Calendar mDay = getDateFrom(j, weekEnd);
			
			Pattern mPattern = getPatternForDay(mDay);
			PhaseLabor phLX = mPattern.getPhaseLabor(PhLArray.get(i-1).getLaborbilling(), PhLArray.get(i-1).getPhase(), PhLArray.get(i-1).getProject());
			
			
			Float perc = null;
			if (phLX != null)
				perc= phLX.getPercentage();
			else
				perc = (float) 0.0; 
			
			Float numberOfHours1= perc * mPattern.getNoOfHours() / 100;
			suma +=  numberOfHours1;
			Cell k = z.createCell(j + 4);
			k.setCellValue(decimalFormat.format(numberOfHours1));
			k.setCellStyle(tablestyle);
		}
		//end here
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
		ts.setCellValue(decimalFormat.format(suma));
		ts.setCellStyle(tablestyle);
		return suma;
	}
	
	/**
	 * Asta trebe sa o inteleg!
	 * @param startingRow
	 * @param startingDay 1-5 as Monday-Friday
	 * @param endingDay 1-5 as Monday-Friday
	 * @param weekEnd last day of week
	 */
	private void generateWeek(Integer startingRow, Integer startingDay, Integer endingDay, Calendar weekEnd) {
		ArrayList<Pattern> pList = new ArrayList<Pattern>();
		//System.out.println("before");
		pList = getListOfPatterns(weekEnd, startingDay, endingDay);
		//System.out.println("after"+pList);
		PhLArray = getListOfDistinctPhaseLabor(pList);
		//System.out.println("line 303");
		Float bigSum = (float) 0;
	    Row WE = sheet1.createRow(startingRow+1);//TODO:Changed by fmacicasan 12:56 30.09.2011
		Cell cWELabel = WE.createCell(9);
		cWELabel.setCellValue("W/E:");
		cWELabel.setCellStyle(style);
		Cell WEValue = WE.createCell(10);
		WEValue.setCellStyle(datestyle);
		//System.out.println("line 310");
		Calendar cloneCurrent = (Calendar) weekEnd.clone();
		cloneCurrent.add(Calendar.DAY_OF_MONTH, -6);
		Integer daysInMonth = 0;
		//start part that decide what to do with weekEnd
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
		//end part ...
		startingRow += 2;
		//System.out.println("line 329");
		//generate rows
		//System.out.println("line 331 generate rows");
	    for(int i = 0; i <= PhLArray.size(); i++) {
	    	if (i == 0) {
    			generateHeader(startingRow);
	    	}
	    	else {
	    		bigSum += generateTableRow(startingRow, i, startingDay, endingDay, weekEnd);    		
	    	}	   
	    }
	    generateTableFooter(startingRow, startingDay, endingDay ,weekEnd, bigSum);
    	//System.out.println("line 340");
	}
	private int howManyDays(Calendar t) {
			int K = t.getActualMaximum(Calendar.DAY_OF_MONTH) - t.get(Calendar.DAY_OF_MONTH);
			return Math.min(K + 1, 5);
	}
	
	/**
	 * Create style for every1
	 */
	private void styleMe() {
		style = wb.createCellStyle();
	    style.setAlignment(CellStyle.ALIGN_CENTER);
	    createHelper = wb.getCreationHelper();
	    Font f1 = wb.createFont();
	    f1.setUnderline((byte) 12);
	    
	    underlinedStyle = wb.createCellStyle();
	    underlinedStyle.setFont(f1);
	    
	    datestyle = wb.createCellStyle();
	    datestyle.setAlignment(CellStyle.ALIGN_CENTER);
	    datestyle.setDataFormat(createHelper.createDataFormat().getFormat("mm/dd/yy"));
	    datestyle.setFont(f1);
	    
	    tablestyle = wb.createCellStyle();
	    tablestyle.setAlignment(CellStyle.ALIGN_CENTER);
	    tablestyle.setBorderBottom((short)1);
	    tablestyle.setBorderLeft((short)1);
	    tablestyle.setBorderRight((short)1);
	    tablestyle.setBorderTop((short)1);
	    
	    tableheadstyle = wb.createCellStyle();
	    tableheadstyle.setAlignment(CellStyle.ALIGN_CENTER);
	    tableheadstyle.setBorderBottom((short)1);
	    tableheadstyle.setBorderLeft((short)1);
	    tableheadstyle.setBorderRight((short)1);
	    tableheadstyle.setBorderTop((short)1);	 
	    tableheadstyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	    tableheadstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    
	    tableweekendstyle = wb.createCellStyle();
	    tableweekendstyle.setAlignment(CellStyle.ALIGN_CENTER);
	    tableweekendstyle.setBorderBottom((short)1);
	    tableweekendstyle.setBorderLeft((short)1);
	    tableweekendstyle.setBorderRight((short)1);
	    tableweekendstyle.setBorderTop((short)1);	 
	    tableweekendstyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
	    tableweekendstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
	    
	}
	
	/**
	 * Heder creation
	 */
	private void generateDocHeader()  {
		
		CellRangeAddress region = CellRangeAddress.valueOf("A1:D1");
		CellRangeAddress regionComp = CellRangeAddress.valueOf("G1:J1");
		CellRangeAddress regionPosLabel = CellRangeAddress.valueOf("G2:J2");
		CellRangeAddress regionDepLabel = CellRangeAddress.valueOf("G3:J3");
		CellRangeAddress regionEmp = CellRangeAddress.valueOf("K1:M1");
		CellRangeAddress regionPosition = CellRangeAddress.valueOf("K2:M2");
		CellRangeAddress regionDepartment = CellRangeAddress.valueOf("K3:M3");
		
		sheet1.addMergedRegion( region );
		sheet1.addMergedRegion(regionComp);
		sheet1.addMergedRegion(regionPosLabel);
		sheet1.addMergedRegion(regionDepLabel);
		sheet1.addMergedRegion(regionEmp);
	    sheet1.addMergedRegion(regionPosition);
	    sheet1.addMergedRegion(regionDepartment);
	    
	    sheet1.setDisplayGridlines(false);
	    
	    
	    Row row = sheet1.createRow((short)0);	
	    
	    Cell cellCompany = row.createCell(0);
	    cellCompany.setCellValue(DEFAULT_TIMESHEET_HEADER_LABEL_COMPANY);
	    
	    Cell cellNameLabel = row.createCell(6);
	    cellNameLabel.setCellValue(DEFAULT_TIMESHEET_HEADER_LABEL_EMPLOYEE);
	    
	    Cell cellName = row.createCell(10);
	    cellName.setCellValue(pEmp.getRegularUser().getFullName());
	    
	    
	    row = sheet1.createRow((short)1);
	    Cell cellPositionLabel = row.createCell(6);
	    cellPositionLabel.setCellValue(DEFAULT_TIMESHEET_HEADER_LABEL_POSITION);
	    
	    Cell cellPosition = row.createCell(10);
	    cellPosition.setCellValue(pEmp.getJobrole().toString());
	    
	    row = sheet1.createRow(2);
	    Cell cellDepartmentLabel = row.createCell(6);
	    cellDepartmentLabel.setCellValue(DEFAULT_TIMESHEET_HEADER_LABEL_DEPARTMENT);
	    
	    Cell cellDepartment = row.createCell(10);
	    cellDepartment.setCellValue(pEmp.getDepartment().toString());
	    
	}
	
	/**
	 * transforms default schedules based on the vacation requests (DB)
	 */
	private void unionScheduleVacation() {	
		List<Schedule> lsnew = new ArrayList<Schedule>();		
		List<FreeDayAbstraction> lFreeDays = FreeDayAbstraction.getAllGrantedFreeDayAbstractions(pEmp.getRegularUser().getUsername());
		for(int i = 0; i < lFreeDays.size(); i++) {
			Schedule freeSched = lFreeDays.get(i).getSchedule();
			Calendar start =  lFreeDays.get(i).getStart();
			Calendar end =  lFreeDays.get(i).getEnd();
			lsnew.clear();
			lsnew.add(freeSched);
			for(int j = 0; j < schedEmp.size(); j++)  {
				/*
				 *		schedEmpSegment                      |-------------------|
				 *		overriding Segment             |-------------------------------| 
				 */
				if (schedEmp.get(j).getStartDate().after(start) && schedEmp.get(j).getEndDate().before(end)) {					
					continue;
				}
				else
					/*
					 *		schedEmpSegment                          |-------------------|
					 *		overriding Segment               |-----------------| 
					 */
				if (((schedEmp.get(j).getStartDate().after(start) || (schedEmp.get(j).getStartDate().equals(start))) && (end.after(schedEmp.get(j).getStartDate())))) {
					Schedule p = new Schedule();
					p.setEndDate(schedEmp.get(j).getEndDate());
					Calendar px = (Calendar) end.clone();
					px.add(Calendar.DAY_OF_MONTH, 1);
					p.setStartDate(px);
					p.setEmployee(schedEmp.get(j).getEmployee());
					p.setPattern(schedEmp.get(j).getPattern());
					lsnew.add(p);					
				}
				else 
					/*
					 *		schedEmpSegment                       |-------------------|
					 *		overriding Segment                           |-------| 
					 */
				 if (start.after(schedEmp.get(j).getStartDate()) && (end.before(schedEmp.get(j).getEndDate()))) { 
					  Calendar clona = (Calendar) end.clone();
					  clona.add(Calendar.DAY_OF_MONTH, 1);
					  Schedule caz3 = new Schedule();
					  caz3.setStartDate(clona);
					  caz3.setEndDate(schedEmp.get(j).getEndDate());
					  caz3.setPattern(schedEmp.get(j).getPattern());
					  caz3.setEmployee(schedEmp.get(j).getEmployee());
					  lsnew.add(caz3);
					  Calendar clone = (Calendar) start.clone();
					  clone.add(Calendar.DAY_OF_MONTH, -1);
					  caz3 = new Schedule();
					  caz3.setEndDate(clone);
					  caz3.setStartDate(schedEmp.get(j).getStartDate());
					  caz3.setPattern(schedEmp.get(j).getPattern());
					  caz3.setEmployee(schedEmp.get(j).getEmployee());
					  lsnew.add(caz3);
				 }
				  else
					     /*
						 *		schedEmpSegment                       |-------------------|
						 *		overriding Segment                                 |--------------| 
						 */ 
					if ((start.before(schedEmp.get(j).getEndDate()) && (end.after(schedEmp.get(j).getEndDate()))) || end.equals(schedEmp.get(j).getEndDate()))  {
						  Calendar clona = (Calendar) start.clone();
						  clona.add(Calendar.DAY_OF_MONTH, -1);
						  Schedule caz4 = new Schedule();
						  caz4.setEndDate(clona);
						  caz4.setStartDate(schedEmp.get(j).getStartDate());
						  caz4.setPattern(schedEmp.get(j).getPattern());
						  caz4.setEmployee(schedEmp.get(j).getEmployee());
						  lsnew.add(caz4);
				  }
					else {
						/*
						 *		schedEmpSegment                       |-------------------|
						 *		overriding Segment                                             |--------------| 
						 */
						 lsnew.add(schedEmp.get(j));
					}
						
			}
				schedEmp.clear();
				for(int it = 0; it < lsnew.size(); it++)
					schedEmp.add(lsnew.get(it));
		}
	}

	public void generateDocFooter(int genMonth, int genYear) {
		String blankSpaces = "                                                     ";
		//String blankSpacesSmall = "              ";
		CellRangeAddress regionFooterSignature = new CellRangeAddress(noOfRows, noOfRows, 0, 2);
		CellRangeAddress regionFooterApprovedBy = new CellRangeAddress(noOfRows + 1, noOfRows + 1, 0, 2	);
		CellRangeAddress regionFooterSignatureEmp = new CellRangeAddress(noOfRows, noOfRows, 3, 4);
		CellRangeAddress regionFooterSignatureAppBy = new CellRangeAddress(noOfRows + 1, noOfRows + 1, 3, 4);
		sheet1.addMergedRegion(regionFooterSignature);
		sheet1.addMergedRegion(regionFooterApprovedBy);
		sheet1.addMergedRegion(regionFooterSignatureEmp);
		sheet1.addMergedRegion(regionFooterSignatureAppBy);
		Row empSign = sheet1.createRow(noOfRows);
		Row appBy = sheet1.createRow(noOfRows + 1);
		
		Cell labelSignature = empSign.createCell(0);
		labelSignature.setCellValue("Employee signature:");
		
		Cell signature1 = empSign.createCell(3);
		signature1.setCellValue(blankSpaces);
		signature1.setCellStyle(underlinedStyle);
		
		
		Cell dateCell = empSign.createCell(9);
		dateCell.setCellValue("Date:");
		
		Cell dateCellV = empSign.createCell(10);
		dateCellV.setCellStyle(datestyle);
		Calendar x = Calendar.getInstance();
		x.set(Calendar.YEAR, genYear);
		x.set(Calendar.MONTH, genMonth);
		x.set(Calendar.DAY_OF_MONTH, x.getActualMaximum(Calendar.DAY_OF_MONTH));
		dateCellV.setCellValue(x);
		
		
		Cell labelApprovedBy = appBy.createCell(0);
		labelApprovedBy.setCellValue("Approved by:");
		
		Cell signatureApproved1 = appBy.createCell(3);
		signatureApproved1.setCellStyle(underlinedStyle);	
		signatureApproved1.setCellValue(blankSpaces);
		
		Cell dateAppCell = appBy.createCell(9);
		dateAppCell.setCellValue("Date:");
		Cell dateCellApprov = appBy.createCell(10);
		dateCellApprov.setCellStyle(datestyle);
		dateCellApprov.setCellValue(x);
		
		
		
		
		
	}
	public Calendar handleFirstWeeksOfMonth(int year, int month) {
		Calendar TimeSheetCalendar = new GregorianCalendar(year, month, 1);
	    Integer dayOfWeek = TimeSheetCalendar.get(Calendar.DAY_OF_WEEK);
	    
	    if ((dayOfWeek > 1) && (dayOfWeek < 7)) {
	    	Calendar auxWE = (Calendar) TimeSheetCalendar.clone();
	    	auxWE.add(Calendar.DAY_OF_MONTH, 8 - dayOfWeek);
	    	generateWeek(noOfRows, dayOfWeek - 1, 5, auxWE);
	    	noOfRows += PhLArray.size() + WeekConstants.ROWS_BETWEEN_TABLES;
	    	TimeSheetCalendar = getNextMonday(TimeSheetCalendar);
	    	auxWE = (Calendar) TimeSheetCalendar.clone();
	    	auxWE.add(Calendar.DAY_OF_MONTH, 6);
	    	generateWeek(noOfRows, 1, 5, auxWE);
	    	noOfRows += PhLArray.size() + WeekConstants.ROWS_BETWEEN_TABLES;
	    }
	    else {
	    	TimeSheetCalendar = getNextMonday(TimeSheetCalendar);
	    	Calendar auxWE = (Calendar) TimeSheetCalendar.clone();
	    	auxWE.add(Calendar.DAY_OF_MONTH, 6);
	    	generateWeek(noOfRows, 1, 5,auxWE);
	    	noOfRows += PhLArray.size() + WeekConstants.ROWS_BETWEEN_TABLES;
	    }
	    return TimeSheetCalendar;
	}
	/**
	 * where the magic happens
	 */
	public File generateDoc(String workbookname, int genmonth, int genyear) {
		unionScheduleVacation();
		wb = new HSSFWorkbook();
		
		styleMe();	
	    fileOut = null;
		try {
			fileOut = new FileOutputStream(new File(workbookname));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    String safeName = WorkbookUtil.createSafeSheetName("TimeSheet");
	    //CreationHelper createHelper = wb.getCreationHelper();
	    sheet1 = wb.createSheet(safeName);
	    sheet1.getPrintSetup().setLandscape(true);//TODO:Changed by fmacicasan 12:56 30.09.2011
		generateDocHeader();
	    noOfRows = 5;
	    Calendar TimeSheetCalendar = handleFirstWeeksOfMonth(genyear,genmonth);
	    Integer noOfDays = TimeSheetCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	    while(TimeSheetCalendar.get(Calendar.DAY_OF_MONTH) + 7 <= noOfDays) { 
	    	TimeSheetCalendar.add(Calendar.DAY_OF_MONTH, 7);
	    	Calendar auxWE = (Calendar) TimeSheetCalendar.clone();
	    	/*
	    	 *  Because TimesheetCalendar is always set on monday I add 6 DAY_OF_MONTH to get the next sunday
	    	 */
	    	auxWE.add(Calendar.DAY_OF_MONTH, 6);
	    	generateWeek(noOfRows, 1, howManyDays(TimeSheetCalendar),auxWE);
	    	noOfRows += PhLArray.size() + WeekConstants.ROWS_BETWEEN_TABLES;
	    }
	    noOfRows += 2;
	    generateDocFooter(genmonth, genyear);
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
	    File f =  new File(workbookname);
//	    if(f!=null){
//	    	//System.out.println("absolute"+f.getAbsolutePath());
//	    	//System.out.println("cucurigu"+f.isFile());
//	    	//System.out.println("terzz"+f.getName());
//	    } else {
//	    	//System.out.println("=============is nuklllllllllllllllllllll");
//	    }
	    return f;
	    
	}
}
