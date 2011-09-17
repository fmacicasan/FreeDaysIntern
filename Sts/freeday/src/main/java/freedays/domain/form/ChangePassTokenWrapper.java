package freedays.domain.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.validation.annotation.SameTokenPass;

@RooJavaBean
@SameTokenPass
public class ChangePassTokenWrapper {
	
	//private String token;
	
	@NotNull
	@Length(min = 6, max = 45)
	private String password;
	
	@NotNull
	@Length(min = 6, max = 45)
	private String repeatpassword;
}
