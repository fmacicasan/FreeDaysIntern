package freedays.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import freedays.app.FreeDay;
import freedays.validation.annotation.FreeDaySpecificDateConstraint;

/**
 * 
 * @author fmacicasan
 * @see FreeDaySpecificDateConstraint
 *
 */
public class CheckFreeDaySpecificDateConstraint implements ConstraintValidator<FreeDaySpecificDateConstraint,FreeDay> {

	@Override
	public void initialize(FreeDaySpecificDateConstraint arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(FreeDay arg0, ConstraintValidatorContext arg1) {
		return arg0.customValidationPolicy();
	}

}
