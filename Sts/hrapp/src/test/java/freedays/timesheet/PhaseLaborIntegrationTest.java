package freedays.timesheet;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

@RooIntegrationTest(entity = PhaseLabor.class)
public class PhaseLaborIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }

    @Autowired
    private PhaseLaborDataOnDemand dod;
    
	@Test
    public void testRemove() {
        freedays.timesheet.PhaseLabor obj = dod.getRandomPhaseLabor();
        org.junit.Assert.assertNotNull("Data on demand for 'PhaseLabor' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'PhaseLabor' failed to provide an identifier", id);
        obj = freedays.timesheet.PhaseLabor.findPhaseLabor(id);
        //obj.remove();
        //obj.flush();
        //org.junit.Assert.assertNull("Failed to remove 'PhaseLabor' with identifier '" + id + "'", freedays.timesheet.PhaseLabor.findPhaseLabor(id));
    }
}
