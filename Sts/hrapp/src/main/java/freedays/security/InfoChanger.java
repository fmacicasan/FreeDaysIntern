package freedays.security;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

import javax.validation.constraints.NotNull;
import java.util.Calendar;

import javax.persistence.EntityNotFoundException;
import javax.persistence.ManyToOne;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.format.annotation.DateTimeFormat;

import freedays.domain.RegularUser;

import javax.persistence.Column;

@RooJavaBean
@RooToString
@RooEntity
public class InfoChanger {

	public static final Integer DEFAULT_EXPIRE_INTERVAL_IN_HOURS = 8;
	@NotNull
	@Column(unique = true)
	private String expcode;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Calendar expdate;

	@ManyToOne
	private RegularUser regularUser;

	@Value("false")
	private Boolean expired;

	@Value("false")
	private Boolean used;

	@Autowired
	private transient MessageDigestPasswordEncoder messageDigestPasswordEncoder;

	public static Calendar computeExpireDate() {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.HOUR_OF_DAY, InfoChanger.DEFAULT_EXPIRE_INTERVAL_IN_HOURS);
		return now;
	}

	public static String generateToken(RegularUser ru) {
		Calendar exp = InfoChanger.computeExpireDate();

		InfoChanger ic = new InfoChanger();

		String token = ic.genToken(ru, exp);
		ic.setRegularUser(ru);
		ic.setExpcode(token);
		ic.setExpdate(exp);
		ic.persist();
		return token;
	}

	private String genToken(RegularUser ru, Calendar exp) {
		String hash = generateHash(ru);
		return messageDigestPasswordEncoder.encodePassword(hash, exp);
	}

	private String generateHash(RegularUser ru) {
		StringBuilder sb = new StringBuilder();
		sb.append(ru.getUsername());
		sb.append(ru.getCreationdate());
		return sb.toString();
	}

	private boolean verifToken(String enchash) {
		String rawhash = generateHash(this.getRegularUser());
		System.out.println(rawhash);
		System.out.println(enchash);
		System.out.println(messageDigestPasswordEncoder.encodePassword(rawhash, this.getExpdate()));
		return messageDigestPasswordEncoder.isPasswordValid(enchash, rawhash, this.getExpdate());
	}

	public static boolean verifyToken(String enchash) {
		System.out.println("cucurigzzzz");
		try {
			InfoChanger ic = InfoChanger.findByHash(enchash).getSingleResult();
			if (!ic.verifToken(enchash)) {
				System.out.println("not1");
				return false;
			}
			if (ic.isExpired() || ic.isUsed() || ic.isJustExpired()) {
				System.out.println("not2");
				return false;
			}
			System.out.println("not3");
			ic.setUsed(true);
			ic.merge();
			return true;
		} catch (EmptyResultDataAccessException e) {
			return false;
		} catch (EntityNotFoundException e) {
			return false;
		} catch (NonUniqueResultException e) {
			return false;
		}
	}

	public boolean isExpired() {
		return this.getExpired();
	}

	public boolean isJustExpired() {
		return this.getExpdate().compareTo(Calendar.getInstance()) > 0;
	}

	public boolean isUsed() {
		return this.getUsed();
	}

	public static TypedQuery<InfoChanger> findByHash(String hash) {
		if (hash == null)
			throw new IllegalArgumentException("the hash argument is required");
		TypedQuery<InfoChanger> q = entityManager().createQuery("SELECT o FROM InfoChanger o JOIN FETCH o.regularUser WHERE o.expcode = :expcode", InfoChanger.class);
		q.setParameter("expcode", hash);
		return q;
	}

}
