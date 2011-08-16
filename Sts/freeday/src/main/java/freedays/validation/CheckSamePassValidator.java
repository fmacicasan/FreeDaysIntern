package freedays.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import freedays.domain.RegularUser;
import freedays.domain.form.SignupWrapper;
import freedays.validation.annotation.SamePass;
import freedays.validation.annotation.UniqueDayPerActiveOrApprovedReq;

/**
 * 
 * @author fmacicasan
 * @see SamePass
 */
public class CheckSamePassValidator implements ConstraintValidator<SamePass, SignupWrapper> {

	@Override
	public void initialize(SamePass arg0) {
		// nothing here
		
	}

	@Override
	public boolean isValid(SignupWrapper sw, ConstraintValidatorContext arg1) {
		String username = sw.getUsername();
		if(RegularUser.countRegularUserByUsername(username)!=0) return false;
		String pass1 = sw.getPassword();
		if(pass1==null)return false;
		String pass2 = sw.getRepeatpassword();
		if(pass2==null)return false;
		return pass1.equals(pass2);
	}

}
