package freedays.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import freedays.domain.form.SignupWrapper;
import freedays.validation.annotation.SamePass;

public class CheckSamePassValidator implements ConstraintValidator<SamePass, SignupWrapper> {

	@Override
	public void initialize(SamePass arg0) {
		// nothing here
		
	}

	@Override
	public boolean isValid(SignupWrapper sw, ConstraintValidatorContext arg1) {
		String pass1 = sw.getPassword();
		if(pass1==null)return false;
		String pass2 = sw.getRepeatpassword();
		if(pass2==null)return false;
		return pass1.equals(pass2);
	}

}
