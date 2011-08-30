package freedays.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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


/**
 * Abstract class describing an general application user.
 * @author fmacicasan
 *
 */
@RooJavaBean
@RooEntity(inheritanceType = "TABLE_PER_CLASS")
public abstract class ApplicationRegularUser  implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
    private RegularUser regularUser;

    @ManyToMany(cascade = CascadeType.ALL)//, fetch=FetchType.EAGER 
    @JoinTable(name = "AppRegUser_AdvRole", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<AdvancedUserRole> roles = new HashSet<AdvancedUserRole>();

    @ManyToOne(fetch=FetchType.EAGER)
    private ApplicationRegularUser granter;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appreguser")
    private Set<Request> requests = new HashSet<Request>();
    
    public enum JobRole{DEV, QA, SDET, PM, IT, EM, PO, SE, PS}
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
			EntityManager em = RegularUser.entityManager();
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
			if(email.contains("@sdl.com")){
				ls.add(email);
			}
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
//        if (this.entityManager == null) this.entityManager = entityManager();
//        if (this.entityManager.contains(this)) {
//            this.entityManager.remove(this);
//        } else {
//            ApplicationRegularUser attached = ApplicationRegularUser.findApplicationRegularUser(this.id);
//            this.entityManager.remove(attached);
//        }
    }
}
