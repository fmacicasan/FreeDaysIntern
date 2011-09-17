package freedays.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.app.FreeDay;
import freedays.app.form.FreeDayRequest;
import freedays.security.UserContextService;
import freedays.util.ValidationUtils;
import freedays.validation.annotation.UniqueDayPerActiveOrApprovedReq;

/**
 * 
 * @author fmacicasan
 * @see UniqueDayPerActiveOrApprovedReq
 */
@RooJavaBean
public class CheckUniqueDayPerActiveOrApprovedReqValidator implements ConstraintValidator<UniqueDayPerActiveOrApprovedReq, FreeDayRequest>{

	@Autowired
	private UserContextService userContextService;
	
	@Override
	public void initialize(UniqueDayPerActiveOrApprovedReq arg0) {
		// nothing here
		
	}

	@Override
	public boolean isValid(FreeDayRequest fdr, ConstraintValidatorContext arg1) {
		List<FreeDay> lfd = FreeDay.getAllNotFailedRequestsByUsername(userContextService.getCurrentUser());
		for (FreeDay freeDay : lfd) {
				if(freeDay.verifyUniqueness(fdr.getReqdate())) {
					return false;
				}
		}
		if(ValidationUtils.checkRomanianLegalHoliday(fdr.getReqdate())){
			System.out.println("i am the master of my faith i am the keeper of soul");
			return false;
		}
		return true;
	}

}
