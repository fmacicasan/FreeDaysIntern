// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import freedays.app.FreeDayRDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect FreeDayRIntegrationTest_Roo_IntegrationTest {
    
    declare @type: FreeDayRIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: FreeDayRIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: FreeDayRIntegrationTest: @Transactional;
    
    @Autowired
    private FreeDayRDataOnDemand FreeDayRIntegrationTest.dod;
    
    @Test
    public void FreeDayRIntegrationTest.testCountFreeDayRs() {
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayR' failed to initialize correctly", dod.getRandomFreeDayR());
        long count = freedays.app.FreeDayR.countFreeDayRs();
        org.junit.Assert.assertTrue("Counter for 'FreeDayR' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void FreeDayRIntegrationTest.testFindFreeDayR() {
        freedays.app.FreeDayR obj = dod.getRandomFreeDayR();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayR' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayR' failed to provide an identifier", id);
        obj = freedays.app.FreeDayR.findFreeDayR(id);
        org.junit.Assert.assertNotNull("Find method for 'FreeDayR' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'FreeDayR' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void FreeDayRIntegrationTest.testFindAllFreeDayRs() {
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayR' failed to initialize correctly", dod.getRandomFreeDayR());
        long count = freedays.app.FreeDayR.countFreeDayRs();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'FreeDayR', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<freedays.app.FreeDayR> result = freedays.app.FreeDayR.findAllFreeDayRs();
        org.junit.Assert.assertNotNull("Find all method for 'FreeDayR' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'FreeDayR' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void FreeDayRIntegrationTest.testFindFreeDayREntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayR' failed to initialize correctly", dod.getRandomFreeDayR());
        long count = freedays.app.FreeDayR.countFreeDayRs();
        if (count > 20) count = 20;
        java.util.List<freedays.app.FreeDayR> result = freedays.app.FreeDayR.findFreeDayREntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'FreeDayR' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'FreeDayR' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void FreeDayRIntegrationTest.testFlush() {
        freedays.app.FreeDayR obj = dod.getRandomFreeDayR();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayR' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayR' failed to provide an identifier", id);
        obj = freedays.app.FreeDayR.findFreeDayR(id);
        org.junit.Assert.assertNotNull("Find method for 'FreeDayR' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyFreeDayR(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'FreeDayR' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void FreeDayRIntegrationTest.testMerge() {
        freedays.app.FreeDayR obj = dod.getRandomFreeDayR();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayR' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayR' failed to provide an identifier", id);
        obj = freedays.app.FreeDayR.findFreeDayR(id);
        boolean modified =  dod.modifyFreeDayR(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        freedays.app.FreeDayR merged = (freedays.app.FreeDayR) obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'FreeDayR' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void FreeDayRIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayR' failed to initialize correctly", dod.getRandomFreeDayR());
        freedays.app.FreeDayR obj = dod.getNewTransientFreeDayR(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayR' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'FreeDayR' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'FreeDayR' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void FreeDayRIntegrationTest.testRemove() {
        freedays.app.FreeDayR obj = dod.getRandomFreeDayR();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayR' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayR' failed to provide an identifier", id);
        obj = freedays.app.FreeDayR.findFreeDayR(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'FreeDayR' with identifier '" + id + "'", freedays.app.FreeDayR.findFreeDayR(id));
    }
    
}
