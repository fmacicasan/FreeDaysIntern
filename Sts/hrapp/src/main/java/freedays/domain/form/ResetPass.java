package freedays.domain.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.roo.addon.javabean.RooJavaBean;


/**
 * Wrapper for a ResetPassword request.
 * @author fmacicasan
 *
 */
@RooJavaBean
public class ResetPass {

	@NotNull
	@NotEmpty
	private String email;
	
}
