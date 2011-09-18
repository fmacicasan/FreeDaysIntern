// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import freedays.app.FreeDayRLDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect FreeDayRLIntegrationTest_Roo_IntegrationTest {
    
    declare @type: FreeDayRLIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: FreeDayRLIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: FreeDayRLIntegrationTest: @Transactional;
    
    @Autowired
    private FreeDayRLDataOnDemand FreeDayRLIntegrationTest.dod;
    
    @Test
    public void FreeDayRLIntegrationTest.testCountFreeDayRLs() {
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayRL' failed to initialize correctly", dod.getRandomFreeDayRL());
        long count = freedays.app.FreeDayRL.countFreeDayRLs();
        org.junit.Assert.assertTrue("Counter for 'FreeDayRL' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void FreeDayRLIntegrationTest.testFindFreeDayRL() {
        freedays.app.FreeDayRL obj = dod.getRandomFreeDayRL();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayRL' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayRL' failed to provide an identifier", id);
        obj = freedays.app.FreeDayRL.findFreeDayRL(id);
        org.junit.Assert.assertNotNull("Find method for 'FreeDayRL' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'FreeDayRL' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void FreeDayRLIntegrationTest.testFindAllFreeDayRLs() {
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayRL' failed to initialize correctly", dod.getRandomFreeDayRL());
        long count = freedays.app.FreeDayRL.countFreeDayRLs();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'FreeDayRL', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<freedays.app.FreeDayRL> result = freedays.app.FreeDayRL.findAllFreeDayRLs();
        org.junit.Assert.assertNotNull("Find all method for 'FreeDayRL' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'FreeDayRL' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void FreeDayRLIntegrationTest.testFindFreeDayRLEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayRL' failed to initialize correctly", dod.getRandomFreeDayRL());
        long count = freedays.app.FreeDayRL.countFreeDayRLs();
        if (count > 20) count = 20;
        java.util.List<freedays.app.FreeDayRL> result = freedays.app.FreeDayRL.findFreeDayRLEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'FreeDayRL' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'FreeDayRL' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void FreeDayRLIntegrationTest.testFlush() {
        freedays.app.FreeDayRL obj = dod.getRandomFreeDayRL();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayRL' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayRL' failed to provide an identifier", id);
        obj = freedays.app.FreeDayRL.findFreeDayRL(id);
        org.junit.Assert.assertNotNull("Find method for 'FreeDayRL' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyFreeDayRL(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'FreeDayRL' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void FreeDayRLIntegrationTest.testMerge() {
        freedays.app.FreeDayRL obj = dod.getRandomFreeDayRL();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayRL' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayRL' failed to provide an identifier", id);
        obj = freedays.app.FreeDayRL.findFreeDayRL(id);
        boolean modified =  dod.modifyFreeDayRL(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        freedays.app.FreeDayRL merged =  obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'FreeDayRL' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void FreeDayRLIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayRL' failed to initialize correctly", dod.getRandomFreeDayRL());
        freedays.app.FreeDayRL obj = dod.getNewTransientFreeDayRL(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayRL' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'FreeDayRL' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'FreeDayRL' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void FreeDayRLIntegrationTest.testRemove() {
        freedays.app.FreeDayRL obj = dod.getRandomFreeDayRL();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayRL' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayRL' failed to provide an identifier", id);
        obj = freedays.app.FreeDayRL.findFreeDayRL(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'FreeDayRL' with identifier '" + id + "'", freedays.app.FreeDayRL.findFreeDayRL(id));
    }
    
}