package freedays.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Calendar;
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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEntity
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
}
