// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.timesheet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ScheduleIntegrationTest_Roo_IntegrationTest {
    
    declare @type: ScheduleIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: ScheduleIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: ScheduleIntegrationTest: @Transactional;
    
    @Test
    public void ScheduleIntegrationTest.testCountSchedules() {
        org.junit.Assert.assertNotNull("Data on demand for 'Schedule' failed to initialize correctly", dod.getRandomSchedule());
        long count = freedays.timesheet.Schedule.countSchedules();
        org.junit.Assert.assertTrue("Counter for 'Schedule' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void ScheduleIntegrationTest.testFindSchedule() {
        freedays.timesheet.Schedule obj = dod.getRandomSchedule();
        org.junit.Assert.assertNotNull("Data on demand for 'Schedule' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Schedule' failed to provide an identifier", id);
        obj = freedays.timesheet.Schedule.findSchedule(id);
        org.junit.Assert.assertNotNull("Find method for 'Schedule' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'Schedule' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void ScheduleIntegrationTest.testFindAllSchedules() {
        org.junit.Assert.assertNotNull("Data on demand for 'Schedule' failed to initialize correctly", dod.getRandomSchedule());
        long count = freedays.timesheet.Schedule.countSchedules();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'Schedule', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<freedays.timesheet.Schedule> result = freedays.timesheet.Schedule.findAllSchedules();
        org.junit.Assert.assertNotNull("Find all method for 'Schedule' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'Schedule' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void ScheduleIntegrationTest.testFindScheduleEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'Schedule' failed to initialize correctly", dod.getRandomSchedule());
        long count = freedays.timesheet.Schedule.countSchedules();
        if (count > 20) count = 20;
        java.util.List<freedays.timesheet.Schedule> result = freedays.timesheet.Schedule.findScheduleEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'Schedule' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'Schedule' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void ScheduleIntegrationTest.testFlush() {
        freedays.timesheet.Schedule obj = dod.getRandomSchedule();
        org.junit.Assert.assertNotNull("Data on demand for 'Schedule' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Schedule' failed to provide an identifier", id);
        obj = freedays.timesheet.Schedule.findSchedule(id);
        org.junit.Assert.assertNotNull("Find method for 'Schedule' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifySchedule(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'Schedule' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void ScheduleIntegrationTest.testMerge() {
        freedays.timesheet.Schedule obj = dod.getRandomSchedule();
        org.junit.Assert.assertNotNull("Data on demand for 'Schedule' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'Schedule' failed to provide an identifier", id);
        obj = freedays.timesheet.Schedule.findSchedule(id);
        boolean modified =  dod.modifySchedule(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        freedays.timesheet.Schedule merged =  obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'Schedule' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void ScheduleIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'Schedule' failed to initialize correctly", dod.getRandomSchedule());
        freedays.timesheet.Schedule obj = dod.getNewTransientSchedule(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'Schedule' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'Schedule' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'Schedule' identifier to no longer be null", obj.getId());
    }
    
}
