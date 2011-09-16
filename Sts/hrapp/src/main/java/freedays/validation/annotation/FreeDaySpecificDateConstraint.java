package freedays.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import freedays.validation.CheckFreeDaySpecificDateConstraint;
/**
 * Annotation for Free Day specific validation of corresponding date.
 * @author fmacicasan
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckFreeDaySpecificDateConstraint.class)
@Documented
public @interface FreeDaySpecificDateConstraint {
	/**
	 * Specifies the error message in case of invalidity.
	 * @return
	 */
	String message() default "FreeDay Specific date constrain failed!";
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
