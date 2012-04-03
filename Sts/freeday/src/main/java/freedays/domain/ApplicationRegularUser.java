package freedays.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.transaction.annotation.Transactional;

import freedays.app.HRManagement;


/**
 * Abstract class describing an general application user.
 * @author fmacicasan
 *
 */
@RooJavaBean
@RooEntity(inheritanceType = "TABLE_PER_CLASS")
public abstract class ApplicationRegularUser   implements Serializable {


	/**
	 * 
	 */
	protected static final long serialVersionUID = 1L;

	@ManyToOne
    private RegularUser regularUser;

    @ManyToMany(cascade = CascadeType.ALL)//, fetch=FetchType.EAGER 
    @JoinTable(name = "AppRegUser_AdvRole", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<AdvancedUserRole> roles = new HashSet<AdvancedUserRole>();

    @ManyToOne(fetch=FetchType.EAGER)
    private ApplicationRegularUser granter;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appreguser")
    private Set<Request> requests = new HashSet<Request>();
    
    public enum JobRole{DEV, QA, SDET, PM, IT, EM, PO, SE, PS, OBS}
    @Enumerated
    private JobRole jobrole;
    
	@PersistenceContext
	transient EntityManager entityManager;
   
	/**
	 * retrieves all the roles of a regular user identified based on his username.
	 * @param username
	 * @return
	 */
    //@Transactional
	public static Set<AdvancedUserRole> getAllRolesByUsername(String username) {
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
		Set<AdvancedUserRole> set=new HashSet<AdvancedUserRole>();
			EntityManager em = ApplicationRegularUser.entityManager();
	        TypedQuery<ApplicationRegularUser> q = em.createQuery("SELECT o FROM ApplicationRegularUser AS o JOIN FETCH o.roles WHERE o.regularUser.username = :username", ApplicationRegularUser.class);
	        q.setParameter("username", username);
	        ApplicationRegularUser aru;
	        try{
	         aru= q.getSingleResult();
	        }catch(EmptyResultDataAccessException e){
				return set;
			}
	        set = aru.getRoles();
	    return set;
	}
	
	public static ApplicationRegularUser findByUsername(String username){
		if(username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
		EntityManager em = ApplicationRegularUser.entityManager();
		TypedQuery<ApplicationRegularUser> q = em.createQuery("SELECT o FROM ApplicationRegularUser AS o WHERE o.regularUser.username = :username",ApplicationRegularUser.class);
		q.setParameter("username",username);
		ApplicationRegularUser aru = q.getSingleResult();
		return aru;
	}

	/**
	 * Finds all the application regular users having the request granting role.
	 * @return
	 */
	public static Collection<ApplicationRegularUser> findAllRequestGranters() {
		TypedQuery<RequestGranter> q = entityManager().createQuery("SELECT o FROM RequestGranter o JOIN FETCH o.appRegUsers ",RequestGranter.class);
		RequestGranter rg=null;
		try{
		     rg=q.getSingleResult();
		}catch(EmptyResultDataAccessException e){
			return null;
		}
		return rg.getAppRegUsers();
	}
	
	public static List<String> findAllAdminEmails() {
		TypedQuery<Admin> q = entityManager().createQuery("SELECT o FROM Admin o JOIN FETCH o.appRegUsers ",Admin.class);
		Admin rg=null;
		try{
		     rg=q.getSingleResult();
		}catch(EmptyResultDataAccessException e){
			return null;
		}
		Collection<ApplicationRegularUser> ruc = rg.getAppRegUsers();
		List<String> ls = new ArrayList<String>();
		for (ApplicationRegularUser aru : ruc) {
			//TODO: ugly workarrownd - keep only the sql emails
			String email = aru.getRegularUser().getEmail();
			//if(email.contains("@sdl.com")){
				ls.add(email);
			//}
		}
		return ls;	
	}
	
	
	public static List<String> findAllHRManagementEmails() {
		TypedQuery<HRManagement> q = entityManager().createQuery("SELECT o FROM HRManagement o JOIN FETCH o.appRegUsers ",HRManagement.class);
		HRManagement rg=null;
		try{
		     rg=q.getSingleResult();
		}catch(EmptyResultDataAccessException e){
			return null;
		}
		Collection<ApplicationRegularUser> ruc = rg.getAppRegUsers();
		List<String> ls = new ArrayList<String>();
		for (ApplicationRegularUser aru : ruc) {
			//TODO: ugly workarrownd - keep only the sql emails
			String email = aru.getRegularUser().getEmail();
			//if(email.contains("@sdl.com")){
				ls.add(email);
			//}
		}
		return ls;	
	}
	
	
	
	/**
	 * Entity comparison
	 * 	- checking only id & version for the moment
	 */
	public boolean isSame(ApplicationRegularUser aru){
		return this.getId().equals(aru.getId()) &&
				this.getVersion().equals(aru.getVersion());
	}


	/**
	 * Adapted operation for the removal of the
	 * underlying regular User without a direct
	 * database removal.
	 */
	@Transactional
    public void remove() {
		this.getRegularUser().remove();
    }

	public boolean isRequestGranter() {
		for(AdvancedUserRole aur : this.getRoles()){
			if(aur.isRequestGranter()){
				return true;
			}
		}
		return false;
	}

	public boolean hasApprover() {
		return this.getGranter() != null;
	}

	public boolean isSuperUser() {
		return this.isRequestGranter() && !this.hasApprover();
	}
	
	public boolean isSuperApproverOf(ApplicationRegularUser aru){
		if(aru == null) throw new IllegalArgumentException("The ApplicationRegularUser argument is required");
		return this.equals(aru.retrieveTopARU());
				
	}
	
	public boolean isSuperApprovedBy(ApplicationRegularUser aru){
		if(aru == null) throw new IllegalArgumentException("The ApplicationRegularUser argument is required");
		return this.retrieveTopARU().equals(aru);
	}
	
	public ApplicationRegularUser retrieveTopARU(){
		ApplicationRegularUser aru = this;
		while(aru.getGranter() != null){
			aru = aru.getGranter();
		}
		return aru;
	}
	
	public static Set<ApplicationRegularUser> findAllSubordinatesRequestGranters(ApplicationRegularUser boss){
		if(boss == null) throw new IllegalArgumentException("The boss argument is required");
		TypedQuery<ApplicationRegularUser> q = entityManager().createQuery("SELECT o FROM ApplicationRegularUser o WHERE o.granter = :boss ",ApplicationRegularUser.class);
		q.setParameter("boss", boss);
		List<ApplicationRegularUser> laru = q.getResultList();
		//System.out.println(laru.size());
//		for(ApplicationRegularUser aru : laru){
//			System.out.println(aru.toString()+aru.isRequestGranter());
//			
//		}
		Iterator<ApplicationRegularUser> iaru = laru.iterator();
		while(iaru.hasNext()){
			ApplicationRegularUser aru = iaru.next();
			if(!aru.isRequestGranter()){
				iaru.remove();
			}
		}
//		System.out.println(laru.size());
//		for(ApplicationRegularUser aru : laru){
//			System.out.println(aru.toString()+aru.isRequestGranter());
//			
//		}
		return new HashSet<ApplicationRegularUser>(laru);
	}
	
	public static Set<ApplicationRegularUser> findAllSubordinatesTree(ApplicationRegularUser boss){
		Set<ApplicationRegularUser> fullSubordinateTree = new HashSet<ApplicationRegularUser>();	
		Queue<ApplicationRegularUser> qaru = new LinkedList<ApplicationRegularUser>();
		qaru.add(boss);
		//System.out.println("initial size"+qaru.size());
		while(!qaru.isEmpty()){
			ApplicationRegularUser aru = qaru.poll();
			//System.out.println("size after pool"+qaru.size());
			Set<ApplicationRegularUser> saru = ApplicationRegularUser.findAllSubordinatesRequestGranters(aru);
			for(ApplicationRegularUser maru:saru){
				qaru.add(maru);
			}
			//System.out.println("size after retrieve"+qaru.size());
			fullSubordinateTree.add(aru);
			//System.out.println("size full tree"+fullSubordinateTree.size());
		}
		fullSubordinateTree.remove(boss);
		return fullSubordinateTree;
		
		
	}

	/**
	 * Will return null if a username has no roles
	 * @param username
	 * @return
	 */
	public static ApplicationRegularUser findByUsernameWithRoles(String username) {
		if(username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
		EntityManager em = ApplicationRegularUser.entityManager();
		TypedQuery<ApplicationRegularUser> q = em.createQuery("SELECT o FROM ApplicationRegularUser AS o JOIN FETCH o.roles WHERE o.regularUser.username = :username",ApplicationRegularUser.class);
		q.setParameter("username",username);
		ApplicationRegularUser aru;
		try{
			 aru = q.getSingleResult();
			//System.out.println("username with role is"+aru);
		}catch(EmptyResultDataAccessException e){
			//System.out.println(e);
			return null;
		}
		return aru;
	}
	
	public static Set<String> findAllSubordinatesTreeUsernameString(Set<ApplicationRegularUser> saru){
		Set<String> sarustring = new HashSet<String>();
		for(ApplicationRegularUser appru : saru){
			sarustring.add(appru.getRegularUser().getUsername());
		}
		return sarustring;
	}
}
