package freedays.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import freedays.app.FDUser;
import freedays.app.FreeDay;
import freedays.app.RequestStatus;
import freedays.app.form.FreeDayRequest;
import freedays.security.UserContextService;
import freedays.util.MailUtils;
import freedays.util.PropertiesUtil;

/**
 * Class describing a request. It is associated with a Application Reuglar user, a Requestable object and
 * an approver. It;s status is described by the request status.
 * @author fmacicasan
 *
 */
@RooJavaBean
@RooToString
@RooEntity
public class Request implements Serializable {

    private static final String FD_APPROVAL_REQ_SUBJECT = "HRApp - Approval Request";

    private static final String FD_INFORM_SUBJECT = "HRApp - Status Information";

    private static final String FD_APPROVAL_REQ_CONTENT = "Hello! you have new Request to approve!!";

    private static final String FD_INFORM_CONTENT_DENY = "Your Request was denied!";

    private static final String FD_INFORM_CONTENT_APPROVE = "Your Request was approved!";

    private static final String FD_INFORM_CONTENT_CANCEL = "Request canceled!";

    private static final String FD_INFORM_CONTENT_AUTODENY = "Your Request was automatically denied!";

    public static boolean DEBUG = false;

    @ManyToOne
    private ApplicationRegularUser appreguser;

    @OneToOne
    private FreeDay requestable;

    @Enumerated
    private RequestStatus status;

    @ManyToOne
    private ApplicationRegularUser approver;

    private String feedback;
    
    @Autowired
	private transient UserContextService userContextService;

    /**
     * Initialization of a newly created request. It represents
     * the starting point of the approval process.
     */
    public void init() {
        if (RequestStatus.isInit(this.status)) {
            advanceApprover();
            if (this.approver != null) {
                requestApproval();
                informSuperiorInit();
            } else {
                setApproveStatus();
                informApproveRequest();
            }
        }
    }

    /**
     * Approve the current request.
     */
    public void approve() {
        if (canAdvance()) {
            advanceStatus();
            requestApproval();
        } else {
            setApproveStatus();
            informApproveRequest();
        }
    }
    
    public void superApprove(){
    	setApproveStatus();
    	informApproveRequest();
    	informSuperApproved();
    	captureApprover();
    }
    
    public void superDeny(){
    	setDenyStatus();
    	informDenyRequest();
    	informSuperDenied();
    	captureApprover();
    }

    /**
     * Denies the current request.
     */
    public void deny() {
        setDenyStatus();
        informDenyRequest();
    }

    /**
     * Automatically denies a request if it's associated
     * requestable object passed it's intended date
     */
    public void autoDeny() {
        setDenyStatus();
        informAutoDenyRequest();
    }

    /**
     * Cancels the current request.
     */
    public void cancel() {
        setCancelStatus();
        informCancelRequest();
    }

    /**
     * Sets the request's deny status.
     * Also the request object's final fail status.
     */
    private void setDenyStatus() {
        this.status = RequestStatus.getDenied();
        requestable.setFinalFailStatus();
    }

    /**
     * Sets the request's approve status.
     * Also the requestobject's final approve status.
     */
    private void setApproveStatus() {
        this.status = RequestStatus.getGranted();
        requestable.setFinalApproveStatus();
    }

    /**
     * Sets the request's cancel status.
     * Also the request object's final fail status.
     */
    private void setCancelStatus() {
        this.status = RequestStatus.getCanceled();
        requestable.setFinalFailStatus();
    }

    /**
     * Modifies the request's status for intermediate states.
     */
    private void advanceStatus() {
        this.status = this.status.getNext();
    }

    /**
     * Verifies weather or not the request chain can advance.
     * @return false if the chain stops
     */
    private boolean canAdvance() {
        if (advanceApproval()) {
            ApplicationRegularUser oldApprover = this.approver;
            advanceApprover();
            return this.approver != null && !this.approver.isSame(oldApprover);
        }
        return false;
    }

    /**
     * Advance the approval in the Approval Chain
     * @return
     */
    private boolean advanceApproval() {
        return this.requestable.nextApproval();
    }

    /**
     * Refresh the approver based on the approval strategy.
     */
    private void advanceApprover() {
        this.approver = this.requestable.getApprover(this.appreguser);
    }
    
    /**
     * Sets the approver that initiated an action that brought the request in a final state.
     */
    private void captureApprover(){
    	this.approver = ApplicationRegularUser.findByUsername(this.userContextService.getCurrentUser());
    }

