package freedays.app;

/**
 * Describe the possible status values form a Request
 * 	- CANCELED/GRANTED/REJECTED status should be on position n-2/n-1/n
 *  - ideal situation = one status for each granter involved in the approval strategy
 * 
 * @author fmacicasan
 *
 */
public enum RequestStatus {

    PENDING, 
    INTERMEDIATE,
   // MATCHED,
    //WAITING,
    CANCELED,
    GRANTED, 
    REJECTED;
    
    /**
     * Special States: Canceled, Granted and Rejected 
     */
    //<blockquote>and Waiting (typeC/R without matching typeR/C) and Matched</blackquote>
    private static final int SPECIAL_FINAL_STATES_COUNT = 3;
    
    public RequestStatus getNext(){
    	RequestStatus[] vals = RequestStatus.values();
    	int ordinal = this.ordinal();
    	return (ordinal+RequestStatus.SPECIAL_FINAL_STATES_COUNT<vals.length-1)?vals[ordinal+1]:vals[ordinal];
    }
//    public static RequestStatus getMatched(){
//    	RequestStatus[] vals = RequestStatus.values();
//    	return vals[vals.length-5];
//    }
//    public static RequestStatus getWaiting(){
//    	RequestStatus[] vals = RequestStatus.values();
//    	return vals[vals.length-4];
//    }
    public static RequestStatus getCanceled(){
    	RequestStatus[] vals = RequestStatus.values();
    	return vals[vals.length-3];
    }
    public static RequestStatus getGranted(){
    	RequestStatus[] vals = RequestStatus.values();
    	return vals[vals.length-2];
    }
    public static RequestStatus getDenied(){
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
