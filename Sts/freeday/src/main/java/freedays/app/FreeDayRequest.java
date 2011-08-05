package freedays.app;

import java.util.Calendar;
import java.util.List;

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
    public enum RequestType {L,C,R,V};
    @NotNull
    @Enumerated
    private RequestType reqtype;
    
    private FreeDaysRCMatch match;
    
    public static FreeDayRequest generateReqFactory(RequestType rt){
    	FreeDayRequest fdr;
    	switch(rt){
	    	case L:
	    	case C:
	    	case R:
	    		fdr = new FreeDayRequest();
	    		break;
	    	case V:
	    		fdr = new FreeDayRequestVacation();
	    		break;
	    	default:
	    		throw new IllegalArgumentException("The request type is not supported!");	
    	}
    	fdr.setReqtype(rt);
    	return fdr;
    }
    
//    @SuppressWarnings("unchecked")
//	public static <T extends FreeDay> List<T> getMatchings(String username, RequestType rt){
//	    		//TODO: catch the ClassCastException if any.
//	    		switch(rt){
//		    		case R:
//		    			return FreeDayR.getAllUnmatchedRequestsByUsername(username);
//		    			
//		    		case C:
//		    			return (List<T>) FreeDayC.getAllUnmatchedRequestsByUsername(username);
//		    		default:
//		    			//throw new IllegalArgumentException("The RequestType argument is invalid: should be R or C");
//		    			return null;
//	    		}
//    		
//    }
}
