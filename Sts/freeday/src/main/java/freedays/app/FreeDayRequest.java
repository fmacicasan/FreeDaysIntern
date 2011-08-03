package freedays.app;

import java.util.Calendar;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Future;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.validation.annotation.BusinessDay;
import freedays.validation.annotation.SpecialWeekday;

@RooJavaBean
@SpecialWeekday
public class FreeDayRequest {

    @NotNull
    @Future(message="date must be in future!")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "S-")
    private Calendar reqdate;

    private String reason;
    
    /**
     * Defines the possible FreeDay request.
     * 	<ul>
     * 		<li>TypeL -> Legal free day</li>
     * 		<li>TypeC -> Compensation free day received on request or for a previous TypeR day</li>
     * 		<li>TypeR -> Recovery day for a TypeC free day</li>
     *  </ul>
     * @author fmacicasan
     *
     */
    public enum RequestType {L,C,R};
    @NotNull
    @Enumerated
    private RequestType reqtype;
    
    private FreeDay match;
    
    public static FreeDayRequest generateReq(RequestType rt){
    	FreeDayRequest fdr = new FreeDayRequest();
    	fdr.setReqtype(rt);
    	return fdr;
    }
}
