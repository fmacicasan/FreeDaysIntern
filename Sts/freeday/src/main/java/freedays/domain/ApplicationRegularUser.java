package freedays.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import freedays.app.FDUser;
import freedays.domain.RegularUser;
import javax.persistence.ManyToOne;

import java.util.Collection;
import java.util.Set;
import freedays.domain.AdvancedUserRole;
import java.util.HashSet;

import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.CascadeType;
import javax.persistence.TypedQuery;

import freedays.domain.RequestGranter;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import freedays.domain.Request;
import javax.persistence.OneToMany;

@RooJavaBean
@RooToString
@RooEntity(inheritanceType = "TABLE_PER_CLASS")
public abstract class ApplicationRegularUser {

    @ManyToOne
    private RegularUser regularUser;

    @ManyToMany(cascade = CascadeType.ALL)//, fetch=FetchType.EAGER 
    @JoinTable(name = "AppRegUser_AdvRole", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<AdvancedUserRole> roles = new HashSet<AdvancedUserRole>();

    @ManyToOne
    private ApplicationRegularUser granter;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appreguser")
    private Set<Request> requests = new HashSet<Request>();
    
   // @Transactional
	public static Set<AdvancedUserRole> getAllRolesByUsername(String username) {
		if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        EntityManager em = RegularUser.entityManager();
        TypedQuery<ApplicationRegularUser> q = em.createQuery("SELECT o FROM ApplicationRegularUser AS o JOIN FETCH o.roles WHERE o.regularUser.username = :username", ApplicationRegularUser.class);
        q.setParameter("username", username);
        ApplicationRegularUser aru = q.getSingleResult();
        //System.out.println(aru.toString());
        Set<AdvancedUserRole> set = aru.getRoles();
        return set;
	}

	public static Collection<ApplicationRegularUser> findAllRequestGranters() {
		RequestGranter rg = entityManager().createQuery("SELECT o FROM RequestGranter o JOIN FETCH o.appRegUsers ",RequestGranter.class).getSingleResult();
		return rg.getAppRegUsers();
	}
}
