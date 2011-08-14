package freedays.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import freedays.validation.CheckUniqueEmailValidator;
/**
 * 
 * @author fmacicasan
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckUniqueEmailValidator.class)
@Documented
public @interface UniqueEmail {
	String message() default "email not valid!";
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
