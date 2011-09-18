// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.timesheet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect PhaseLaborIntegrationTest_Roo_IntegrationTest {
    
    declare @type: PhaseLaborIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: PhaseLaborIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: PhaseLaborIntegrationTest: @Transactional;
    
    @Test
    public void PhaseLaborIntegrationTest.testCountPhaseLabors() {
        org.junit.Assert.assertNotNull("Data on demand for 'PhaseLabor' failed to initialize correctly", dod.getRandomPhaseLabor());
        long count = freedays.timesheet.PhaseLabor.countPhaseLabors();
        org.junit.Assert.assertTrue("Counter for 'PhaseLabor' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void PhaseLaborIntegrationTest.testFindPhaseLabor() {
        freedays.timesheet.PhaseLabor obj = dod.getRandomPhaseLabor();
        org.junit.Assert.assertNotNull("Data on demand for 'PhaseLabor' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'PhaseLabor' failed to provide an identifier", id);
        obj = freedays.timesheet.PhaseLabor.findPhaseLabor(id);
        org.junit.Assert.assertNotNull("Find method for 'PhaseLabor' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'PhaseLabor' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void PhaseLaborIntegrationTest.testFindAllPhaseLabors() {
        org.junit.Assert.assertNotNull("Data on demand for 'PhaseLabor' failed to initialize correctly", dod.getRandomPhaseLabor());
        long count = freedays.timesheet.PhaseLabor.countPhaseLabors();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'PhaseLabor', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<freedays.timesheet.PhaseLabor> result = freedays.timesheet.PhaseLabor.findAllPhaseLabors();
        org.junit.Assert.assertNotNull("Find all method for 'PhaseLabor' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'PhaseLabor' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void PhaseLaborIntegrationTest.testFindPhaseLaborEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'PhaseLabor' failed to initialize correctly", dod.getRandomPhaseLabor());
        long count = freedays.timesheet.PhaseLabor.countPhaseLabors();
        if (count > 20) count = 20;
        java.util.List<freedays.timesheet.PhaseLabor> result = freedays.timesheet.PhaseLabor.findPhaseLaborEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'PhaseLabor' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'PhaseLabor' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void PhaseLaborIntegrationTest.testFlush() {
        freedays.timesheet.PhaseLabor obj = dod.getRandomPhaseLabor();
        org.junit.Assert.assertNotNull("Data on demand for 'PhaseLabor' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'PhaseLabor' failed to provide an identifier", id);
        obj = freedays.timesheet.PhaseLabor.findPhaseLabor(id);
        org.junit.Assert.assertNotNull("Find method for 'PhaseLabor' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyPhaseLabor(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'PhaseLabor' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void PhaseLaborIntegrationTest.testMerge() {
        freedays.timesheet.PhaseLabor obj = dod.getRandomPhaseLabor();
        org.junit.Assert.assertNotNull("Data on demand for 'PhaseLabor' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'PhaseLabor' failed to provide an identifier", id);
        obj = freedays.timesheet.PhaseLabor.findPhaseLabor(id);
        boolean modified =  dod.modifyPhaseLabor(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        freedays.timesheet.PhaseLabor merged =  obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'PhaseLabor' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void PhaseLaborIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'PhaseLabor' failed to initialize correctly", dod.getRandomPhaseLabor());
        freedays.timesheet.PhaseLabor obj = dod.getNewTransientPhaseLabor(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'PhaseLabor' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'PhaseLabor' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'PhaseLabor' identifier to no longer be null", obj.getId());
    }
    
}