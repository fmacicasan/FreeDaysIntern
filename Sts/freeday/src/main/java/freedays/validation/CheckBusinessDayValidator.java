package freedays.validation;

import java.util.Calendar;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import freedays.validation.annotation.BusinessDay;

public class CheckBusinessDayValidator implements ConstraintValidator<BusinessDay, Calendar>{

	@Override
	public void initialize(BusinessDay constraintAnnotation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(Calendar value, ConstraintValidatorContext context) {
		int dayOfWeek = value.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek != Calendar.SATURDAY
				&& dayOfWeek != Calendar.SUNDAY;
	}


}
