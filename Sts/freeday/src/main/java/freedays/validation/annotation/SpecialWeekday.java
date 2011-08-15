package freedays.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import freedays.validation.CheckSpecialWeekdayValidator;

/**
 * Custom validator for {@link freedays.app.form.FreeDayRequest FreeDayRequest}.
 * It restricts it's Calendar member based on the request type.
 * <ul>
 * 		<li>{@link freedays.app.form.FreeDayRequest.RequestType TypeL} -> {@link freedays.validation.annotation.BusinessDay BusinessDay}</li>
 * 		<li>{@link freedays.app.form.FreeDayRequest.RequestType TypeC} -> {@link freedays.validation.annotation.BusinessDay BusinessDay}</li>
 * 		<li>{@link freedays.app.form.FreeDayRequest.RequestType TypeR} -> {@link freedays.validation.annotation.Weekend Weekend}</li> 
 * </ul>
 * @author fmacicasan
 * @see freedays.app.form.FreeDayRequest.RequestType
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckSpecialWeekdayValidator.class)
@Documented
public @interface SpecialWeekday {
	String message() default "Specialized date constrain!";
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
