package freedays.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import freedays.validation.CheckSamePassValidator;

/**
 * Custom validation for the Signum backing object. It restricts
 * the creation of an regular user to objects having the same
 * values for the passwords and a username not taken yet.
 * @author fmacicasan
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckSamePassValidator.class)
@Documented
public @interface SamePass {
	/**
	 * Specifies the error message in case of invalidity.
	 * @return
	 */
	String message() default "passwords dont match!";
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
