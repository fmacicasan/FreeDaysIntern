
package freedays.validation; 

import freedays.domain.RegularUser;

public class RegularUserValidator {

	public static boolean validate(RegularUser regularUser) {
		return RegularUser.countRegularUserByEmail(regularUser.getEmail()) == 0;
	}

}

