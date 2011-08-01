package freedays.app;

import java.util.Calendar;
import java.util.GregorianCalendar;
import org.springframework.roo.addon.dod.RooDataOnDemand;

import freedays.util.DateUtils;

@RooDataOnDemand(entity = FreeDayR.class)
public class FreeDayRDataOnDemand {

	public void setRecoverdate(FreeDayR obj, int index) {
        Calendar recoverdate = DateUtils.generateFutureWeekendDay();
        obj.setRecoverdate(recoverdate);
    }
}
