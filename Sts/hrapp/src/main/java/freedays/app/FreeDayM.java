package freedays.app;

import javax.persistence.DiscriminatorValue;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import freedays.app.form.FreeDayRequest;
import freedays.app.form.FreeDayRequest.RequestType;
import freedays.util.PropertiesUtil;

@RooJavaBean
@RooEntity
@DiscriminatorValue("typeM")
public class FreeDayM extends FreeDay {

	@Override
	protected FreeDayStatus getApproveStatus() {
		return FreeDayStatus.FINALIZE_SUCCESS;
	}

	@Override
	protected void initialize(FreeDayRequest fdr) {
		// nothing special here	
	}

	@Override
	protected void finalizeFail() {
		// nothing special here
		
	}

	@Override
	public RequestType getType() {
		return RequestType.M;
	}

	@Override
	public String getReportType() {
		return PropertiesUtil.getProperty("freedaysreport_legend_typem");
	}
}
