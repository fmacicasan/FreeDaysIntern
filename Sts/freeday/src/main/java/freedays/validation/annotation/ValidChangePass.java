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
 * 
 * @author fmacicasan
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckChangePassValidator.class)
@Documented
public @interface ValidChangePass {
	String message() default "change pass not valid pass missmatch or not equal!";
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}