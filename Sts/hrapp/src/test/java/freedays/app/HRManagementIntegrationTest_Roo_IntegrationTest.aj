// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import freedays.app.HRManagementDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect HRManagementIntegrationTest_Roo_IntegrationTest {
    
    declare @type: HRManagementIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: HRManagementIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: HRManagementIntegrationTest: @Transactional;
    
    @Autowired
    private HRManagementDataOnDemand HRManagementIntegrationTest.dod;
    
    @Test
    public void HRManagementIntegrationTest.testCountHRManagements() {
        org.junit.Assert.assertNotNull("Data on demand for 'HRManagement' failed to initialize correctly", dod.getRandomHRManagement());
        long count = freedays.app.HRManagement.countHRManagements();
        org.junit.Assert.assertTrue("Counter for 'HRManagement' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void HRManagementIntegrationTest.testFindHRManagement() {
        freedays.app.HRManagement obj = dod.getRandomHRManagement();
        org.junit.Assert.assertNotNull("Data on demand for 'HRManagement' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'HRManagement' failed to provide an identifier", id);
        obj = freedays.app.HRManagement.findHRManagement(id);
        org.junit.Assert.assertNotNull("Find method for 'HRManagement' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'HRManagement' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void HRManagementIntegrationTest.testFindAllHRManagements() {
        org.junit.Assert.assertNotNull("Data on demand for 'HRManagement' failed to initialize correctly", dod.getRandomHRManagement());
        long count = freedays.app.HRManagement.countHRManagements();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'HRManagement', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<freedays.app.HRManagement> result = freedays.app.HRManagement.findAllHRManagements();
        org.junit.Assert.assertNotNull("Find all method for 'HRManagement' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'HRManagement' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void HRManagementIntegrationTest.testFindHRManagementEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'HRManagement' failed to initialize correctly", dod.getRandomHRManagement());
        long count = freedays.app.HRManagement.countHRManagements();
        if (count > 20) count = 20;
        java.util.List<freedays.app.HRManagement> result = freedays.app.HRManagement.findHRManagementEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'HRManagement' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'HRManagement' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void HRManagementIntegrationTest.testFlush() {
        freedays.app.HRManagement obj = dod.getRandomHRManagement();
        org.junit.Assert.assertNotNull("Data on demand for 'HRManagement' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'HRManagement' failed to provide an identifier", id);
        obj = freedays.app.HRManagement.findHRManagement(id);
        org.junit.Assert.assertNotNull("Find method for 'HRManagement' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyHRManagement(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'HRManagement' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void HRManagementIntegrationTest.testMerge() {
        freedays.app.HRManagement obj = dod.getRandomHRManagement();
        org.junit.Assert.assertNotNull("Data on demand for 'HRManagement' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'HRManagement' failed to provide an identifier", id);
        obj = freedays.app.HRManagement.findHRManagement(id);
        boolean modified =  dod.modifyHRManagement(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        freedays.app.HRManagement merged = (freedays.app.HRManagement) obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'HRManagement' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void HRManagementIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'HRManagement' failed to initialize correctly", dod.getRandomHRManagement());
        freedays.app.HRManagement obj = dod.getNewTransientHRManagement(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'HRManagement' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'HRManagement' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'HRManagement' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void HRManagementIntegrationTest.testRemove() {
        freedays.app.HRManagement obj = dod.getRandomHRManagement();
        org.junit.Assert.assertNotNull("Data on demand for 'HRManagement' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'HRManagement' failed to provide an identifier", id);
        obj = freedays.app.HRManagement.findHRManagement(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'HRManagement' with identifier '" + id + "'", freedays.app.HRManagement.findHRManagement(id));
    }
    
}
