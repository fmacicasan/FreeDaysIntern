// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.domain;

import freedays.domain.RequestGranterDataOnDemand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

privileged aspect RequestGranterIntegrationTest_Roo_IntegrationTest {
    
    declare @type: RequestGranterIntegrationTest: @RunWith(SpringJUnit4ClassRunner.class);
    
    declare @type: RequestGranterIntegrationTest: @ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml");
    
    declare @type: RequestGranterIntegrationTest: @Transactional;
    
    @Autowired
    private RequestGranterDataOnDemand RequestGranterIntegrationTest.dod;
    
    @Test
    public void RequestGranterIntegrationTest.testCountRequestGranters() {
        org.junit.Assert.assertNotNull("Data on demand for 'RequestGranter' failed to initialize correctly", dod.getRandomRequestGranter());
        long count = freedays.domain.RequestGranter.countRequestGranters();
        org.junit.Assert.assertTrue("Counter for 'RequestGranter' incorrectly reported there were no entries", count > 0);
    }
    
    @Test
    public void RequestGranterIntegrationTest.testFindRequestGranter() {
        freedays.domain.RequestGranter obj = dod.getRandomRequestGranter();
        org.junit.Assert.assertNotNull("Data on demand for 'RequestGranter' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'RequestGranter' failed to provide an identifier", id);
        obj = freedays.domain.RequestGranter.findRequestGranter(id);
        org.junit.Assert.assertNotNull("Find method for 'RequestGranter' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'RequestGranter' returned the incorrect identifier", id, obj.getId());
    }
    
    @Test
    public void RequestGranterIntegrationTest.testFindAllRequestGranters() {
        org.junit.Assert.assertNotNull("Data on demand for 'RequestGranter' failed to initialize correctly", dod.getRandomRequestGranter());
        long count = freedays.domain.RequestGranter.countRequestGranters();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'RequestGranter', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<freedays.domain.RequestGranter> result = freedays.domain.RequestGranter.findAllRequestGranters();
        org.junit.Assert.assertNotNull("Find all method for 'RequestGranter' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'RequestGranter' failed to return any data", result.size() > 0);
    }
    
    @Test
    public void RequestGranterIntegrationTest.testFindRequestGranterEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'RequestGranter' failed to initialize correctly", dod.getRandomRequestGranter());
        long count = freedays.domain.RequestGranter.countRequestGranters();
        if (count > 20) count = 20;
        java.util.List<freedays.domain.RequestGranter> result = freedays.domain.RequestGranter.findRequestGranterEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'RequestGranter' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'RequestGranter' returned an incorrect number of entries", count, result.size());
    }
    
    @Test
    public void RequestGranterIntegrationTest.testFlush() {
        freedays.domain.RequestGranter obj = dod.getRandomRequestGranter();
        org.junit.Assert.assertNotNull("Data on demand for 'RequestGranter' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'RequestGranter' failed to provide an identifier", id);
        obj = freedays.domain.RequestGranter.findRequestGranter(id);
        org.junit.Assert.assertNotNull("Find method for 'RequestGranter' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyRequestGranter(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'RequestGranter' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void RequestGranterIntegrationTest.testMerge() {
        freedays.domain.RequestGranter obj = dod.getRandomRequestGranter();
        org.junit.Assert.assertNotNull("Data on demand for 'RequestGranter' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'RequestGranter' failed to provide an identifier", id);
        obj = freedays.domain.RequestGranter.findRequestGranter(id);
        boolean modified =  dod.modifyRequestGranter(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        freedays.domain.RequestGranter merged = (freedays.domain.RequestGranter) obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'RequestGranter' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }
    
    @Test
    public void RequestGranterIntegrationTest.testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'RequestGranter' failed to initialize correctly", dod.getRandomRequestGranter());
        freedays.domain.RequestGranter obj = dod.getNewTransientRequestGranter(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'RequestGranter' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'RequestGranter' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'RequestGranter' identifier to no longer be null", obj.getId());
    }
    
    @Test
    public void RequestGranterIntegrationTest.testRemove() {
        freedays.domain.RequestGranter obj = dod.getRandomRequestGranter();
        org.junit.Assert.assertNotNull("Data on demand for 'RequestGranter' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'RequestGranter' failed to provide an identifier", id);
        obj = freedays.domain.RequestGranter.findRequestGranter(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'RequestGranter' with identifier '" + id + "'", freedays.domain.RequestGranter.findRequestGranter(id));
    }
    
}