    /**
     * Request approval via e-mail 
     */
    private void requestApproval() {
        if (!Request.DEBUG) {
            String mailTo = this.approver.getRegularUser().getEmail();
            String content = this.prepareContent(Request.FD_APPROVAL_REQ_CONTENT, this.toString());
            MailUtils.send(mailTo, Request.FD_APPROVAL_REQ_SUBJECT, content);
        }
    }

    private void informSuperiorInit() {
        if (!Request.DEBUG) {
            RegularUser superapprover = getSuperiorRegularUser();
            if (!this.getApprover().getRegularUser().equals(superapprover)) {
                MailUtils.sendUpperRequestNotification(superapprover.getEmail(), superapprover.getFullName(), this.getApprover().getRegularUser().getFullName(), this.toString());
            }
        }
    }

    private void informSuperiorDeny() {
        if (!Request.DEBUG) {
            RegularUser superapprover = getSuperiorRegularUser();
            if (!this.getApprover().getRegularUser().equals(superapprover)) {
                MailUtils.sendUpperRequestDenyNotification(superapprover.getEmail(), superapprover.getFullName(), this.getApprover().getRegularUser().getFullName(), this.toString());
            }
        }
    }

    private void informSuperiorCancel() {
        if (!Request.DEBUG) {
            RegularUser superapprover = getSuperiorRegularUser();
            if (!this.getApprover().getRegularUser().equals(superapprover)) {
                MailUtils.sendUpperRequestCancelNotification(superapprover.getEmail(), superapprover.getFullName(), this.toString());
            }
        }
    }
    
    private void informSuperApproved(){
    	this.informOnSuperAction("approved");
    }
    private void informSuperDenied(){
    	this.informOnSuperAction("denied");
    }
    
    private void informOnSuperAction(String outcome){
    	if(!Request.DEBUG){
    		RegularUser actualapprover = this.approver.getRegularUser();
    		RegularUser superapprover = this.getUltimateRegularUser();
    		MailUtils.send2LowerOnSuperApproveNotification(actualapprover.getEmail(), actualapprover.toString(), superapprover.toString(), outcome, this.toString());
    	}
    }

    private RegularUser getSuperiorRegularUser() {
        return this.getRequestable().getNextApprover(this.getAppreguser()).getRegularUser();
    }
    
    private RegularUser getUltimateRegularUser(){
    	RegularUser ru = this.getRequestable().getUltimateApprover(this.getAppreguser()).getRegularUser();
    	System.out.println(ru);
    	return ru;
    }

    private String prepareContent(String top, String bottom) {
        StringBuilder sb = new StringBuilder();
        sb.append(top).append("\n");
        sb.append(bottom).append("\n\n\n");
        return sb.toString();
    }

    /**
     * Constructs an approval message to be sent via email
     */
    private void informApproveRequest() {
        if (!Request.DEBUG) {
            this.informRequest(Request.FD_INFORM_CONTENT_APPROVE);
        }
    }

    /**
     * Constructs an denial message to be sent via email
     */
    private void informDenyRequest() {
        if (!Request.DEBUG) {
            this.informRequest(Request.FD_INFORM_CONTENT_DENY);
            this.informSuperiorDeny();
        }
    }

    /**
     * Constructs an automatic denial message to be sent via email
     */
    private void informAutoDenyRequest() {
        if (!Request.DEBUG) {
            this.informRequest(Request.FD_INFORM_CONTENT_AUTODENY);
        }
    }

    /**
     * Constructs an cancel message to be sent via email
     */
    private void informCancelRequest() {
        if (!Request.DEBUG) {
            this.informRequest(Request.FD_INFORM_CONTENT_CANCEL);
            this.informSuperiorCancel();
        }
    }

    /**
     * Sends a message via email.
     */
    private void informRequest(String msg) {
        String content = this.prepareContent(msg, this.toString());
        String mailTo = this.appreguser.getRegularUser().getEmail();
        MailUtils.send(mailTo, Request.FD_INFORM_SUBJECT, content);
    }

    /**
     * Textual representation of a request.
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Requester: ").append(this.appreguser.getRegularUser()).append("->");
        sb.append(this.requestable).append("\n");
        sb.append(" with status ").append(this.status).append("\n");
        return sb.toString().toUpperCase();
    }
    
    /**
	 * Verifies weather or not the FDUser associated with the regular user identified
	 * by the provided username is the owner of this
	 * @param name
	 * @return
	 */
    public boolean isOwner(String name) {
        if (name == null || name.length() == 0) throw new IllegalArgumentException("The username argument is required");
        return this.getAppreguser().getRegularUser().getUsername().equals(name);
    }

