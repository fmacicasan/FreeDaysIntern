package freedays.app;

/**
 * Describe the possible status values form a Request
 * 	- GRANTED/REJECTED status should be on position n-1/n
 *  - ideal situation = one status for each granter involved in the approval strategy
 * 
 * @author fmacicasan
 *
 */
public enum RequestStatus {

    PENDING, 
    INTERMEDIATE, 
    GRANTED, 
    REJECTED;
    
    public RequestStatus getNext(){
    	RequestStatus[] vals = RequestStatus.values();
    	int ordinal = this.ordinal();
    	return (ordinal+3<vals.length)?vals[ordinal+1]:vals[ordinal];
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
