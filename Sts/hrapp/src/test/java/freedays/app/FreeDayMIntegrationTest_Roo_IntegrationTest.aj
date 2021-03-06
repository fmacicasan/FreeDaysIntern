// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import freedays.app.FreeDayMDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect FreeDayMIntegrationTest_Roo_IntegrationTest {
    
    declare @type: FreeDayMIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: FreeDayMIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: FreeDayMIntegrationTest: @Transactional;
    
    @Autowired
    private FreeDayMDataOnDemand FreeDayMIntegrationTest.dod;
    
    @Test
    public void FreeDayMIntegrationTest.testCountFreeDayMs() {
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayM' failed to initialize correctly", dod.getRandomFreeDayM());
        long count = freedays.app.FreeDayM.countFreeDayMs();
        org.junit.Assert.assertTrue("Counter for 'FreeDayM' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void FreeDayMIntegrationTest.testFindFreeDayM() {
        freedays.app.FreeDayM obj = dod.getRandomFreeDayM();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayM' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayM' failed to provide an identifier", id);
        obj = freedays.app.FreeDayM.findFreeDayM(id);
        org.junit.Assert.assertNotNull("Find method for 'FreeDayM' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'FreeDayM' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void FreeDayMIntegrationTest.testFindAllFreeDayMs() {
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayM' failed to initialize correctly", dod.getRandomFreeDayM());
        long count = freedays.app.FreeDayM.countFreeDayMs();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'FreeDayM', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<freedays.app.FreeDayM> result = freedays.app.FreeDayM.findAllFreeDayMs();
        org.junit.Assert.assertNotNull("Find all method for 'FreeDayM' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'FreeDayM' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void FreeDayMIntegrationTest.testFindFreeDayMEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayM' failed to initialize correctly", dod.getRandomFreeDayM());
        long count = freedays.app.FreeDayM.countFreeDayMs();
        if (count > 20) count = 20;
        java.util.List<freedays.app.FreeDayM> result = freedays.app.FreeDayM.findFreeDayMEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'FreeDayM' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'FreeDayM' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void FreeDayMIntegrationTest.testFlush() {
        freedays.app.FreeDayM obj = dod.getRandomFreeDayM();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayM' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayM' failed to provide an identifier", id);
        obj = freedays.app.FreeDayM.findFreeDayM(id);
        org.junit.Assert.assertNotNull("Find method for 'FreeDayM' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyFreeDayM(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'FreeDayM' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void FreeDayMIntegrationTest.testMerge() {
        freedays.app.FreeDayM obj = dod.getRandomFreeDayM();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayM' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayM' failed to provide an identifier", id);
        obj = freedays.app.FreeDayM.findFreeDayM(id);
        boolean modified =  dod.modifyFreeDayM(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        freedays.app.FreeDayM merged = (freedays.app.FreeDayM) obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'FreeDayM' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void FreeDayMIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayM' failed to initialize correctly", dod.getRandomFreeDayM());
        freedays.app.FreeDayM obj = dod.getNewTransientFreeDayM(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayM' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'FreeDayM' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'FreeDayM' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void FreeDayMIntegrationTest.testRemove() {
        freedays.app.FreeDayM obj = dod.getRandomFreeDayM();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayM' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayM' failed to provide an identifier", id);
        obj = freedays.app.FreeDayM.findFreeDayM(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'FreeDayM' with identifier '" + id + "'", freedays.app.FreeDayM.findFreeDayM(id));
    }
    
}
