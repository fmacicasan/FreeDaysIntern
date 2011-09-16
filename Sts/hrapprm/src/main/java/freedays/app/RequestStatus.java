package freedays.app;

import java.util.ArrayList;
import java.util.List;

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
    
    /**
     * Retrieves the next corresponding next status
     * @return
     */
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
    /**
     * Retrieves the cancelation status
     * @return
     */
    public static RequestStatus getCanceled(){
    	RequestStatus[] vals = RequestStatus.values();
    	return vals[vals.length-3];
    }
    /**
     * Retrieves the granted status
     * @return
     */
    public static RequestStatus getGranted(){
    	RequestStatus[] vals = RequestStatus.values();
    	return vals[vals.length-2];
    }
    /**
     * Retrieves the denial status
     * @return
     */
    public static RequestStatus getDenied(){
    	RequestStatus[] vals = RequestStatus.values();
    	return vals[vals.length-1];
    }
    /**
     * Retrieves the initial status
     * @param status
     * @return
     */
	public static boolean isInit(RequestStatus status) {
		return RequestStatus.getInit().equals(status);
	}
	
	/**
	 * Retreives the initial status
	 * @return
	 */
	 public static RequestStatus getInit(){
	    	return RequestStatus.values()[0];
	    }
	 
	 /**
	  * Retrieves the posible finalization status.
	  */
	 public static List<RequestStatus> getPossibleFinalStatusList(){
		 List<RequestStatus> lst = new ArrayList<RequestStatus>();
		 lst.add(RequestStatus.getCanceled());
		 lst.add(RequestStatus.getDenied());
		 lst.add(RequestStatus.getGranted());
		 return lst;
	 }
	 
	 public static List<RequestStatus> getPossibleActiveStatusList(){
		 List<RequestStatus> lst = new ArrayList<RequestStatus>();
		 RequestStatus[] all = RequestStatus.values();
		 for(int i=0;i<all.length-RequestStatus.SPECIAL_FINAL_STATES_COUNT;i++){
			 lst.add(all[i]);
		 }
		 return lst;
	 }
    
}
