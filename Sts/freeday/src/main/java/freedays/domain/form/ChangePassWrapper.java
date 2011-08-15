package freedays.domain.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.validation.annotation.ValidChangePass;

@ValidChangePass
@RooJavaBean
public class ChangePassWrapper {

	@NotNull
	@Length(min = 6, max = 45)
	private String oldpassword;
	
	@NotNull
	@Length(min = 6, max = 45)
	private String password;
	
	@NotNull
	@Length(min = 6, max = 45)
	private String repeatpassword;
}
