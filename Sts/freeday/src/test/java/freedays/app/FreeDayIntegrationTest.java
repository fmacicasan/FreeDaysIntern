package freedays.app;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

@RooIntegrationTest(entity = FreeDay.class)
public class FreeDayIntegrationTest {

    @Autowired
    private FreeDayDataOnDemand dod;
    
    @Test
    public void testMarkerMethod() {
    }

	@Test
    public void testRemove() {
        freedays.app.FreeDay obj = dod.getRandomFreeDay();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDay' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'FreeDay' failed to provide an identifier", id);
        obj = freedays.app.FreeDay.findFreeDay(id);
        //obj.remove();
        //obj.flush();
        //org.junit.Assert.assertNull("Failed to remove 'FreeDay' with identifier '" + id + "'", freedays.app.FreeDay.findFreeDay(id));
    }
}