    /**
	 * Verifies weather or not the FDUser associated with the regular user identified
	 * by the provided username is the current approver of this
	 * @param name
	 * @return
	 */
    public boolean isApprover(String name) {
        try {
            if (name == null || name.length() == 0) throw new IllegalArgumentException("The username argument is required");
            return this.getApprover().getRegularUser().getUsername().equals(name);
        } catch (NullPointerException e) {
            return false;
        }
    }
    
    public boolean isUltimateApprover(String name) {
    	 try {
             if (name == null || name.length() == 0) throw new IllegalArgumentException("The username argument is required");
             //will return true for the actual ultimate approver
             String username =  this.getUltimateRegularUser().getUsername();
             return username.equals(name);
         } catch (NullPointerException e) {
             return false;
         }
	}
    
    public boolean isSuperiorApprover(String name){
    	try{
    		if(name==null || name.length() == 0) throw new IllegalArgumentException("The username argument is mandatory");
    		String username = this.getSuperiorRegularUser().getUsername();
    		//will return true for the actual superior approver
    		return username.equals(name);// && !this.getAppreguser().getRegularUser().getUsername().equals(username);
    	} catch (NullPointerException e){
    		return false;
    	}
    }

    /**
	 * Verifies weather or not this can be canceled.
	 * @return
	 */
    public boolean isCancelable() {
        return this.status != RequestStatus.CANCELED && this.status != RequestStatus.REJECTED && this.requestable.isCancelable();
    }

    /**
	 * Verifies weather or not the request can be approved
	 * @return
	 */
    public boolean isApprovalble() {
        return RequestStatus.getPossibleActiveStatusList().contains(this.getStatus());
    }

    /**
     * Counts the number of requests of the given fduser with the given status.
     * @param fdUser
     * @param status
     * @return
     */
    public static Long countRequests(ApplicationRegularUser fdUser, RequestStatus status) {
        if (fdUser == null) throw new IllegalArgumentException("The fdUser argument is required");
        EntityManager em = Request.entityManager();
        TypedQuery<Long> q = em.createQuery("SELECT COUNT(o) FROM Request AS o WHERE o.appreguser.regularUser.username = :fduser AND status = :status", Long.class);
        q.setParameter("fduser", fdUser.getRegularUser().getUsername());
        q.setParameter("status", status);
        Long res;
        try {
            res = q.getSingleResult();
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return res;
    }

    /**
	 * Counts all the requests associated with a FDUser
	 * @param fdUser
	 * @return
	 */
    public static Long countAllRequests(ApplicationRegularUser fdUser) {
        if (fdUser == null) throw new IllegalArgumentException("The fdUser argument is required");
        EntityManager em = Request.entityManager();
        TypedQuery<Long> q = em.createQuery("SELECT COUNT(o) FROM Request AS o WHERE o.appreguser.regularUser.username = :fduser", Long.class);
        q.setParameter("fduser", fdUser.getRegularUser().getUsername());
        Long res;
        try {
            res = q.getSingleResult();
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return res;
    }

    /**
	 * Creates a persistent request based on the request form wrapper. It behaves
	 * polimorphically for all the currently supported request types with their
	 * associated request form wrappers.
	 * @param fdr
	 * @param username
	 * @return
	 */
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

    /**
	 * Retrieves all the requests of a regular user identified based on his username.
	 * @param username
	 * @return
	 */
    public static List<Request> findAllRequestsByUsername(String username) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        EntityManager em = RegularUser.entityManager();
        TypedQuery<Request> q = em.createQuery("SELECT o FROM Request AS o WHERE o.appreguser.regularUser.username = :username ", Request.class);
        q.setParameter("username", username);
        return q.getResultList();
    }

    /**
	 * Retrieves all the requests of a regular user identified by its username
	 * that are under the approval mechanism.
	 * @param username
	 * @return
	 */
    public static List<Request> findAllPendingApprovalsByUsername(String username) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        EntityManager em = Request.entityManager();
        TypedQuery<Request> q = em.createQuery("SELECT o FROM Request AS o WHERE o.approver.regularUser.username = :username and o.status NOT IN :finishstates ", Request.class);
        q.setParameter("username", username);
        q.setParameter("finishstates", RequestStatus.getPossibleFinalStatusList());
        return q.getResultList();
    }
    


