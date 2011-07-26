// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import freedays.app.FDUserDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect FDUserIntegrationTest_Roo_IntegrationTest {
    
    declare @type: FDUserIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: FDUserIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: FDUserIntegrationTest: @Transactional;
    
    @Autowired
    private FDUserDataOnDemand FDUserIntegrationTest.dod;
    
    @Test
    public void FDUserIntegrationTest.testCountFDUsers() {
        org.junit.Assert.assertNotNull("Data on demand for 'FDUser' failed to initialize correctly", dod.getRandomFDUser());
        long count = freedays.app.FDUser.countFDUsers();
        org.junit.Assert.assertTrue("Counter for 'FDUser' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void FDUserIntegrationTest.testFindFDUser() {
        freedays.app.FDUser obj = dod.getRandomFDUser();
        org.junit.Assert.assertNotNull("Data on demand for 'FDUser' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FDUser' failed to provide an identifier", id);
        obj = freedays.app.FDUser.findFDUser(id);
        org.junit.Assert.assertNotNull("Find method for 'FDUser' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'FDUser' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void FDUserIntegrationTest.testFindAllFDUsers() {
        org.junit.Assert.assertNotNull("Data on demand for 'FDUser' failed to initialize correctly", dod.getRandomFDUser());
        long count = freedays.app.FDUser.countFDUsers();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'FDUser', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<freedays.app.FDUser> result = freedays.app.FDUser.findAllFDUsers();
        org.junit.Assert.assertNotNull("Find all method for 'FDUser' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'FDUser' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void FDUserIntegrationTest.testFindFDUserEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'FDUser' failed to initialize correctly", dod.getRandomFDUser());
        long count = freedays.app.FDUser.countFDUsers();
        if (count > 20) count = 20;
        java.util.List<freedays.app.FDUser> result = freedays.app.FDUser.findFDUserEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'FDUser' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'FDUser' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void FDUserIntegrationTest.testFlush() {
        freedays.app.FDUser obj = dod.getRandomFDUser();
        org.junit.Assert.assertNotNull("Data on demand for 'FDUser' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FDUser' failed to provide an identifier", id);
        obj = freedays.app.FDUser.findFDUser(id);
        org.junit.Assert.assertNotNull("Find method for 'FDUser' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyFDUser(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'FDUser' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void FDUserIntegrationTest.testMerge() {
        freedays.app.FDUser obj = dod.getRandomFDUser();
        org.junit.Assert.assertNotNull("Data on demand for 'FDUser' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FDUser' failed to provide an identifier", id);
        obj = freedays.app.FDUser.findFDUser(id);
        boolean modified =  dod.modifyFDUser(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        freedays.app.FDUser merged = (freedays.app.FDUser) obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'FDUser' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void FDUserIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'FDUser' failed to initialize correctly", dod.getRandomFDUser());
        freedays.app.FDUser obj = dod.getNewTransientFDUser(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'FDUser' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'FDUser' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'FDUser' identifier to no longer be null", obj.getId());
    }
    
}