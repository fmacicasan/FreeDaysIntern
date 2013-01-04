package freedays.timesheet;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import freedays.timesheet.TimesheetUser.Department;
import freedays.util.DateUtils;
import freedays.util.PropertiesUtil;

//TODO: create structure for entities within report: START<COLUMN, ROW>, END, CONTENT and populate report with that 
@Service
public class ReportGenerator implements TimesheetGenerator {
    
    private static final int TABLE_SPACE_ALLIGNMENT = 3;
    private static final int UNIT_COLUMN = 0;
    private static final int TITLE_COLUMN = 8;
    private static final int LEGEND_COLUMN = 19;
    private static final int REPORT_COLUMN_SIZE = 50;
    @Autowired
    private ReportService reportService;
    
    public File generateDoc(String workbookpath, int genmonth, int genyear){
        Workbook wb = new HSSFWorkbook();
        
        FileOutputStream fileOut = reportService.createOutputStream(workbookpath);
        
        Sheet sheet = reportService.generateSheet(wb, "cucurigu");
        
        int row = generateDocHeader(sheet, genmonth);
        
        generateDocContent(sheet, row, genmonth);
        
        
        
        
        
        for(int i=0;i<REPORT_COLUMN_SIZE;i++){
            sheet.autoSizeColumn(i, true); 
        }
        reportService.writeReport(wb, fileOut);
        File f = new File(workbookpath);
        
        return f;
        
    }

    private void generateDocContent(Sheet sheet, int rowcnt, int month) {
        // iterate over departments
        int nrcrt=0;
     // iterate over teampays
        // iterate over each timesheet user
        // obtain array with status for each day
        for (Department department : Department.values()) {
            List<TimesheetUser> timesheetUsers = TimesheetUser.findAllTimesheetUsersByDepartment(department);
//            if (timesheetUsers.size() > 0) {
                int currentteampay = -1;
                for(TimesheetUser timesheetUser : timesheetUsers) {
                    if(timesheetUser.getTeampay() != currentteampay){
                        Row row = sheet.createRow((short) ++rowcnt);
                        currentteampay = timesheetUser.getTeampay();
                        populateCell(sheet, row,constructCellRange("A","AX",rowcnt),0,department+" "+currentteampay);
                    }
                    String timesheetUserUsername = timesheetUser.getFduser().getRegularUser().getUsername();
                    Integer noOfHours = timesheetUser.getScheduleLst().get(0).getPattern().getNoOfHours();
                    
                    List<FreeDayAbstraction> allGrantedFreeDays = FreeDayAbstraction.getAllGrantedFreeDayAbstractionsFilteredByMonth(month, PropertiesUtil.getInteger(PropertiesUtil.CURRENT_YEAR),timesheetUserUsername);
                    System.out.println("Timesheet user"+timesheetUserUsername+noOfHours);
                    
                    Row row = sheet.createRow((short) ++rowcnt);
                    populateCell(sheet, row,"A1:A1",0, ++nrcrt+"");
                    populateCell(sheet, row,constructCellRange("B","D", rowcnt),1,timesheetUser.getRegularUser().getFullName());
                    
                    int offset = 4;
                    int daysInMonthPlusOffset = DateUtils.getDaysInMonth112(month)+offset;
                    Map<Integer,Cell> dayToCell = new HashMap<Integer, Cell>();
                    for(int day = offset;day < daysInMonthPlusOffset; day++){
                        Cell cellDayOfMonth = row.createCell(day);
                        cellDayOfMonth.setCellValue(noOfHours);
                        //start with 1
                        dayToCell.put(day-offset+1, cellDayOfMonth);
                    }
                    
                    for(FreeDayAbstraction abstraction : allGrantedFreeDays){
                        for(Calendar c = (Calendar)abstraction.getStart().clone(); c.compareTo(abstraction.getEnd()) <= 0;c.add(Calendar.DAY_OF_YEAR, 1)){
                            dayToCell.get(c.get(Calendar.DAY_OF_MONTH)).setCellValue(abstraction.getLegendCode());
                        }
                    }
//                }
                
            }
        }
        // do ordering stuff 
                
    }



