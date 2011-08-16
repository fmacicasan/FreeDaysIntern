package freedays.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import freedays.validation.CheckSpecialWeekdayValidator;
import freedays.validation.CheckUniqueDayPerActiveOrApprovedReqValidator;
import freedays.validation.CheckUniqueVacationPerActiveOrApprovedReqValidator;

/**
 * 
 * @author fmacicasan
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckUniqueVacationPerActiveOrApprovedReqValidator.class)
@Documented
public @interface UniqueVacationPerActiveOrApprovedReq {
	/**
	 * Specifies the error message in case of invalidity.
	 * @return
	 */
	String message() default "Vacation overlaps some active/approved req!";
	/**
	 * Defines a group 
	 * @return
	 */
	Class<?>[] groups() default {};
	
	/**
	 * Specifies the severity of the constraint.
	 * @return
	 */
    Class<? extends Payload>[] payload() default {};
}
