package freedays.app;

import org.springframework.roo.addon.dod.RooDataOnDemand;

import freedays.domain.ApprovalStrategy;
import freedays.util.DateUtils;

@RooDataOnDemand(entity = FreeDayR.class)
public class FreeDayRDataOnDemand {

	public void setRecoverdate(FreeDayR obj, int index) {
        obj.setRecoverdate(DateUtils.generateFutureWeekendDay());
    }

	public static FreeDayR getenrateFreeDayR() {
		FreeDayR fdr = new FreeDayR();
		fdr.setApproval(AppStrategL1.getDefaultInitialStrateg());
		fdr.setRecoverdate(DateUtils.generateFutureWeekendDay());
		return fdr;
	}

	public void setApproval(FreeDayR obj, int index) {
        obj.setApproval(AppStrategL1.getDefaultInitialStrateg());
    }

	public void setRequest(FreeDayR obj, int index) {
        FreeDayC request = null;
        obj.setRequest(request);
    }
}
