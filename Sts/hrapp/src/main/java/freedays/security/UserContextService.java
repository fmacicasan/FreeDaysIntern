package freedays.security;

import freedays.domain.RegularUser;


public interface UserContextService {
	
	/**
	 * Retrieves the currently logged user.
	 * Will return null if there is no user currently logged in.
	 * @return
	 */
	public String getCurrentUser();
	
	/**
	 * Verifies weather or not the currently logged user has the role 
	 * textually represented by role.
	 * @param role
	 * @return
	 */
	public boolean hasRole(String role);
	
	/**
	 * Verifies weather or not the currently logged user is a HRManager
	 * @return
	 */
	public boolean isHR();

	/**
	 * Verifies weather the currently logged user corresponds to the
	 * provided regular user.
	 * @param regularUser
	 * @return
	 */
	public boolean isOwn(RegularUser regularUser);

}
