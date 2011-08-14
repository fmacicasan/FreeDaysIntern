package freedays.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import freedays.validation.CheckSamePassValidator;
import freedays.validation.CheckUniqueEmailValidator;

/**
 * 
 * @author fmacicasan
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckSamePassValidator.class)
@Documented
public @interface SamePass {
	String message() default "passwords dont match!";
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
