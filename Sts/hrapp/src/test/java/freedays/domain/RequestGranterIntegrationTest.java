package freedays.domain;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

@RooIntegrationTest(entity = RequestGranter.class)
public class RequestGranterIntegrationTest {

	@Autowired
    private RequestGranterDataOnDemand dod;
    @Test
    public void testMarkerMethod() {
    }

}