    @Override
    public int generateDocHeader(Sheet sheet1, int month) {
        
        Row row = sheet1.createRow((short) 0);

        CellRangeAddress regionUnit = CellRangeAddress.valueOf("A1:D1");
        sheet1.addMergedRegion(regionUnit);
        Cell cellCompany = row.createCell(UNIT_COLUMN);
        cellCompany.setCellValue("Unitate SC Language Weaver");

        CellRangeAddress regionName = CellRangeAddress.valueOf("I1:P1");
        sheet1.addMergedRegion(regionName);
        Cell cellNameLabel = row.createCell(TITLE_COLUMN);
        cellNameLabel.setCellValue("FOAIE COLECTIVA DE PREZENTA");

        populateCell(sheet1, row,"T1:X1",LEGEND_COLUMN,"LEGENDA");
        
        Map<ReportLegend, CellRangeAddress> reportLocations = new HashMap<ReportLegend, CellRangeAddress>();
        ReportLegend[] reportValues = ReportLegend.values();
        for(int i = 0;i<reportValues.length;i++){
            int legendRow = i+1;
            CellRangeAddress reportRange = CellRangeAddress.valueOf(constructCellRange("T","X", legendRow));
            
            reportLocations.put(reportValues[i], reportRange);
            sheet1.addMergedRegion(reportRange);
        }
        sheet1.setDisplayGridlines(false);

        int i;
        for(i = 0;i<reportValues.length;i++){
            row = sheet1.createRow((short) (i+1));
            if(i==0){
                CellRangeAddress regionComp = CellRangeAddress.valueOf("A2:D2");
                sheet1.addMergedRegion(regionComp);
                Cell cellPositionLabel = row.createCell(0);
                cellPositionLabel.setCellValue("Compartimentul Cluj Napoca"); 
            }
            Cell cellPosition = row.createCell(LEGEND_COLUMN);
            cellPosition.setCellValue(reportValues[i].getReportRepresentaiton());
        }
        
        i += TABLE_SPACE_ALLIGNMENT;
        
        row = sheet1.createRow(i);
        int j = 0;
//        Cell cellNrCrt = row.createCell(j);
//        cellNrCrt.setCellValue("Nr. crt.");
        populateCell(sheet1, row,"A1:A1",j, "Nr. crt.");
        
        j++;
//        CellRangeAddress regionNameHuman = CellRangeAddress.valueOf(constructCellRange("B","D", i));
//        sheet1.addMergedRegion(regionNameHuman);
//        Cell cellNameHuman = row.createCell(j);
//        cellNameHuman.setCellValue("Numele si prenumele");
        populateCell(sheet1, row,constructCellRange("B","D", i),j,"Numele si prenumele");
        
        int daysInMonth = DateUtils.getDaysInMonth112(month);
        
        j++;
        j++;
        for(int monthDay=1;monthDay<=daysInMonth;monthDay++){
            Cell cellDayOfMonth = row.createCell(j+monthDay);
            cellDayOfMonth.setCellValue(monthDay);
        }
        j+= daysInMonth;
        
        j++;
        Cell cellTotalWorked = row.createCell(j);
        cellTotalWorked.setCellValue("Total ore lucrate");
        
        j++;
        Cell cellNightHours = row.createCell(j);
        cellNightHours.setCellValue("Ore de noapte");
        
        j++;
        Cell cellStopHours = row.createCell(j);
        cellStopHours.setCellValue("Orele de intrerup.");
        
        for(int index = 0;index<reportValues.length;index++){
            j++;
            Cell cellLegendHead = row.createCell(j);
            cellLegendHead.setCellValue(reportValues[index].getTerm());
        }
        
        j++;
        Cell cellTotalNumber = row.createCell(j);
        cellTotalNumber.setCellValue("Nr total");
        
        j++;
        Cell cellDaysVacation = row.createCell(j);
        cellDaysVacation.setCellValue("Zile Co");
        
        j++;
        CellRangeAddress regionTicket = CellRangeAddress.valueOf(constructCellRange("AR","AR", i));
        sheet1.addMergedRegion(regionTicket);
        Cell cellTickets = row.createCell(j);
        cellTickets.setCellValue("Tichete");
        
        j++;
        CellRangeAddress regionCheck = CellRangeAddress.valueOf(constructCellRange("AS","AU", i));
        sheet1.addMergedRegion(regionCheck);
        Cell cellCheck = row.createCell(j);
        cellCheck.setCellValue("Verificare");
        
        return i;

        
    }

    private void populateCell(Sheet sheet1, Row row, String range, int column, String content) {
        CellRangeAddress regionLegendHead = CellRangeAddress.valueOf(range);
        sheet1.addMergedRegion(regionLegendHead);
        Cell cellName = row.createCell(column);
        cellName.setCellValue(content);
    }
    
    private String constructCellRange(String start, String end, int row){
        // rows start from 1
        row++;
        return start+row+":"+end+row;
    }

}
