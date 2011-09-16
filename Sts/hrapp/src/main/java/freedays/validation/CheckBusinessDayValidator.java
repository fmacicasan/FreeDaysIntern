package freedays.validation;

import java.util.Calendar;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import freedays.util.ValidationUtils;
import freedays.validation.annotation.BusinessDay;

/**
 * Validator used by the {@link freedays.validation.annotation.BusinessDay @BusinessDay} annotation.
 * @author fmacicasan
 *
 */
public class CheckBusinessDayValidator implements ConstraintValidator<BusinessDay, Calendar>{

	@Override
	public void initialize(BusinessDay constraintAnnotation) {
		// nothing here
		
	}

	@Override
	public boolean isValid(Calendar value, ConstraintValidatorContext context) {
//		int dayOfWeek = value.get(Calendar.DAY_OF_WEEK);
//		return dayOfWeek != Calendar.SATURDAY
//				&& dayOfWeek != Calendar.SUNDAY;
		return ValidationUtils.checkBusinessDay(value);
	}


}
