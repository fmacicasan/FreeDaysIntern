package freedays.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

import freedays.domain.RegularUser;
import freedays.domain.form.ChangePassWrapper;
import freedays.security.UserContextService;
import freedays.validation.annotation.ValidChangePass;

/**
 * 
 * @author fmacicasan
 * @see ValidChangePass
 */
public class CheckChangePassValidator implements ConstraintValidator<ValidChangePass, ChangePassWrapper> {

	@Autowired
	private UserContextService userContextService;
	@Autowired
	private MessageDigestPasswordEncoder messageDigestPasswordEncoder;
	
	@Override
	public void initialize(ValidChangePass arg0) {
		// nthing here
		
	}

	@Override
	public boolean isValid(ChangePassWrapper cpw, ConstraintValidatorContext arg1) {
		//change null checks with exception catch
		String pass1 = cpw.getPassword();
		if(pass1==null) return false;
		String pass2 = cpw.getRepeatpassword();
		if(pass2==null) return false;
		if(!pass1.equals(pass2))return false;
		String username = this.userContextService.getCurrentUser();
		if(username == null)return false;
		RegularUser ru = RegularUser.findRegularUsersByUsername(username).getSingleResult();
		String oldPass = cpw.getOldpassword();
		if(oldPass == null) return false;
		String encodedpass = this.messageDigestPasswordEncoder.encodePassword(oldPass, null);
		return ru.getPassword().equals(encodedpass);
	}

}
