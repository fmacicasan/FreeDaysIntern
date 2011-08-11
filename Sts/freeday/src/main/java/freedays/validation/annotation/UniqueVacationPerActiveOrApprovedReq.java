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
	String message() default "Vacation overlaps some active/approved req!";
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
