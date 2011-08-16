package freedays.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import freedays.validation.CheckChangePassValidator;
import freedays.validation.CheckSamePassValidator;

/**
 * Validator for the change pass form. It verifies weather
 * the old pass matches the existing pass and the other
 * two match.
 * @author fmacicasan
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckChangePassValidator.class)
@Documented
public @interface ValidChangePass {
    /**
	 * Specifies the error message in case of invalidity.
	 * @return
	 */
	String message() default "change pass not valid pass missmatch or not equal!";
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