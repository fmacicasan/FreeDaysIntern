package freedays.timesheet;

import java.io.File;
import java.util.Calendar;
import java.util.Random;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import freedays.app.FDUser;
import freedays.app.RequestStatus;
import freedays.domain.RegularUser;
import freedays.domain.Request;
import freedays.timesheet.TimesheetUser.Department;
import freedays.util.PropertiesUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
public class ReportGeneratorTest {

    @Autowired
    private ReportGenerator reportGenerator;
    
//    @Test
    public void testCreation() {
        File file = reportGenerator.generateDoc(PropertiesUtil.getProperty(PropertiesUtil.TIMESHEET_LOCATION_ROOT)+"/10_2012_Orig_Production.xls", 12, 2012);
        
        Assert.assertTrue(file.exists());
    }
    
//    @Test
    public void main(){
        for(int i=0;i<53;i++){
            System.out.println(ReportGenerator.convTo24(i));
        }
//        System.out.println(ReportGenerator.convTo24(0));
//        System.out.println(ReportGenerator.convTo24(1));
//        System.out.println(ReportGenerator.convTo24(23));
//        System.out.println(ReportGenerator.convTo24(24));
//        System.out.println(ReportGenerator.convTo24(26));
//        System.out.println(ReportGenerator.convTo24(27));
//        System.out.println(ReportGenerator.convTo24(46));
//        System.out.println(ReportGenerator.convTo24(48));
//        System.out.println(ReportGenerator.convTo24(49));
//        System.out.println(ReportGenerator.convTo24(47));
//        System.out.println(ReportGenerator.convTo24(52));
    }
    
//    @Test
//    @Transactional
//    @Rollback(false)
    public void populateTimesheetUsers(){
        Random rand = new Random();
        for(TimesheetUser timesheetUser : TimesheetUser.findAllTimesheetUsers()){
            
            int random = rand.nextInt() %2;
            if(random == 1){
                timesheetUser.setDepartment(Department.ADMINISTRATIV);
            } else {
                timesheetUser.setDepartment(Department.SOFTWARE);
            }
            random = rand.nextInt() %2;
            if(random == 1){
                timesheetUser.setTeampay(1);
            } else {
                timesheetUser.setTeampay(2);
            }
            
            timesheetUser.persist();
            
        }
    }
    
//    @Test
    public void reportRemainingDays2012(){
        System.out.println("||Name||Remaining Days||");
        for(FDUser fduser: FDUser.findAllReportableFDUsers()){
            System.out.println("||"+fduser.getRegularUser().getFullName()+"||"+fduser.computeteAvailableFreeDaysTotal()+"||");
        }
    }
    
//    @Test
    public void reportShit(){
        for( FDUser fduser : FDUser.findAllFDUsers()){
            System.out.println(fduser.getRegularUser().getReportName());
            for( Request request : Request.findAllRequestsByUsername(fduser.getRegularUser().getUsername())){
                if(RequestStatus.GRANTED.equals(request.getStatus()) && ReportLegend.LEGAL.getTerm().equals(request.getRequestable().getReportLegendCode())){
                    if(request.getRequestable().getDate().get(Calendar.YEAR) == 2012 || request.getRequestable().getEnd().get(Calendar.YEAR) == 2012){
                        System.out.println("\t"+request.getRequestable().getDateReport() + "\t" + request.getRequestable().getReportLegendCode()); 
                    }
                    
                }
            }
        }
        
    }
    
    
    
    
}
