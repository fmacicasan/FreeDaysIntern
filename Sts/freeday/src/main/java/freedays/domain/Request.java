package freedays.domain;

import java.util.Calendar;
import java.util.List;

import freedays.util.MailUtils;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import freedays.domain.ApplicationRegularUser;

import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import javax.persistence.TypedQuery;

import freedays.app.FDUser;
import freedays.app.FreeDay;
import javax.validation.constraints.NotNull;
import javax.persistence.OneToOne;
import freedays.app.RequestStatus;
import javax.persistence.Enumerated;


@RooJavaBean
@RooToString
@RooEntity
public class Request {

	
	private static final String FD_APPROVAL_REQ_SUBJECT = "FreeDays - Approval Request";
	private static final String FD_INFORM_SUBJECT = "FreeDays - Status Information";
	
	private static final String FD_APPROVAL_REQ_CONTENT = "Hello! you have new Request to approve!!\n";
	private static final String FD_INFORM_CONTENT_DENY = "Your Request not approved!";
	private static final String FD_INFORM_CONTENT_APPROVE = "Your Request approved!";
    
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
    		requestApproval();
    	}
    }
    
    public void approve(){
    	if(advanceApproval()){
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
    
    private void setDenyStatus(){
    	this.status = this.status.setDenied();
    }
    private void setApproveStatus(){
    	this.status = this.status.setGranted();
    }
    private void advanceStatus(){
    	this.status = this.status.getNext();
    }
    
    private boolean advanceApproval(){
    	return this.requestable.nextApproval();
    }
    private void requestApproval(){
    	this.approver = this.requestable.getApprover(this.appreguser);
    	String mailTo = this.approver.getRegularUser().getEmail();
    	StringBuilder sb = new StringBuilder();
    	sb.append(Request.FD_APPROVAL_REQ_CONTENT);
    	sb.append(this.toString());
    	MailUtils.send(mailTo,Request.FD_APPROVAL_REQ_SUBJECT,sb.toString());
    }
    
    private void informApproveRequest(){
    	this.informRequest(Request.FD_INFORM_CONTENT_APPROVE);
    }
    private void informDenyRequest(){
    	this.informRequest(Request.FD_INFORM_CONTENT_DENY);
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

	public static long countRequests(ApplicationRegularUser fdUser, RequestStatus status) {
		if (fdUser == null) throw new IllegalArgumentException("The fdUser argument is required");
		EntityManager em = Request.entityManager();
        TypedQuery<Long> q = em.createQuery("SELECT COUNT(o) FROM Request AS o WHERE o.appreguser.regularUser.username = :fduser AND status = :status", Long.class);
        q.setParameter("fduser", fdUser.getRegularUser().getUsername());
        q.setParameter("status", status);
        return q.getSingleResult();	
	}
	
	public static void createPersistentReq(Calendar date, String username) {
		Request req = new Request();
		req.setStatus(RequestStatus.getInit());
		req.setAppreguser(FDUser.findFDUserByUsername(username));
		req.setRequestable(FreeDay.createPersistentFreeDay(date));
		System.out.println(req);
		req.init();
		req.persist();
		
		
	}

	public static List<Request> findAllRequestsByUsername(String username) {
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        EntityManager em = RegularUser.entityManager();
        TypedQuery<Request> q = em.createQuery("SELECT o FROM Request AS o WHERE o.appreguser.regularUser.username = :username ", Request.class);
        q.setParameter("username", username);
        return q.getResultList();
	}

	public static Object findAllPendingApprovalsByUsername(String username) {
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        EntityManager em = RegularUser.entityManager();
        TypedQuery<Request> q = em.createQuery("SELECT o FROM Request AS o WHERE o.approver.regularUser.username = :username ", Request.class);
        q.setParameter("username", username);
        return q.getResultList();
	}

	public static long countActiveRequests(FDUser aru) {
		return Request.findAllRequestsByUsername(aru.getRegularUser().getUsername()).size()
				- Request.countRequests(aru, RequestStatus.GRANTED)
				- Request.countRequests(aru, RequestStatus.REJECTED);
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
}
