package freedays.timesheet;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

@RooIntegrationTest(entity = TimesheetUser.class)
public class TimesheetUserIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }
    
    @Autowired
    private TimesheetUserDataOnDemand dod;

	@Test
    public void testRemove() {
        freedays.timesheet.TimesheetUser obj = dod.getRandomTimesheetUser();
        org.junit.Assert.assertNotNull("Data on demand for 'TimesheetUser' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'TimesheetUser' failed to provide an identifier", id);
        //obj = freedays.timesheet.TimesheetUser.findTimesheetUser(id);
        //obj.remove();
        //obj.flush();
        //org.junit.Assert.assertNull("Failed to remove 'TimesheetUser' with identifier '" + id + "'", freedays.timesheet.TimesheetUser.findTimesheetUser(id));
    }
}
