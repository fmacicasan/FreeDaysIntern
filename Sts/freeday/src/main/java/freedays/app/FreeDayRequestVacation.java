package freedays.app;

import java.util.Calendar;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.app.FreeDayVacation.ConfidenceLevel;
import freedays.validation.annotation.BusinessDay;
import freedays.validation.annotation.UniqueVacationPerActiveOrApprovedReq;

@RooJavaBean
@UniqueVacationPerActiveOrApprovedReq
public class FreeDayRequestVacation extends FreeDayRequest {

	@NotNull
    @Future(message="date must be in future!")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "S-")
	@BusinessDay
    private Calendar finish;
	
	private ConfidenceLevel confidence;
	 
	 
}
