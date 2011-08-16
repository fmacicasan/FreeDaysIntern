package freedays.validation;

import java.util.Calendar;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import freedays.util.ValidationUtils;
import freedays.validation.annotation.Weekend;

/**
 * 
 * @author fmacicasan
 * @see Weekend
 */
public class CheckWeekendDayValidator implements ConstraintValidator<Weekend, Calendar> {

	@Override
	public void initialize(Weekend constraintAnnotation) {
		// nothing here
		
	}

	@Override
	public boolean isValid(Calendar value, ConstraintValidatorContext context) {
//		int dayOfWeek = value.get(Calendar.DAY_OF_WEEK);
//		return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
		return ValidationUtils.checkWeekend(value);
	}

}
