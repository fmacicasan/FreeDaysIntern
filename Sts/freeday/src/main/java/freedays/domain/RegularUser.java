package freedays.domain;

import freedays.annotations.UniqueEmail;
import freedays.util.MailUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import freedays.util.PhraseUtils;

@RooJavaBean
@RooToString
@RooEntity
//@UniqueEmail
public class RegularUser implements Serializable {
	
	/**
	 * 0 - username
	 * 1 - email
	 * 2 - surename
	 * 3 - firstname
	 * 4 - usermodifier
	 */
	public static final String[] SEARCH_FILTERS = { "username", "email",
			"surename", "firstname", "usermodifier" };

	@NotNull
	@Column(unique = true)
	// TODO check what's going on
	@Length(min = 3, max = 45, message = "#{messages['field_invalid_length']}")
	private String username;

	@NotNull
	@Length(min = 6, max = 45)
	private String password;

	@NotNull
	@Email(message = "#{messages['field_invalid_email']}")
	private String email;

	@NotNull
	private String surename;

	@NotNull
	private String firstname;

    private Boolean deleted;

	@NotNull
	private Boolean activ;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "hh:mm:ss dd-MM-yyyy")
    private Calendar lastmodified;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "hh:mm:ss dd-MM-yyyy")
    private Calendar creationdate;

	private String usermodifier;

	/**
	 * Search all the RegularUser entities with username LIKE searchKey
	 * 
	 * @param searchKey
	 *            the Search object -> wrapper for Search filters
	 * @return a List with all matching RegularUser
	 */
	public static List<RegularUser> findAllRegularUsersLike(Search search) {
		EntityManager emag = RegularUser.entityManager();

		TypedQuery<RegularUser> query = emag.createQuery(
				"SELECT o FROM RegularUser o WHERE o." + search.getSearchKey()
						+ " LIKE ?1", RegularUser.class);
		// query.setParameter(1, search.getSearchKey());
		query.setParameter(1, search.searchValueLike());
		// TODO use logger
		List<RegularUser> result = query.getResultList();
		//System.out.println(query.getParameter(1).getName());
		//System.out.println("RegularUser list size:" + result.size());
		return result;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	public static long countRegularUsers() {
		return entityManager().createQuery(
				"SELECT COUNT(o) FROM RegularUser o", Long.class)
				.getSingleResult();
	}

	public static final EntityManager entityManager() {
		EntityManager em = new RegularUser().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	public static List<RegularUser> findAllRegularUsers() {
		return entityManager().createQuery("SELECT o FROM RegularUser o",
				RegularUser.class).getResultList();
	}

	public static RegularUser findRegularUser(Long id) {
        if (id == null) return null;
        return entityManager().find(RegularUser.class, id);
    }

	public static List<RegularUser> findRegularUserEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM RegularUser o", RegularUser.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
	
    public static TypedQuery<RegularUser> findRegularUsersByUsernameAndPasswordEquals(String username, String password) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        if (password == null || password.length() == 0) throw new IllegalArgumentException("The password argument is required");
        System.out.println("cucuriguuuu!!!");
        EntityManager em = RegularUser.entityManager();
        TypedQuery<RegularUser> q = em.createQuery("SELECT o FROM RegularUser AS o WHERE o.username = :username AND o.password = :password", RegularUser.class);
        q.setParameter("username", username);
        q.setParameter("password", password);
        return q;
    }
	
	/**
	 * Retrieval of the associated search criteria
	 * @return list of criteria fields
	 */
	public static List<String> getSearchCriteria(){
		return Arrays.asList(RegularUser.SEARCH_FILTERS);
	}
	
	/**
	 * Insertion trigger simulation
	 */
	@PrePersist
	protected void onCreate(){
		this.creationdate=Calendar.getInstance();
		this.lastmodified=Calendar.getInstance();
		this.deleted = false;
	}
	
	/**
	 * Update trigger simulation
	 */
	@PreUpdate
	protected void onUpdate(){
		this.lastmodified=Calendar.getInstance();
	}
	
	/**
	 * Mark deletion of user without actual removal
	 * @param id2 user identifier
	 */
	public static void deleteRegularUser(Long id2) {
		RegularUser regularU = RegularUser.findRegularUser(id2);
		regularU.setDeleted(true);
		regularU.persist();
	}

	private static final String RESET_PASS_TITLE = "FreeDays-PasswordReset";
	private static final String RESET_PASS_MESSAGE = "Your new password is:";
	public static boolean resetPassword(String email2) {
		List<RegularUser> list = RegularUser.findRegularUserByEmail(email2);
		if(list.size()!=1){
			return false;
		}
		RegularUser ru = list.get(0);
		String newPass = PhraseUtils.getRandomPhrase();
		ru.setPassword(newPass);
		MailUtils.send(ru.getEmail(), RESET_PASS_TITLE, RESET_PASS_MESSAGE+newPass);
		ru.persist();
		return true;
	}

	public static List<RegularUser> findRegularUserByEmail(String email) {
		if (email == null || email.length() == 0) throw new IllegalArgumentException("The email argument is required");
        EntityManager em = RegularUser.entityManager();
        TypedQuery<RegularUser> q = em.createQuery("SELECT o FROM RegularUser AS o WHERE o.email = :email ", RegularUser.class);
        q.setParameter("email", email);
        
        List<RegularUser> list = new ArrayList<RegularUser>();
        list.add(q.getSingleResult());
        return list;
	}
	
	@Transactional
	public static long countRegularUserByEmail(String email) {
		if (email == null || email.length() == 0) throw new IllegalArgumentException("The email argument is required");
        EntityManager em = RegularUser.entityManager();
        TypedQuery<Long> q = em.createQuery("SELECT COUNT(o) FROM RegularUser AS o WHERE o.email = :email and o.deleted = 0", Long.class);
        q.setParameter("email", email);
        
        return q.getSingleResult();
	}

	public String toString() {
        StringBuilder sb = new StringBuilder();
//        sb.append("Activ: ").append(getActiv()).append(", ");
//        sb.append("Creationdate: ").append(getCreationdate() == null ? "null" : getCreationdate().getTime()).append(", ");
//        sb.append("Deleted: ").append(getDeleted()).append(", ");
//        sb.append("Email: ").append(getEmail()).append(", ");
       // sb.append("Firstname: ");
        sb.append(getFirstname()).append(" ");
//        sb.append("Id: ").append(getId()).append(", ");
//        sb.append("Lastmodified: ").append(getLastmodified() == null ? "null" : getLastmodified().getTime()).append(", ");
//        sb.append("Password: ").append(getPassword()).append(", ");
//        sb.append("SearchCriteria: ").append(getSearchCriteria() == null ? "null" : getSearchCriteria().size()).append(", ");
        //sb.append("Surename: ").
        sb.append(getSurename());
//        sb.append("Usermodifier: ").append(getUsermodifier()).append(", ");
//        sb.append("Username: ").append(getUsername()).append(", ");
//        sb.append("Version: ").append(getVersion());
        return sb.toString().toUpperCase();
    }

	public static TypedQuery<RegularUser> findRegularUsersByUsername(String username) {
		 if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
	        EntityManager em = RegularUser.entityManager();
	        TypedQuery<RegularUser> q = em.createQuery("SELECT o FROM RegularUser AS o WHERE o.username = :username ", RegularUser.class);
	        q.setParameter("username", username);
	        return q;
	}

	public static Collection<RegularUser> findAllRegularUsersUnasociated() {
		return entityManager().createQuery("SELECT o FROM RegularUser o WHERE o.id NOT IN (SELECT f.regularUser FROM FDUser f) ",
				RegularUser.class).getResultList();
	}

	

	@Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        
        if (email == null || email.length() == 0) throw new IllegalArgumentException("The email argument is required");
        EntityManager em = RegularUser.entityManager();
        TypedQuery<Long> q = em.createQuery("SELECT COUNT(o) FROM RegularUser AS o WHERE o.email = :email and o.deleted = 0", Long.class);
        q.setParameter("email", email);
        
        if( q.getSingleResult()==0){
        	this.entityManager.persist(this);
        	}
    }
}
