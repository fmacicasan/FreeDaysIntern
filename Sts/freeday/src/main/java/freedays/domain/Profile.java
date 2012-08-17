package freedays.domain;

import javax.persistence.EntityManager;
import javax.persistence.OneToOne;
import javax.persistence.TypedQuery;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * 
 * Profile for a user, which contains a document
 * 
 * 
 * 
 * @author osuciu
 * 
 */

@RooJavaBean
@RooToString
@RooEntity
public class Profile {

	@OneToOne
	private Document document;

	@OneToOne
	private RegularUser regularUser;

	public static Profile findProfileByRegularUserId(Long id) {

		TypedQuery<Profile> query = entityManager().createQuery(
				"SELECT o FROM Profile o WHERE o.regularUser.id = ?1",
				Profile.class);
		query.setParameter(1, id);

		return query.getSingleResult();
	}

	/**
	 * Retrieves the associated entity manager
	 * 
	 * @return
	 */
	public static final EntityManager entityManager() {
		EntityManager em = new RegularUser().entityManager;
		if (em == null)
			throw new IllegalStateException(
					"Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
		return em;
	}

}
