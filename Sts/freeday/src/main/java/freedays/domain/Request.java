package freedays.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import freedays.util.DAOUtils;
import freedays.util.MailUtils;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import freedays.domain.ApplicationRegularUser;

import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import javax.persistence.TypedQuery;

import freedays.app.AppStrategL1;
import freedays.app.FDUser;
import freedays.app.FreeDay;
import freedays.app.FreeDayL;
import freedays.app.FreeDayRequest;

import javax.validation.constraints.NotNull;
import javax.persistence.OneToOne;
import freedays.app.RequestStatus;
import javax.persistence.Enumerated;


@RooJavaBean
@RooToString
@RooEntity
public class Request   implements Serializable{

	
	private static final String FD_APPROVAL_REQ_SUBJECT = "FreeDays - Approval Request";
	private static final String FD_INFORM_SUBJECT = "FreeDays - Status Information";
	
	private static final String FD_APPROVAL_REQ_CONTENT = "Hello! you have new Request to approve!!\n";
	private static final String FD_INFORM_CONTENT_DENY = "Your Request not approved!";
	private static final String FD_INFORM_CONTENT_APPROVE = "Your Request approved!";
	private static final String FD_INFORM_CONTENT_CANCEL = "Request canceled!";
	
	public static boolean DEBUG = false;
    
	@ManyToOne
    private ApplicationRegularUser appreguser;

    @OneToOne
    private FreeDay requestable;

    @Enumerated
    private RequestStatus status;
    
    @ManyToOne
    private ApplicationRegularUser approver;

    public void init(){
    	if(RequestStatus.isInit(this.status)){
    		//ApplicationRegularUser oldApprover = this.approver;
    		advanceApprover();
    		if(this.approver != null){// && !this.approver.isSame(oldApprover)){
    			requestApproval();
    		} else {
    			setApproveStatus();
        		informApproveRequest();
    		}
    	}
    }
    
    public void approve(){
    	if(canAdvance()){
    		advanceStatus();
    		requestApproval(); 	
    	} else {
    		setApproveStatus();
    		informApproveRequest();
    	}
    	
    }
    
    public void deny(){
    	setDenyStatus();
    	informDenyRequest();
    }
      
    public void cancel(){
    	setCancelStatus();
    	informCancelRequest();
    }
    
    private void setDenyStatus(){
    	this.status = RequestStatus.getDenied();
    	requestable.setFinalFailStatus();
    }
    private void setApproveStatus(){
    	this.status = RequestStatus.getGranted();
    	requestable.setFinalApproveStatus();
    }
    private void setCancelStatus(){
    	this.status =RequestStatus.getCanceled();
    	requestable.setFinalFailStatus();
    }
    private void advanceStatus(){
    	this.status = this.status.getNext();
    }
    private boolean canAdvance(){
    	if(advanceApproval()){
    		ApplicationRegularUser oldApprover = this.approver;
    		advanceApprover();
    		//TODO: don't return false if the approver did not change, only stop the approval request
    		return this.approver != null && !this.approver.isSame(oldApprover); // check also if the approver changed, if not it is the same and shouldn't advance  
    	}
    	return false;
    	
    }
    private boolean advanceApproval(){
    	return this.requestable.nextApproval();
    }
    private void advanceApprover(){
    	this.approver = this.requestable.getApprover(this.appreguser);
    }
    private void requestApproval(){
    	
    	String mailTo = this.approver.getRegularUser().getEmail();
    	StringBuilder sb = new StringBuilder();
    	sb.append(Request.FD_APPROVAL_REQ_CONTENT);
    	sb.append(this.toString());
    	MailUtils.send(mailTo,Request.FD_APPROVAL_REQ_SUBJECT,sb.toString());
    }
    
    private void informApproveRequest(){
    	if(!Request.DEBUG){
    		this.informRequest(Request.FD_INFORM_CONTENT_APPROVE);
    	}
    		
    }
    private void informDenyRequest(){
    	if(!Request.DEBUG){
    		this.informRequest(Request.FD_INFORM_CONTENT_DENY);
    	}	
    }
    
    private void informCancelRequest(){
    	if(!Request.DEBUG){
    		this.informRequest(Request.FD_INFORM_CONTENT_CANCEL);
    	}
    }
    private void informRequest(String msg){
    	StringBuilder sb = new StringBuilder();
		sb.append(msg);
		sb.append(this.toString());
    	String mailTo = this.appreguser.getRegularUser().getEmail();
    	MailUtils.send(mailTo, Request.FD_INFORM_SUBJECT, sb.toString());
    }
    
