package freedays.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import freedays.validation.CheckWeekendDayValidator;


/**
 * Restricts a calendar instance to dates representing weekends.
 * Will validate only Calendar.SUNDAY and Calendar.SATURDAY instances.
 * @author fmacicasan
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckWeekendDayValidator.class)
@Documented
public @interface Weekend {
	/**
	 * Specifies the error message in case of invalidity.
	 * @return
	 */
	String message() default "Date must be during weekends";
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
