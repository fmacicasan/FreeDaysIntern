package freedays.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import freedays.validation.CheckSamePassValidator;
import freedays.validation.CheckSameTokenPassValidator;

/**
 * Custom validation for the Token change pass backing object. It restricts
 * the token password change to objects having the same
 * values for the passwords
 * @author fmacicasan
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckSameTokenPassValidator.class)
@Documented
public @interface SameTokenPass {
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
