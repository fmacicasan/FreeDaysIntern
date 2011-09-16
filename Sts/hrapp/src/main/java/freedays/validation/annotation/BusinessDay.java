/**
 * 
 */
package freedays.validation.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import freedays.validation.CheckBusinessDayValidator;

/**
 * Restricts a Calendar instance to days representing working days.
 * It won't validate any Calendar.SUNDAY or Calendar.SATURDAY obj.
 * @author fmacicasan
 *
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckBusinessDayValidator.class)
@Documented
public @interface BusinessDay {
	/**
	 * Specifies the error message in case of invalidity.
	 * @return
	 */
	String message() default "Date must represent a business day";
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
