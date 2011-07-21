package freedays.domain;

import freedays.util.MailUtils;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import freedays.domain.ApplicationRegularUser;

import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import javax.persistence.TypedQuery;

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
    	ApplicationRegularUser nextApprover = this.requestable.getApprover(this.appreguser);
    	String mailTo = nextApprover.getRegularUser().getEmail();
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
    	return sb.toString();
    }

	public static long countGrantedRequests(ApplicationRegularUser fdUser, RequestStatus status) {
		if (fdUser == null) throw new IllegalArgumentException("The fdUser argument is required");
		EntityManager em = Request.entityManager();
        TypedQuery<Long> q = em.createQuery("SELECT COUNT(o) FROM Request AS o WHERE o.appreguser = :fduser AND status = :status", Long.class);
        q.setParameter("fduser", fdUser);
        q.setParameter("status", status);
        return q.getSingleResult();
		
	}
}
