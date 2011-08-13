package freedays.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = RequestGranter.class)
public class RequestGranterDataOnDemand {

	private List<RequestGranter> data;
	public void init() {
//        data = RequestGranter.findRequestGranterEntries(0, 10);
//        if (data == null) throw new IllegalStateException("Find entries implementation for 'RequestGranter' illegally returned null");
//        if (!data.isEmpty()) {
//            return;
//        }
        
        data = new ArrayList<freedays.domain.RequestGranter>();
        for (int i = 0; i < 10; i++) {
            RequestGranter obj = getNewTransientRequestGranter(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> it = e.getConstraintViolations().iterator(); it.hasNext();) {
                    ConstraintViolation<?> cv = it.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
}
