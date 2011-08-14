package freedays.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import freedays.app.FreeDayRequest;
import freedays.domain.RegularUser;
import freedays.domain.form.SignupWrapper;
import freedays.validation.annotation.UniqueDayPerActiveOrApprovedReq;
import freedays.validation.annotation.UniqueEmail;

public class CheckUniqueEmailValidator  implements ConstraintValidator<UniqueEmail, String> {

	@Override
	public void initialize(UniqueEmail arg0) {
		// nothing here
		
	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext arg1) {
		if(email==null)return false;
		return RegularUser.countRegularUserByEmail(email) == 0;
	}

}
