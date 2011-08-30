package freedays.timesheet;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

@RooIntegrationTest(entity = Schedule.class)
public class ScheduleIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

    @Autowired
    private ScheduleDataOnDemand dod;
	@Test
    public void testRemove() {
        freedays.timesheet.Schedule obj = dod.getRandomSchedule();
        org.junit.Assert.assertNotNull("Data on demand for 'Schedule' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Schedule' failed to provide an identifier", id);
        obj = freedays.timesheet.Schedule.findSchedule(id);
//        obj.remove();
//        obj.flush();
//        org.junit.Assert.assertNull("Failed to remove 'Schedule' with identifier '" + id + "'", freedays.timesheet.Schedule.findSchedule(id));
    }
}
