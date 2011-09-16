// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import freedays.app.FreeDayNPDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect FreeDayNPIntegrationTest_Roo_IntegrationTest {
    
    declare @type: FreeDayNPIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: FreeDayNPIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: FreeDayNPIntegrationTest: @Transactional;
    
    @Autowired
    private FreeDayNPDataOnDemand FreeDayNPIntegrationTest.dod;
    
    @Test
    public void FreeDayNPIntegrationTest.testCountFreeDayNPs() {
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayNP' failed to initialize correctly", dod.getRandomFreeDayNP());
        long count = freedays.app.FreeDayNP.countFreeDayNPs();
        org.junit.Assert.assertTrue("Counter for 'FreeDayNP' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void FreeDayNPIntegrationTest.testFindFreeDayNP() {
        freedays.app.FreeDayNP obj = dod.getRandomFreeDayNP();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayNP' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayNP' failed to provide an identifier", id);
        obj = freedays.app.FreeDayNP.findFreeDayNP(id);
        org.junit.Assert.assertNotNull("Find method for 'FreeDayNP' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'FreeDayNP' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void FreeDayNPIntegrationTest.testFindAllFreeDayNPs() {
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayNP' failed to initialize correctly", dod.getRandomFreeDayNP());
        long count = freedays.app.FreeDayNP.countFreeDayNPs();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'FreeDayNP', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<freedays.app.FreeDayNP> result = freedays.app.FreeDayNP.findAllFreeDayNPs();
        org.junit.Assert.assertNotNull("Find all method for 'FreeDayNP' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'FreeDayNP' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void FreeDayNPIntegrationTest.testFindFreeDayNPEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayNP' failed to initialize correctly", dod.getRandomFreeDayNP());
        long count = freedays.app.FreeDayNP.countFreeDayNPs();
        if (count > 20) count = 20;
        java.util.List<freedays.app.FreeDayNP> result = freedays.app.FreeDayNP.findFreeDayNPEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'FreeDayNP' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'FreeDayNP' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void FreeDayNPIntegrationTest.testFlush() {
        freedays.app.FreeDayNP obj = dod.getRandomFreeDayNP();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayNP' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayNP' failed to provide an identifier", id);
        obj = freedays.app.FreeDayNP.findFreeDayNP(id);
        org.junit.Assert.assertNotNull("Find method for 'FreeDayNP' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyFreeDayNP(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'FreeDayNP' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void FreeDayNPIntegrationTest.testMerge() {
        freedays.app.FreeDayNP obj = dod.getRandomFreeDayNP();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayNP' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayNP' failed to provide an identifier", id);
        obj = freedays.app.FreeDayNP.findFreeDayNP(id);
        boolean modified =  dod.modifyFreeDayNP(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        freedays.app.FreeDayNP merged = (freedays.app.FreeDayNP) obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'FreeDayNP' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void FreeDayNPIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayNP' failed to initialize correctly", dod.getRandomFreeDayNP());
        freedays.app.FreeDayNP obj = dod.getNewTransientFreeDayNP(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayNP' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'FreeDayNP' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'FreeDayNP' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void FreeDayNPIntegrationTest.testRemove() {
        freedays.app.FreeDayNP obj = dod.getRandomFreeDayNP();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayNP' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDayNP' failed to provide an identifier", id);
        obj = freedays.app.FreeDayNP.findFreeDayNP(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'FreeDayNP' with identifier '" + id + "'", freedays.app.FreeDayNP.findFreeDayNP(id));
    }
    
}