    /**
	 * Counts all the requests of a regular user identified by its username
	 * that are under the approval mechanism.
	 * @param username
	 * @return
	 */
    public static long countActiveRequests(String username) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        FDUser aru = FDUser.findFDUserByUsername(username);
        long all = Request.countAllRequests(aru);
        long granted = Request.countRequests(aru, RequestStatus.GRANTED);
        long rejected = Request.countRequests(aru, RequestStatus.REJECTED);
        long canceled = Request.countRequests(aru, RequestStatus.CANCELED);
        return all - granted - rejected - canceled;
    }

    /**
	 * Approves a requests identified based on its identifier.
	 * @param id2
	 */
	public static void approve(Long id2) {
		if (id2 == null) throw new IllegalArgumentException("The id argument is required");
		Request req = Request.findRequest(id2);
		req.approve();
		req.persist();
	}
    
    /**
     * The Top approver approves a request itentified based on its identifier
     * @param id
     */
    public static void superApprove(Long id){
    	Request req = Request.findRequest(id);
    	req.superApprove();
    	req.persist();
    }
    
    /**
     * The Top approver denies a request identified based on its identifier
     * @param id
     */
    public static void superDeny(Long id){
    	Request req = Request.findRequest(id);
    	req.superDeny();
    	req.persist();
    }

    /**
	 * Denies a requests identified based on its identifier.
	 * @param id2
	 */
    public static void deny(Long id2) {
        if (id2 == null) throw new IllegalArgumentException("The id argument is required");
        Request req = Request.findRequest(id2);
        req.deny();
        req.persist();
    }

    /**
	 * Cancels a requests identified based on its identifier.
	 * @param id2
	 */
    public static void cancel(Long id2) {
        if (id2 == null) throw new IllegalArgumentException("The id argument is required");
        Request req = Request.findRequest(id2);
        req.cancel();
        req.persist();
    }

    

    /**
	 * Calculates the available legal days requests that a FDUser associated
	 * with a regular user identified by the provided username can still make.
	 * @param username
	 * @return
	 */
    public static long computeAvailableFreeDays(String username) {
        FDUser aru = FDUser.findFDUserByUsername(username);
        return aru.computeAvailableFreeDays();
    }

    /**
	 * Computes the remainnig derogation of the FDUser associated with
	 * the regular user identified by the provided username
	 * @param username
	 * @return
	 */
    public static long computeAvailableDerogations(String username) {
        FDUser aru = FDUser.findFDUserByUsername(username);
        return aru.computeAvailableDerogations();
    }

    /**
	 * Retrieves all the requests of a regular user identified by its username
	 * that are under the approval mechanism.
	 * @param username
	 * @return
	 */
    public static List<Request> findAllPendingApprovals() {
        EntityManager em = Request.entityManager();
        TypedQuery<Request> q = em.createQuery("SELECT o FROM Request AS o WHERE o.status NOT IN :finishstates ", Request.class);
        q.setParameter("finishstates", RequestStatus.getPossibleFinalStatusList());
        return q.getResultList();
    }

    /**
	 * Computes the total number of days a FDUser identified by username can still have.
	 * @param username
	 * @return
	 */
    public static long computeTotalAvailableFreeDays(String username) {
        FDUser aru = FDUser.findFDUserByUsername(username);
        return aru.computeteAvailableFreeDaysTotal();
    }

    public static void handleFeedback(Long id2, String feedback2) {
        if (id2 == null) throw new IllegalArgumentException("The id argument is required");
        if (feedback2 == null || feedback2.length() == 0) throw new IllegalArgumentException("The username argument is required");
        if (!feedback2.equals(PropertiesUtil.getProperty("fdays.request.feedback.default"))) {
            Request req = Request.findRequest(id2);
            req.setFeedback(feedback2);
            req.persist();
        }
    }
    
	public static List<Request> findAllPendingSuperApprovalsByUsername(String username) {
		if(username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
		ApplicationRegularUser aru = ApplicationRegularUser.findByUsername(username);
		if(aru.isSuperUser()){
			//retrieve its granters and their requests
			//should see the requests off all his subordinates
			Set<ApplicationRegularUser> saru = ApplicationRegularUser.findAllSubordinatesTree(aru);
			Set<String> sarustring = ApplicationRegularUser.findAllSubordinatesTreeUsernameString(saru);
			if(!sarustring.isEmpty()){
				TypedQuery<Request> q = entityManager().createQuery("SELECT o FROM Request AS o WHERE o.approver.regularUser.username IN :username and o.status IN :finishstates ", Request.class);
				q.setParameter("username", sarustring);
				q.setParameter("finishstates", RequestStatus.getPossibleActiveStatusList());
				return q.getResultList();
			}
			return null;
		}
		throw new IllegalArgumentException("The username is not applicable!");
	}
	




	
}
