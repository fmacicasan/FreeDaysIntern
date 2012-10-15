package freedays.timesheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    
    public Sheet generateSheet(Workbook wb, String string) {
        // TODO Auto-generated method stub
        String safeName = WorkbookUtil.createSafeSheetName("TimeSheet");
        // CreationHelper createHelper = wb.getCreationHelper();
        Sheet sheet1 = wb.createSheet(safeName);
        sheet1.getPrintSetup().setLandscape(true);    
        return sheet1;
    }

    public void writeReport(Workbook wb, FileOutputStream fileOut) {
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

    public FileOutputStream createOutputStream(String workbookpath) {
        FileOutputStream fileOut = null;
        try {

            
            File f = new File(workbookpath);
            new File(f.getParent()).mkdirs();
            if (!f.exists()) {
                f.createNewFile();
            }

            fileOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileOut;
    }

}
