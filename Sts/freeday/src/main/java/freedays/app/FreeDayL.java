package freedays.app;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Future;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

import freedays.domain.ApplicationRegularUser;
import freedays.domain.ApprovalStrategy;
import freedays.validation.annotation.BusinessDay;

import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooEntity
@DiscriminatorValue("typeL")
public class FreeDayL extends FreeDay {

    @NotNull
    @Future
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "S-")
    @BusinessDay
    private Calendar legalday;
    
    public static FreeDayL createPersistentFreeDay(Calendar date, String reason){
		if (date == null) throw new IllegalArgumentException("The date argument is required");
    	FreeDayL fd = new FreeDayL();
    	fd.setLegalday(date);
    	fd.setReason(reason);
    	fd.setApproval(AppStrategL1.getDefaultInitialStrateg());
    	fd.persist();
    	return fd;
    }

	@Override
	public Calendar getDate() {
		return this.getLegalday();
	}
    

    
	



	public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Approval: ").append(getApproval()).append(", ");
//        sb.append("Date: ").append(getDate() == null ? "null" : getDate().getTime()).append(", ");
//        sb.append("Id: ").append(getId()).append(", ");
//        sb.append("Legalday: ").append(getLegalday() == null ? "null" : getLegalday().getTime()).append(", ");
//        sb.append("Reason: ").append(getReason()).append(", ");
//        sb.append("Version: ").append(getVersion()).append(", ");
//        sb.append("Cancelable: ").append(isCancelable());
//        return sb.toString();
		return super.toString();
    }

	@Override
	public FreeDayStatus getApproveStatus() {
		return FreeDayStatus.COMPLETED_SUCCESS;
	}
}
