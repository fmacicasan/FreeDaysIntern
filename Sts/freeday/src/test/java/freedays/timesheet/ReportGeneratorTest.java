package freedays.timesheet;

import java.io.File;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
public class ReportGeneratorTest {

    @Autowired
    private ReportGenerator reportGenerator;
    
//    @Test
    public void testCreation() {
        File file = reportGenerator.generateDoc("D:\\Freedays\\report.xls", 10, 2012);
        
        Assert.assertTrue(file.exists());
    }
    
    
}
