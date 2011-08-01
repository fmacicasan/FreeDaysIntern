package freedays.app;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import java.util.Calendar;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Future;
import javax.persistence.DiscriminatorValue;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

import freedays.validation.annotation.BusinessDay;

@RooJavaBean
@RooToString
@RooEntity
@DiscriminatorValue("typeC")
public class FreeDayC extends FreeDay implements FreeDayRCMatchable<FreeDayR> {

    @NotNull
    @Future
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "S-")
    @BusinessDay
    private Calendar requestdate;

    @OneToOne
    private FreeDayR recover;

	@Override
	public Calendar getDate() {
		return this.getRequestdate();
	}

	public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Approval: ").append(getApproval()).append(", ");
//        sb.append("Date: ").append(getDate() == null ? "null" : getDate().getTime()).append(", ");
//        sb.append("Id: ").append(getId()).append(", ");
//        sb.append("Reason: ").append(getReason()).append(", ");
//        sb.append("Recover: ").append(getRecover()).append(", ");
//        sb.append("Requestdate: ").append(getRequestdate() == null ? "null" : getRequestdate().getTime()).append(", ");
//        sb.append("Version: ").append(getVersion()).append(", ");
//        sb.append("Cancelable: ").append(isCancelable());
//        return sb.toString();
		return super.toString();
    }

	@Override
	public FreeDayStatus getApproveStatus() {
		return FreeDayStatus.WAITING;
	}

	@Override
	public boolean match(FreeDayR match) {
		if(this.canMatch() &&  match.canMatch())return false;
		this.setMatch(match);
		match.setMatch(this);
		this.setMergedStatus();
		match.setMergedStatus();
		this.persist();
		match.persist();
		return true;
	}

	@Override
	public void setMatch(FreeDayR match) {
		this.recover = match;
		
	}

	@Override
	public FreeDayR getMatch() {
		return this.recover;
	}

	@Override
	public boolean canMatch() {
		return this.getMatch() != null;
	}
}
