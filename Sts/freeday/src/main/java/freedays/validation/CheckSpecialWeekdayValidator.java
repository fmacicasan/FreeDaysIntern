package freedays.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import freedays.app.form.FreeDayRequest;
import freedays.app.form.FreeDayRequest.RequestType;
import freedays.util.ValidationUtils;
import freedays.validation.annotation.SpecialWeekday;

/**
 * Validator used by {@link freedays.validation.annotation.SpecialWeekday SpecialWeekday} annotation.
 * @author fmacicasan
 *
 */
public class CheckSpecialWeekdayValidator implements ConstraintValidator<SpecialWeekday, FreeDayRequest> {

	@Override
	public void initialize(SpecialWeekday constraintAnnotation) {
		// nothing here
		
	}

	@Override
	public boolean isValid(FreeDayRequest value,
			ConstraintValidatorContext context) {
		RequestType fds = value.getReqtype();
		if(fds == RequestType.R){
			return ValidationUtils.checkWeekend(value.getReqdate());
		}//TODO: verify also the other possible values of RequestType
		return ValidationUtils.checkBusinessDay(value.getReqdate());
	}

}
