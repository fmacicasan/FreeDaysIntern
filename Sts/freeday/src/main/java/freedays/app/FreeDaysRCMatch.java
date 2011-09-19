package freedays.app;

import java.util.LinkedList;
import java.util.List;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.app.form.FreeDayRequest;
import freedays.util.DateUtils;


/**
 * Class used to extract the common behavior exhibited during the matching process of two objects 
 * matchable objects. Such objects need a complement to finalize.
 * @author fmacicasan
 *
 * @param <T> represents the class to which the matching is restricted. It must <b>extend</b> {@link freedays.app.FreeDay FreeDay}.
 */
@RooJavaBean
@RooEntity
public abstract class  FreeDaysRCMatch extends FreeDay{
	
	  
	/**
	 * Performs the match with a complement
	 * @param match the complement of the current request
	 * @return true if the matching completed successfully
	 * @See FreeDaysRCMatch
	 */
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
	
	/**
	 * Abstract method used by to register their matching complement.
	 * @param match
	 */
	public abstract void setMatch(FreeDaysRCMatch match);
	
	/**
	 * Abstract method used to get the matching complement.
	 */
	public abstract FreeDaysRCMatch getMatch();

	/**
	 * Changes the status of a mergeable free day after the
	 * merging took place.
	 */
	protected void setMergedStatus(){
		super.setStatus(FreeDayStatus.FINALIZE_SUCCESS);
	}
	
	/**
	 * Verifies if a match can take place.
	 * @return
	 */
	public boolean canMatch() {
		return this.getMatch() != null;
	}

//	@Override
//	public abstract Calendar getDate();
//
//	@Override
//	protected abstract void setDate(Calendar date);

	@Override
	public FreeDayStatus getApproveStatus() {
		//TODO: replace with catch from exception thrown by match
		if(this.getMatch() == null){
			return FreeDayStatus.WAITING;
		}
		if(this.match(this.getMatch())){
			return FreeDayStatus.FINALIZE_SUCCESS;
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
			match.merge();
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
	public List<String> reportPrint(){
		if(this.getMatch()!=null){
			StringBuilder sb = new StringBuilder();
			sb.append(super.reportPrint());
			sb.append("/").append(DateUtils.printShortDate(this.getMatch().getDate())).append(this.getMatch().getType());
			List<String> lst = new LinkedList<String>();
			lst.add(sb.toString());
			return lst;
		}
		return super.reportPrint();
	}

}
