package freedays.validation;

import java.util.Calendar;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.app.FreeDay;
import freedays.app.form.FreeDayRequestVacation;
import freedays.security.UserContextService;
import freedays.util.ValidationUtils;
import freedays.validation.annotation.UniqueVacationPerActiveOrApprovedReq;
/**
 * 
 * @author fmacicasan
 * @see UniqueVacationPerActiveOrApprovedReq
 */
@RooJavaBean
public class CheckUniqueVacationPerActiveOrApprovedReqValidator implements ConstraintValidator<UniqueVacationPerActiveOrApprovedReq, FreeDayRequestVacation> {

	@Autowired
	private UserContextService userContextService;
	
	@Override
	public void initialize(UniqueVacationPerActiveOrApprovedReq arg0) {
		// nothing hereb
		
	}

	@Override
	public boolean isValid(FreeDayRequestVacation fdr, ConstraintValidatorContext arg1) {
		List<FreeDay> lfd = FreeDay.getAllNotFailedRequestsByUsername(userContextService.getCurrentUser());
		for (FreeDay freeDay : lfd) {
				Calendar start = fdr.getReqdate();
				if(freeDay.verifyUniqueness(start)) return false;
				Calendar finish = fdr.getFinish();
				if(freeDay.verifyUniqueness(finish)) return false;
				Calendar date = freeDay.getDate();
				if(start.before(date) && finish.after(date)) return false;
		}
		if(ValidationUtils.checkRomanianLegalHoliday(fdr.getReqdate()) || ValidationUtils.checkRomanianLegalHoliday(fdr.getFinish())){
			return false;
		}
		return true;
	}

}
