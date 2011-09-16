package freedays.timesheet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = TimesheetAdmin.class)
public class TimesheetAdminDataOnDemand {

    private List<TimesheetAdmin> data;
	public void init() {
        /*data = TimesheetAdmin.findTimesheetAdminEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'TimesheetAdmin' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        */
        data = new ArrayList<freedays.timesheet.TimesheetAdmin>();
        for (int i = 0; i < 10; i++) {
            TimesheetAdmin obj = getNewTransientTimesheetAdmin(i);
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
