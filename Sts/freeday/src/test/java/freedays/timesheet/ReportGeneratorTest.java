package freedays.timesheet;

import java.io.File;
import java.util.Random;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import freedays.timesheet.TimesheetUser.Department;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
public class ReportGeneratorTest {

    @Autowired
    private ReportGenerator reportGenerator;
    
    @Test
    public void testCreation() {
        File file = reportGenerator.generateDoc("D:\\Freedays\\report.xls", 10, 2012);
        
        Assert.assertTrue(file.exists());
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
    
    
    
    
}
