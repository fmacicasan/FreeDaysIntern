package freedays.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.app.form.FreeDayRequest;
import freedays.domain.RegularUser;
import freedays.domain.form.SignupWrapper;
import freedays.security.UserContextService;
import freedays.validation.annotation.UniqueDayPerActiveOrApprovedReq;
import freedays.validation.annotation.UniqueEmail;

@RooJavaBean
public class CheckUniqueEmailValidator  implements ConstraintValidator<UniqueEmail, String> {

	@Autowired
	private UserContextService userContextService;
	
	@Override
	public void initialize(UniqueEmail arg0) {
		// nothing here
		
	}

	@Override
	public boolean isValid(String email, ConstraintValidatorContext arg1) {
		if(email==null)return false;
		if(RegularUser.countRegularUserByEmail(email) != 0)//if there is some1, it should be the logged dude
		{
			String username = userContextService.getCurrentUser();
			if(username == null) return false;//if no1 is logged then can't user same email
			RegularUser ru = RegularUser.findRegularUsersByUsername(username).getSingleResult();
			return email.equals(ru.getEmail());//can use the same email as the logged user
		}
		return true;
	}

}
