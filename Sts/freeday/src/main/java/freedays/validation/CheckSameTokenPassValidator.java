package freedays.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import freedays.domain.form.ChangePassTokenWrapper;
import freedays.validation.annotation.SameTokenPass;

public class CheckSameTokenPassValidator implements ConstraintValidator<SameTokenPass, ChangePassTokenWrapper> {

	@Override
	public void initialize(SameTokenPass constraintAnnotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(ChangePassTokenWrapper value,
			ConstraintValidatorContext context) {
		String pass1 = value.getPassword();
		if(pass1 == null) return false;
		String pass2 = value.getRepeatpassword();
		//no need to check pass2 for null due to equals propertie of not nullity
		return pass1.equals(pass2);
	}

}
