package freedays.app;

import java.io.Serializable;

/**
 * Describe the possible status values form a Request
 * 	- CANCELED/GRANTED/REJECTED status should be on position n-2/n-1/n
 *  - ideal situation = one status for each granter involved in the approval strategy
 * 
 * @author fmacicasan
 *
 */
public enum RequestStatus  implements Serializable{

    PENDING, 
    INTERMEDIATE,
    CANCELED,
    GRANTED, 
    REJECTED;
    
    /**
     * Special States: Canceled, Granted and Rejected
     */
    private static final int SPECIAL_FINAL_STATES_COUNT = 3;
    
    public RequestStatus getNext(){
    	RequestStatus[] vals = RequestStatus.values();
    	int ordinal = this.ordinal();
    	return (ordinal+RequestStatus.SPECIAL_FINAL_STATES_COUNT<vals.length-1)?vals[ordinal+1]:vals[ordinal];
    }
    
    public RequestStatus setCanceled(){
    	RequestStatus[] vals = RequestStatus.values();
    	return vals[vals.length-3];
    }
    public RequestStatus setGranted(){
    	RequestStatus[] vals = RequestStatus.values();
    	return vals[vals.length-2];
    }
    public RequestStatus setDenied(){
    	RequestStatus[] vals = RequestStatus.values();
    	return vals[vals.length-1];
    }
	public static boolean isInit(RequestStatus status) {
		return RequestStatus.getInit().equals(status);
	}
	
	 public static RequestStatus getInit(){
	    	return RequestStatus.values()[0];
	    }
    
}