    public String toString(){
    	StringBuilder sb = new StringBuilder();
    	sb.append(this.appreguser.getRegularUser());
    	sb.append("->");
    	sb.append(this.requestable);
    	sb.append(" with status ");
    	sb.append(this.status);
    	return sb.toString().toUpperCase();
    }

	public static Long countRequests(ApplicationRegularUser fdUser, RequestStatus status) {
		if (fdUser == null) throw new IllegalArgumentException("The fdUser argument is required");
		EntityManager em = Request.entityManager();
        TypedQuery<Long> q = em.createQuery("SELECT COUNT(o) FROM Request AS o WHERE o.appreguser.regularUser.username = :fduser AND status = :status", Long.class);
        q.setParameter("fduser", fdUser.getRegularUser().getUsername());
        q.setParameter("status", status);
        Long res;
		try{
			res=q.getSingleResult();
		}catch(EmptyResultDataAccessException e){
			return null;
		}
		return res;
	}
	
	public static Long countAllRequests(ApplicationRegularUser fdUser){
		if (fdUser == null) throw new IllegalArgumentException("The fdUser argument is required");
		EntityManager em = Request.entityManager();
        TypedQuery<Long> q = em.createQuery("SELECT COUNT(o) FROM Request AS o WHERE o.appreguser.regularUser.username = :fduser", Long.class);
        q.setParameter("fduser", fdUser.getRegularUser().getUsername());
        Long res;
		try{
			res=q.getSingleResult();
		}catch(EmptyResultDataAccessException e){
			return null;
		}
		return res;
	}
	
	public static Request createPersistentReq(FreeDayRequest fdr, String username) {
		Request req = new Request();
		req.setStatus(RequestStatus.getInit());
		req.setAppreguser(FDUser.findFDUserByUsername(username));
		req.setRequestable(FreeDay.createPersistentFreeDay(fdr));
		System.out.println(req);
		req.init();
		req.persist();
		return req;
	}
	
//	public static void createPersistentReq(FreeDayRequest fdr, String username){
//		Request.createPersistentReq(fdr.getReqdate(), fdr.getReason(), username);
//	}


	public static List<Request> findAllRequestsByUsername(String username) {
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        EntityManager em = RegularUser.entityManager();
        TypedQuery<Request> q = em.createQuery("SELECT o FROM Request AS o WHERE o.appreguser.regularUser.username = :username ", Request.class);
        q.setParameter("username", username);
        return q.getResultList();
	}

	public static List<Request> findAllPendingApprovalsByUsername(String username) {
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        EntityManager em = RegularUser.entityManager();
        TypedQuery<Request> q = em.createQuery("SELECT o FROM Request AS o WHERE o.approver.regularUser.username = :username ", Request.class);
        //TODO: change the searched status only for the ones that need approval
        q.setParameter("username", username);
        return q.getResultList();
	}

	public static long countActiveRequests(String username) {
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
		FDUser aru = FDUser.findFDUserByUsername(username);
		long all =  Request.countAllRequests(aru);
		long granted = Request.countRequests(aru, RequestStatus.GRANTED);
		long rejected = Request.countRequests(aru, RequestStatus.REJECTED);
		long canceled = Request.countRequests(aru, RequestStatus.CANCELED);
		return all - granted - rejected - canceled;
	}

	public static void approve(Long id2) {
		if (id2 == null) throw new IllegalArgumentException("The id argument is required");
		Request req = Request.findRequest(id2);
		req.approve();
		req.persist();
	}

	public static void deny(Long id2) {
		if (id2 == null) throw new IllegalArgumentException("The id argument is required");
		Request req = Request.findRequest(id2);
		req.deny();
		//TODO: who should call the persisting?? the request or the calling object?
		req.persist();
		
	}

	public static void cancel(Long id2) {
		if (id2 == null) throw new IllegalArgumentException("The id argument is required");
		Request req = Request.findRequest(id2);
		req.cancel();
		req.persist();
		
	}

	public boolean isOwner(String name) {
		if (name == null || name.length() == 0) throw new IllegalArgumentException("The username argument is required");
		return this.getAppreguser().getRegularUser().getUsername().equals(name);
	}

	public boolean isApprover(String name) {
		try{
			if (name == null || name.length() == 0) throw new IllegalArgumentException("The username argument is required");
			return this.getApprover().getRegularUser().getUsername().equals(name);
		}catch(NullPointerException e){
			return false;
		}
	}
	
	public  boolean isCancelable(){
		return this.status != RequestStatus.GRANTED
				&& this.status != RequestStatus.CANCELED
				&& this.status != RequestStatus.REJECTED
				&& this.requestable.isCancelable();
		
	}
	
	public static long computeAvailableFreeDays(String username) {
		FDUser aru = FDUser.findFDUserByUsername(username);
		return aru.computeAvailableFreeDays();
	}


}
