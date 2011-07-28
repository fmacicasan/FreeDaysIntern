package freedays.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NoResultException;
import javax.persistence.OneToMany;
import javax.persistence.TypedQuery;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import freedays.util.DAOUtils;

@RooJavaBean
@RooToString
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

    @ManyToOne
    private ApplicationRegularUser granter;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appreguser")
    private Set<Request> requests = new HashSet<Request>();
    
    public enum JobRole{DEV, QA, SDET, PM, IT, EM, PO, SE, PS}
    @Enumerated
    private JobRole jobrole;
    
   
    //@Transactional
	public static Set<AdvancedUserRole> getAllRolesByUsername(String username) {
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
		Set<AdvancedUserRole> set=new HashSet<AdvancedUserRole>();
			EntityManager em = RegularUser.entityManager();
	        TypedQuery<ApplicationRegularUser> q = em.createQuery("SELECT o FROM ApplicationRegularUser AS o JOIN FETCH o.roles WHERE o.regularUser.username = :username", ApplicationRegularUser.class);
	        q.setParameter("username", username);
	        ApplicationRegularUser aru = DAOUtils.getSingleResult(q);

	        if(aru!=null)
	          set = aru.getRoles();
	    return set;
	}

	public static Collection<ApplicationRegularUser> findAllRequestGranters() {
		TypedQuery<RequestGranter> q = entityManager().createQuery("SELECT o FROM RequestGranter o JOIN FETCH o.appRegUsers ",RequestGranter.class);
		RequestGranter rg=DAOUtils.getSingleResult(q);
		return rg.getAppRegUsers();
	}
	
	/**
	 * Entity comparison
	 * 	- checking only id & version for the moment
	 */
	public boolean isSame(ApplicationRegularUser aru){
		return this.getId().equals(aru.getId()) &&
				this.getVersion().equals(aru.getVersion());
	}

}
