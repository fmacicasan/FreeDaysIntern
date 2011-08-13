package freedays.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = Admin.class)
public class AdminDataOnDemand {
	private List<Admin> data;
	

	public void init() {
//        data = Admin.findAdminEntries(0, 10);
//        if (data == null) throw new IllegalStateException("Find entries implementation for 'Admin' illegally returned null");
//        if (!data.isEmpty()) {
//            return;
//        }
        
        data = new ArrayList<freedays.domain.Admin>();
        for (int i = 0; i < 10; i++) {
            Admin obj = getNewTransientAdmin(i);
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
