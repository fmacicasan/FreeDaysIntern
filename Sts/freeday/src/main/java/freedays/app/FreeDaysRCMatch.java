package freedays.app;

import java.util.Calendar;


/**
 * Class used to extract the common behavior exhibited during the matching process of two objects implementing
 * the {@link freedays.app.FreeDayRCMatchable FreeDayRCMatchable} interface.
 * @author fmacicasan
 *
 * @param <T> represents the class to which the matching is restricted. It must <b>extend</b> {@link freedays.app.FreeDay FreeDay}.
 */
@Deprecated
public abstract class  FreeDaysRCMatch<T extends FreeDay> extends FreeDay implements FreeDayRCMatchable<T>{

	@Override
	public boolean match(T match) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public abstract void setMatch(T match);

	@Override
	public abstract T getMatch();

	@Override
	public boolean canMatch() {
		return this.getMatch() != null;
	}

	@Override
	public abstract Calendar getDate();

	@Override
	public abstract FreeDayStatus getApproveStatus();

}
