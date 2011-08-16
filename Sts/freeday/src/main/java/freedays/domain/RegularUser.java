package freedays.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import freedays.security.UserContextService;
import freedays.domain.form.ChangePassWrapper;
import freedays.domain.form.Search;
import freedays.domain.form.SignupWrapper;
import freedays.domain.form.UpdateWrapper;
import freedays.util.MailUtils;
import freedays.util.PhraseUtils;

@RooJavaBean
@RooToString
@RooEntity
public class RegularUser implements Serializable {

	/**
	 * 0 - username 1 - email 2 - surename 3 - firstname 4 - usermodifier
	 */
	public static final String[] SEARCH_FILTERS = { "username", "email",
			"surename", "firstname", "usermodifier" };

	@NotNull
	@Column(unique = true)
	// TODO check what's going on
	@Length(min = 3, max = 45, message = "#{messages['field_invalid_length']}")
	private String username;

	@NotNull
	@Length(min = 6)
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
	
	@Autowired
	private transient UserContextService userContextService;
	@Autowired
	private transient MessageDigestPasswordEncoder messageDigestPasswordEncoder;
	
	

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
		return result;
	}

	@PersistenceContext
	transient EntityManager entityManager;

	/**
	 * Counts the number of regular users in the database
	 * @return
	 */
	public static Long countRegularUsers() {
		TypedQuery<Long> q = entityManager().createQuery(
				"SELECT COUNT(o) FROM RegularUser o", Long.class);
		Long res;
		try{
			res= q.getSingleResult();
		}catch(EmptyResultDataAccessException e){
			return null;
		}
		return res;
		
	}

	
	/**
	 * Retrieves the associated entity manager
	 * @return
	 */
	public static final EntityManager entityManager() {
		EntityManager em = new RegularUser().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

	/**
	 * Retrieves all the regular users
	 * @return
	 */
	public static List<RegularUser> findAllRegularUsers() {
		return entityManager().createQuery("SELECT o FROM RegularUser o",
				RegularUser.class).getResultList();
	}

	/**
	 * Retrieves a regular user based on his identifier.
	 * @param id
	 * @return
	 */
	public static RegularUser findRegularUser(Long id) {
		if (id == null)
			return null;
		return entityManager().find(RegularUser.class, id);
	}

	/**
	 * Retrieves a number of maxResults regular user entries starting at the first result
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	public static List<RegularUser> findRegularUserEntries(int firstResult,
			int maxResults) {
		return entityManager()
				.createQuery("SELECT o FROM RegularUser o", RegularUser.class)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}

	/**
	 * Finds a regular user based on his username and password.
	 * @param username
	 * @param password
	 * @return
	 */
	@Deprecated
	public static TypedQuery<RegularUser> findRegularUsersByUsernameAndPasswordEquals(
			String username, String password) {
		if (username == null || username.length() == 0)
			throw new IllegalArgumentException(
					"The username argument is required");
		if (password == null || password.length() == 0)
			throw new IllegalArgumentException(
					"The password argument is required");
		EntityManager em = RegularUser.entityManager();
		TypedQuery<RegularUser> q = em
				.createQuery(
						"SELECT o FROM RegularUser AS o WHERE o.username = :username AND o.password = :password",
						RegularUser.class);// AND o.deleted = :false AND o.activ = :true
		q.setParameter("username", username);
		q.setParameter("password", password);
		//q.setParameter("false", false);
		//q.setParameter("true", true);
		return q;
	}

	/**
	 * Retrieval of the associated search criteria
	 * 
	 * @return list of criteria fields
	 */
	public static List<String> getSearchCriteria() {
		return Arrays.asList(RegularUser.SEARCH_FILTERS);
	}

	/**
	 * Insertion trigger simulation
	 */
	@PrePersist
	protected void onCreate() {
		this.creationdate = Calendar.getInstance();
		this.lastmodified = Calendar.getInstance();
		this.deleted = false;
		this.activ = true;
		String cu = this.userContextService.getCurrentUser();
		this.usermodifier=(cu!=null)?cu:this.getUsername();
	}

	/**
	 * Update trigger simulation
	 */
	@PreUpdate
	protected void onUpdate() {
		this.lastmodified = Calendar.getInstance();
		String cu = this.userContextService.getCurrentUser();
		this.usermodifier=(cu!=null)?cu:this.getUsername();
	}

	/**
	 * Mark deletion of user without actual removal
	 * 
	 * @param id2
	 *            user identifier
	 */
	public static void deleteRegularUser(Long id2) {
		RegularUser regularU = RegularUser.findRegularUser(id2);
		regularU.remove();
//		regularU.setDeleted(true);
//		regularU.persist();
	}

	private static final String RESET_PASS_TITLE = "FreeDays-PasswordReset";
	private static final String RESET_PASS_MESSAGE = "Your new password is:";

	/**
	 * Reset's a regular's user password based on his email address.
	 * @param email2
	 * @return
	 */
	public static boolean resetPassword(String email2) {
		List<RegularUser> list = RegularUser.findRegularUserByEmail(email2);
		if (list.size() != 1) {
			return false;
		}
		RegularUser ru = list.get(0);
		String newPass = PhraseUtils.getRandomPhrase();
		String encodednewpass = ru.getMessageDigestPasswordEncoder().encodePassword(newPass, null);
		ru.setPassword(encodednewpass);
		MailUtils.send(ru.getEmail(), RESET_PASS_TITLE, RESET_PASS_MESSAGE
				+ newPass);
		ru.persist();
		return true;
	}

	/**
	 * Retrieves a regular user based on his email.
	 * @param email
	 * @return
	 */
	public static List<RegularUser> findRegularUserByEmail(String email) {
		if (email == null || email.length() == 0)
			throw new IllegalArgumentException("The email argument is required");
		EntityManager em = RegularUser.entityManager();
		TypedQuery<RegularUser> q = em.createQuery(
				"SELECT o FROM RegularUser AS o WHERE o.email = :email ",
				RegularUser.class);
		q.setParameter("email", email);

		return q.getResultList();
	}
	
	/**
	 * Provides the complete name of the regular user
	 * @return
	 */
	public String getFullName(){
		StringBuilder sb = new StringBuilder();
		sb.append(this.firstname);
		sb.append(" ");
		sb.append(this.surename);
		return sb.toString();
	}

	/**
	 * Counts the apparitions of an email in the regular user database.
	 * The email should be unique.
	 * @param email
	 * @return
	 */
//	@Transactional
	public static Long countRegularUserByEmail(String email) {
		if (email == null || email.length() == 0)
			throw new IllegalArgumentException("The email argument is required");
		EntityManager em = RegularUser.entityManager();
		TypedQuery<Long> q = em
				.createQuery(
						"SELECT COUNT(o) FROM RegularUser AS o WHERE o.email = :email and o.deleted = 0",
						Long.class);
		q.setParameter("email", email);
		Long res;
		try{
			res=q.getSingleResult();
		}catch(EmptyResultDataAccessException e){
			return null;
		}
		return res;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		// sb.append("Activ: ").append(getActiv()).append(", ");
		// sb.append("Creationdate: ").append(getCreationdate() == null ? "null"
		// : getCreationdate().getTime()).append(", ");
		// sb.append("Deleted: ").append(getDeleted()).append(", ");
		// sb.append("Email: ").append(getEmail()).append(", ");
		// sb.append("Firstname: ");
		sb.append(getFirstname()).append(" ");
		// sb.append("Id: ").append(getId()).append(", ");
		// sb.append("Lastmodified: ").append(getLastmodified() == null ? "null"
		// : getLastmodified().getTime()).append(", ");
		// sb.append("Password: ").append(getPassword()).append(", ");
		// sb.append("SearchCriteria: ").append(getSearchCriteria() == null ?
		// "null" : getSearchCriteria().size()).append(", ");
		// sb.append("Surename: ").
		sb.append(getSurename());
		// sb.append("Usermodifier: ").append(getUsermodifier()).append(", ");
		// sb.append("Username: ").append(getUsername()).append(", ");
		// sb.append("Version: ").append(getVersion());
		return sb.toString().toUpperCase();
	}
	
	/**
	 * Selects a regular user based on his unique username.
	 * @param username
	 * @return the typed query representing the selection
	 */
	public static TypedQuery<RegularUser> findRegularUsersByUsername(
			String username) {
		if (username == null || username.length() == 0)
			throw new IllegalArgumentException(
					"The username argument is required");
		EntityManager em = RegularUser.entityManager();
		TypedQuery<RegularUser> q = em.createQuery(
				"SELECT o FROM RegularUser AS o WHERE o.username = :username ",
				RegularUser.class);
		q.setParameter("username", username);
		return q;
	}

	
	/**
	 * Offers the collection of all the RegularUsers that are not having an associated
	 * FDUser.
	 * @return collection representing all regularUsers not having an associated FDUser.
	 */
	public static Collection<RegularUser> findAllRegularUsersUnasociated() {
		
		return entityManager()
				.createQuery(
						"SELECT o FROM RegularUser o WHERE o.id NOT IN (SELECT f.regularUser FROM FDUser f) ",
						RegularUser.class).getResultList();
	}

	/**
	 * Persists the entity.
	 */
	@Transactional
	public void persist() {
		if (this.entityManager == null)
			this.entityManager = entityManager();
		this.entityManager.persist(this);
		entityManager.flush();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((activ == null) ? 0 : activ.hashCode());
		result = prime * result
				+ ((creationdate == null) ? 0 : creationdate.hashCode());
		result = prime * result + ((deleted == null) ? 0 : deleted.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result
				+ ((lastmodified == null) ? 0 : lastmodified.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((surename == null) ? 0 : surename.hashCode());
		result = prime * result
				+ ((usermodifier == null) ? 0 : usermodifier.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegularUser other = (RegularUser) obj;
		if (activ == null) {
			if (other.activ != null)
				return false;
		} else if (!activ.equals(other.activ))
			return false;
		if (creationdate == null) {
			if (other.creationdate != null)
				return false;
		} else if (!creationdate.equals(other.creationdate))
			return false;
		if (deleted == null) {
			if (other.deleted != null)
				return false;
		} else if (!deleted.equals(other.deleted))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (lastmodified == null) {
			if (other.lastmodified != null)
				return false;
		} else if (!lastmodified.equals(other.lastmodified))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (surename == null) {
			if (other.surename != null)
				return false;
		} else if (!surename.equals(other.surename))
			return false;
		if (usermodifier == null) {
			if (other.usermodifier != null)
				return false;
		} else if (!usermodifier.equals(other.usermodifier))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}


	/**
	 * Custom removal operation. It will set the deleted flag
	 * without an actual removal from the data source.
	 */
	@Transactional
    public void remove() {
		this.setDeleted(true);
		this.persist();
		//this.setUsermodifier(SecurityContextHolder.getContext().getAuthentication().getName());
//        if (this.entityManager == null) this.entityManager = entityManager();
//        if (this.entityManager.contains(this)) {
//            this.entityManager.remove(this);
//        } else {
//            RegularUser attached = RegularUser.findRegularUser(this.getId());
//            RegularUser attached = RegularUser.findRegularUser(this.id);
//            this.entityManager.remove(attached);
//        }
    }

	/**
	 * Creates and persists a new regular user based on the signupwrapper object.
	 * @param regularUser
	 * @return
	 */
	public static RegularUser signupnew(SignupWrapper sw) {
		RegularUser ru = new RegularUser();
		ru.setUsername(sw.getUsername());
		ru.setEmail(sw.getEmail());
		ru.setSurename(sw.getSurename());
		ru.setFirstname(sw.getFirstname());
		ru.setPassword(sw.getPassword());
		ru.persist();
		return ru;
	}

	/**
	 * Update method based on the update account form.
	 * @param uw
	 */
	public void update(UpdateWrapper uw) {
		this.setEmail(uw.getEmail());
		this.setFirstname(uw.getFirstname());
		this.setSurename(uw.getSurename());
		this.merge();
		
	}

	/**
	 * Update method based on the change password form.
	 * @param uw
	 */
	public void update(ChangePassWrapper uw) {
		this.setPassword(uw.getPassword());
		this.merge();
		
	}

	/**
	 * @return the messageDigestPasswordEncoder
	 */
	public MessageDigestPasswordEncoder getMessageDigestPasswordEncoder() {
		return messageDigestPasswordEncoder;
	}

	/**
	 * @param messageDigestPasswordEncoder the messageDigestPasswordEncoder to set
	 */
	public void setMessageDigestPasswordEncoder(
			MessageDigestPasswordEncoder messageDigestPasswordEncoder) {
		this.messageDigestPasswordEncoder = messageDigestPasswordEncoder;
	}

	/**
	 * Counts the regular users based on their username
	 * @param username
	 * @return
	 */
	public static Long countRegularUserByUsername(String username) {
		if (username == null || username.length() == 0)throw new IllegalArgumentException("The username2 argument is required");
		EntityManager em = RegularUser.entityManager();
		TypedQuery<Long> q = em.createQuery("SELECT COUNT(o) FROM RegularUser AS o WHERE o.username = :username and o.deleted = 0",Long.class);
		q.setParameter("username", username);
		Long res=q.getSingleResult();
		return res;
	}
}
