
package freedays.validation; 

import freedays.domain.RegularUser;

/**
 * Validates a regular user from the point of view of the unique email address.
 * @author fmacicasan
 *
 */
public class RegularUserValidator {

	/**
	 * Validates a regular user based on his email uniqueness.
	 * @param regularUser
	 * @return
	 */
	public static boolean validate(RegularUser regularUser) {
		return RegularUser.countRegularUserByEmail(regularUser.getEmail()) == 0;
	}

}
