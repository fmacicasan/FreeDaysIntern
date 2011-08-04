package freedays.app;

import java.util.Calendar;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.app.FreeDay.FreeDayStatus;
import freedays.util.DateUtils;


/**
 * Class used to extract the common behavior exhibited during the matching process of two objects implementing
 * the {@link freedays.app.FreeDayRCMatchable FreeDayRCMatchable} interface.
 * @author fmacicasan
 *
 * @param <T> represents the class to which the matching is restricted. It must <b>extend</b> {@link freedays.app.FreeDay FreeDay}.
 */
@RooJavaBean
@RooEntity
public abstract class  FreeDaysRCMatch extends FreeDay{
	
	  
	
	public boolean match(FreeDaysRCMatch match) {
		if(match == null)throw new IllegalArgumentException("The username argument is required");
		if(match.canMatch())return false;
		//this.setMatch(match);
		match.setMatch(this);
		//this.setMergedStatus();
		match.setMergedStatus();
		//this.persist();
		match.persist();
		return true;
	}

	public abstract void setMatch(FreeDaysRCMatch match);
	public abstract FreeDaysRCMatch getMatch();

	public boolean canMatch() {
		return this.getMatch() != null;
	}

	@Override
	protected abstract Calendar getDate();

	@Override
	protected abstract void setDate(Calendar date);

	@Override
	public FreeDayStatus getApproveStatus() {
		//TODO: replace with catch from exception thrown by match
		if(this.getMatch() == null){
			return FreeDayStatus.WAITING;
		}
		if(this.match(this.getMatch())){
			return FreeDayStatus.COMPLETED_SUCCESS;
		} else {
			//if it gets here then the matching cannot take place => the current match was already
			//matched so the request goes back in waiting state.
			this.setMatch(null);
			return FreeDayStatus.WAITING;
		}
	}

	@Override
	protected void initialize(FreeDayRequest fdr) {
		FreeDaysRCMatch match = fdr.getMatch();
		this.setMatch(match);
		if(match != null){
			match.setInitStatus();
			match.persist();
		}
	}
	
	@Override
	protected void finalizeFail(){
		FreeDaysRCMatch match = this.getMatch();
		if(match != null){
			match.setFinalApproveStatus();
		}
	}
	
	@Override
	public String reportPrint(){
		if(this.getMatch()!=null){
			StringBuilder sb = new StringBuilder();
			sb.append(super.reportPrint());
			sb.append("/").append(DateUtils.printShortDate(this.getMatch().getDate())).append(this.getMatch().getType());
			return sb.toString();
		}
		return super.reportPrint();
	}

}
