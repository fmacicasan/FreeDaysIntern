// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect FreeDayLIntegrationTest_Roo_IntegrationTest {
    
    declare @type: FreeDayLIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: FreeDayLIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: FreeDayLIntegrationTest: @Transactional;
    
    @Test
    public void FreeDayLIntegrationTest.testCountFreeDayLs() {
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayL' failed to initialize correctly", dod.getRandomFreeDayL());
        long count = freedays.app.FreeDayL.countFreeDayLs();
        org.junit.Assert.assertTrue("Counter for 'FreeDayL' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void FreeDayLIntegrationTest.testFindFreeDayL() {
        freedays.app.FreeDayL obj = dod.getRandomFreeDayL();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayL' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayL' failed to provide an identifier", id);
        obj = freedays.app.FreeDayL.findFreeDayL(id);
        org.junit.Assert.assertNotNull("Find method for 'FreeDayL' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'FreeDayL' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void FreeDayLIntegrationTest.testFindAllFreeDayLs() {
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayL' failed to initialize correctly", dod.getRandomFreeDayL());
        long count = freedays.app.FreeDayL.countFreeDayLs();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'FreeDayL', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<freedays.app.FreeDayL> result = freedays.app.FreeDayL.findAllFreeDayLs();
        org.junit.Assert.assertNotNull("Find all method for 'FreeDayL' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'FreeDayL' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void FreeDayLIntegrationTest.testFindFreeDayLEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayL' failed to initialize correctly", dod.getRandomFreeDayL());
        long count = freedays.app.FreeDayL.countFreeDayLs();
        if (count > 20) count = 20;
        java.util.List<freedays.app.FreeDayL> result = freedays.app.FreeDayL.findFreeDayLEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'FreeDayL' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'FreeDayL' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void FreeDayLIntegrationTest.testFlush() {
        freedays.app.FreeDayL obj = dod.getRandomFreeDayL();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayL' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayL' failed to provide an identifier", id);
        obj = freedays.app.FreeDayL.findFreeDayL(id);
        org.junit.Assert.assertNotNull("Find method for 'FreeDayL' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyFreeDayL(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'FreeDayL' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void FreeDayLIntegrationTest.testMerge() {
        freedays.app.FreeDayL obj = dod.getRandomFreeDayL();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayL' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayL' failed to provide an identifier", id);
        obj = freedays.app.FreeDayL.findFreeDayL(id);
        boolean modified =  dod.modifyFreeDayL(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        freedays.app.FreeDayL merged = (freedays.app.FreeDayL) obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'FreeDayL' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void FreeDayLIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayL' failed to initialize correctly", dod.getRandomFreeDayL());
        freedays.app.FreeDayL obj = dod.getNewTransientFreeDayL(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayL' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'FreeDayL' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'FreeDayL' identifier to no longer be null", obj.getId());
    }
    
}