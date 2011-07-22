package freedays.domain;


import freedays.util.MailUtils;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;


@RooIntegrationTest(entity = RegularUser.class)
public class RegularUserIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }
    
    @Test
    public void testFindAllRegularUsersLikeMethod(){
    	freedays.domain.RegularUser obj = dod.getRandomRegularUser();
        org.junit.Assert.assertNotNull("Data on demand for 'RegularUser' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'RegularUser' failed to provide an identifier", id);
        obj = freedays.domain.RegularUser.findRegularUser(id);
        org.junit.Assert.assertNotNull("Find method for 'RegularUser' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'RegularUser' returned the incorrect identifier", id, obj.getId());
        freedays.domain.Search search = new Search();
        search.setSearchKey(RegularUser.SEARCH_FILTERS[0]);
        search.setSearchValue(obj.getUsername());
        java.util.List<RegularUser> all = freedays.domain.RegularUser.findAllRegularUsersLike(search);
        org.junit.Assert.assertTrue("Search method did not find added unit", all.contains(obj));
    }
    
    @Test
    public void testRemove() {
        freedays.domain.RegularUser obj = dod.getRandomRegularUser();
        org.junit.Assert.assertNotNull("Data on demand for 'RegularUser' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'RegularUser' failed to provide an identifier", id);
        obj = freedays.domain.RegularUser.findRegularUser(id);
        //obj.remove();
        //obj.flush();
        //org.junit.Assert.assertNull("Failed to remove 'RegularUser' with identifier '" + id + "'", freedays.domain.RegularUser.findRegularUser(id));
    }
	

	@Autowired
    private RegularUserDataOnDemand dod;
}
