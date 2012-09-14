package freedays.domain;

import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.OneToOne;
import javax.persistence.TypedQuery;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.log4j.Logger;
import org.hibernate.annotations.Cascade;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.web.multipart.MultipartFile;

import antlr.collections.List;

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
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	@NotNull
	private Document document;

	@OneToOne
	@NotNull
	private RegularUser regularUser;

	public static Profile findProfileByRegularUserId(Long id) {

		TypedQuery<Profile> query = entityManager().createQuery(
				"SELECT o FROM Profile o WHERE o.regularUser.id = ?1",
				Profile.class);
		query.setParameter(1, id);

		java.util.List<Profile> pl = query.getResultList();
		return pl.size() == 0 ? null : pl.get(0);
	}

	/*
	 * public static Collection<RegularUser> findAllRegularUsersWithProfile(){
	 * 
	 * }
	 */

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

	public void setDocumentContent(MultipartFile content) {

		try {
			this.getDocument().setContent(content.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getDocument().setContentType(content.getContentType());
		this.getDocument().setFilename(content.getOriginalFilename());
		this.getDocument().setSize(content.getSize());

	}

}
