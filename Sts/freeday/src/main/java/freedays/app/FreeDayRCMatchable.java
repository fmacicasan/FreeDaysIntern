package freedays.app;

/**
 * Describes the intended matching behavior
 * of the type R/C free day requests.
 * @author fmacicasan
 *
 */
public interface FreeDayRCMatchable<T extends FreeDay> {
	
	/**
	 * Performs the matching between a type R/C request
	 * and its counterpart.
	 * @param match the pair request
	 * @return 	<i>true</i> if the matching was successful
	 * 			and <i>false</i> if the target was already matched
	 */
	public  boolean match(T match);
	
	/**
	 * Sets the matching element. Will be used to mark the correlation
	 * between an instance of type T and the implementor of the interface.
	 * @param match the matching element
	 */
	public void setMatch(T match);
	
	/**
	 * Obtains the matching element.
	 * @return the matching element
	 */
	public T getMatch();
	
	/**
	 * Verifies if the current object can be matched.
	 * @return <i>true</i> if a matching can occur and
	 * 			<i>false</i> otherwise.
	 */
	public boolean canMatch();

}
