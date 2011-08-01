package freedays.app;

import org.springframework.roo.addon.dod.RooDataOnDemand;

import freedays.domain.ApprovalStrategy;
import freedays.util.DateUtils;
import java.util.Calendar;
import java.util.GregorianCalendar;

@RooDataOnDemand(entity = FreeDayC.class)
public class FreeDayCDataOnDemand {

	public static FreeDayC generateFreeDayC() {
		FreeDayC fdc = new FreeDayC();
		fdc.setApproval(AppStrategL1.getDefaultInitialStrateg());
		fdc.setRequestdate(DateUtils.generateFutureBusinessDay());
		return fdc;
	}

	public void setRequestdate(FreeDayC obj, int index) {
        obj.setRequestdate(DateUtils.generateFutureBusinessDay());
    }

	public void setApproval(FreeDayC obj, int index) {
        obj.setApproval(AppStrategL1.getDefaultInitialStrateg());
    }

	public void setRecover(FreeDayC obj, int index) {
        FreeDayR recover = null;
        obj.setRecover(recover);
    }
}
