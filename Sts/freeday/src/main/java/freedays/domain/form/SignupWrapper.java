package freedays.domain.form;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.validation.annotation.SamePass;
import freedays.validation.annotation.UniqueEmail;


/**
 * Wrapper class backing the signup form.
 * @author fmacicasan
 *
 */
@SamePass
@RooJavaBean
public class SignupWrapper {

	@NotNull
	@Column(unique = true)
	// TODO check what's going on
	@Length(min = 3, max = 45, message = "#{messages['field_invalid_length']}")
	private String username;

	@NotNull
	@Length(min = 6, max = 45)
	private String password;
	
	@NotNull
	@Length(min = 6, max = 45)
	private String repeatpassword;

	@NotNull
	@Email(message = "#{messages['field_invalid_email']}")
	@UniqueEmail
	private String email;

	@NotNull
	private String surename;

	@NotNull
	private String firstname;
}
