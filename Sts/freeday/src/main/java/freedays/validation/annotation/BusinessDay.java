/**
 * 
 */
package freedays.validation.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

import freedays.validation.CheckBusinessDayValidator;

/**
 * @author fmacicasan
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckBusinessDayValidator.class)
@Documented
public @interface BusinessDay {
	String message() default "Date must represent a business day";
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
