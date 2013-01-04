package freedays.app;

import javax.persistence.DiscriminatorValue;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import freedays.app.FreeDayInterval.ConfidenceLevel;
import freedays.app.form.FreeDayRequest;
import freedays.app.form.FreeDayRequestInterval;
import freedays.app.form.FreeDayRequest.RequestType;
import freedays.timesheet.ReportLegend;
import freedays.util.DateUtils;
import freedays.util.PropertiesUtil;

@RooJavaBean
@RooEntity
@DiscriminatorValue("typeNP")
public class FreeDayNP extends FreeDayInterval {

	@Override
	protected FreeDayStatus getApproveStatus() {
		return FreeDayStatus.FINALIZE_SUCCESS;
	}

	/*
	 * @Override protected void initialize(FreeDayRequest fdr) {
	 * 
	 * super.initialize(fdr);
	 * 
	 * 
	 * // if(!(fdr instanceof FreeDayRequestVacation))throw new //
	 * IllegalArgumentException
	 * ("The fdr argument must be of type FreeDayRequestVacation"); if (fdr
	 * instanceof FreeDayRequestInterval) { // initialize a multiple days
	 * vacation FreeDayRequestInterval fdrv = (FreeDayRequestInterval) fdr;
	 * this.setConfidence(fdrv.getConfidence());
	 * this.setSpan(DateUtils.dateDifferenceInWorkingDays(fdrv.getReqdate(),
	 * fdrv.getFinish())); } else { // initialize a 1 day vacataion
	 * this.setSpan(0); this.setConfidence(ConfidenceLevel.HIGH); }
	 * 
	 * }
	 */

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Not payed Request:");
		// sb.append(" from ").append(DateUtils.printShortDate(this.getDate()));
		// sb.append(" to ").append(DateUtils.printShortDate(this.getEnd()));
		sb.append(this.getDateReport());
		// DateUtils.dateAddRomanianBusinessDay(this.getDate(), this.getSpan())
		return sb.toString();
	}

	@Override
	protected void finalizeFail() {
		// nothing special here

	}

	@Override
	public RequestType getType() {
		return RequestType.NP;
	}

	@Override
	public String getReportType() {
		return PropertiesUtil.getProperty("freedaysreport_legend_typenp");
	}
	
	//Added for report generation
    @Override
    public boolean hasReportLegend() {
        return true;
    }

    @Override
    public String getReportLegendCode() {
        return ReportLegend.NO_PAY.getTerm();
    }
}
