package freedays.timesheet;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import freedays.util.DateUtils;

@Service
public class ReportGenerator implements TimesheetGenerator {
    
    private static final int TABLE_SPACE_ALLIGNMENT = 3;
    private static final int UNIT_COLUMN = 0;
    private static final int TITLE_COLUMN = 8;
    private static final int LEGEND_COLUMN = 17;
    @Autowired
    private ReportService reportService;
    
    public File generateDoc(String workbookpath, int genmonth, int genyear){
        Workbook wb = new HSSFWorkbook();
        
        FileOutputStream fileOut = reportService.createOutputStream(workbookpath);
        
        Sheet sheet = reportService.generateSheet(wb, "cucurigu");
        
        generateDocHeader(sheet, genmonth);
        
         
        reportService.writeReport(wb, fileOut);
        
        File f = new File(workbookpath);
        
        return f;
        
    }

    @Override
    public void generateDocHeader(Sheet sheet1, int month) {
        
        CellRangeAddress regionUnit = CellRangeAddress.valueOf("A1:D1");
        sheet1.addMergedRegion(regionUnit);
        CellRangeAddress regionComp = CellRangeAddress.valueOf("A2:D2");
        sheet1.addMergedRegion(regionComp);
        CellRangeAddress regionName = CellRangeAddress.valueOf("I1:P1");
        sheet1.addMergedRegion(regionName);
        CellRangeAddress regionLegendHead = CellRangeAddress.valueOf("T1:X1");
        sheet1.addMergedRegion(regionLegendHead);
        Map<ReportLegend, CellRangeAddress> reportLocations = new HashMap<ReportLegend, CellRangeAddress>();
        ReportLegend[] reportValues = ReportLegend.values();
        for(int i = 0;i<reportValues.length;i++){
            int legendRow = i+1;
            CellRangeAddress reportRange = CellRangeAddress.valueOf(constructCellRange("T","X", legendRow));
            
            reportLocations.put(reportValues[i], reportRange);
            sheet1.addMergedRegion(reportRange);
        }

        sheet1.setDisplayGridlines(false);

        Row row = sheet1.createRow((short) 0);

        Cell cellCompany = row.createCell(UNIT_COLUMN);
        cellCompany.setCellValue("Unitate SC Language Weaver");

        Cell cellNameLabel = row.createCell(TITLE_COLUMN);
        cellNameLabel.setCellValue("FOAIE COLECTIVA DE PREZENTA");

        Cell cellName = row.createCell(LEGEND_COLUMN);
        cellName.setCellValue("LEGENDA");

        int i;
        for(i = 0;i<reportValues.length;i++){
            row = sheet1.createRow((short) (i+1));
            if(i==0){
                Cell cellPositionLabel = row.createCell(0);
                cellPositionLabel.setCellValue("Compartimentul Cluj Napoca"); 
            }
            Cell cellPosition = row.createCell(LEGEND_COLUMN);
            cellPosition.setCellValue(reportValues[i].getReportRepresentaiton());
        }
        
        i+=TABLE_SPACE_ALLIGNMENT;
        
        row = sheet1.createRow(i);
        
        Cell cellNrCrt = row.createCell(1);
        cellNrCrt.setCellValue("Nr. crt.");
        
        CellRangeAddress regionNameHuman = CellRangeAddress.valueOf(constructCellRange("B","D", i));
        sheet1.addMergedRegion(regionNameHuman);
        Cell cellNameHuman = row.createCell(2);
        cellNameHuman.setCellValue("Numele si prenumele");
        
        int daysInMonth = DateUtils.getDaysInMonth(month);
        
        
    }
    
    private String constructCellRange(String start, String end, int row){
        return start+row+":"+end+row;
    }

}
