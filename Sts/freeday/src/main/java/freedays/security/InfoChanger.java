package freedays.security;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.List;

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
import freedays.util.DateUtils;

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



	public String genToken() {
		String hash = generateHash();
		String token = messageDigestPasswordEncoder.encodePassword(hash, this.getSeed());
		return  token;
	}

	private String generateHash() {
		RegularUser ru = this.getRegularUser();
		StringBuilder sb = new StringBuilder();
		sb.append(ru.getUsername());
		sb.append(DateUtils.printLong(ru.getCreationdate()));
		return sb.toString();
	}
	
	private String getSeed(){
		return DateUtils.printLong(this.getExpdate());
	}

	private boolean verifToken(String enchash) {
		String rawhash = generateHash();
		return messageDigestPasswordEncoder.isPasswordValid(enchash, rawhash, this.getSeed());
	}

	public boolean isExpired() {
		return this.getExpired();
	}

	public boolean isJustExpired() {
		return this.getExpdate().compareTo(Calendar.getInstance()) <= 0;
	}
	

	public boolean isUsed() {
		return this.getUsed();
	}

	public static Calendar computeExpireDate() {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.HOUR_OF_DAY, InfoChanger.DEFAULT_EXPIRE_INTERVAL_IN_HOURS);
		return now;
	}

	public static String generateToken(RegularUser ru) {
		Calendar exp = InfoChanger.computeExpireDate();
		InfoChanger ic = new InfoChanger();
		ic.setRegularUser(ru);
		ic.setExpdate(exp);
		ic.setExpcode(ic.genToken());
		ic.persist();
		return ic.getExpcode();
	}
	

	public static boolean verifyToken(String enchash) {
		try {
			InfoChanger ic = InfoChanger.findByHash(enchash).getSingleResult();
			if (!ic.verifToken(enchash)) {
				return false;
			}
			if (ic.isExpired() || ic.isUsed()) {
				return false;
			}
			if(ic.isJustExpired()){
				ic.setExpired(true);
				ic.merge();
				return false;
			}
			
			return true;
		} catch (EmptyResultDataAccessException e) {
			return false;
		} catch (EntityNotFoundException e) {
			return false;
		} catch (NonUniqueResultException e) {
			return false;
		}
	}
	
	public static TypedQuery<InfoChanger> findByHash(String hash) {
		if (hash == null) throw new IllegalArgumentException("the hash argument is required");
		TypedQuery<InfoChanger> q = entityManager().createQuery("SELECT o FROM InfoChanger o JOIN FETCH o.regularUser WHERE o.expcode = :expcode", InfoChanger.class);
		q.setParameter("expcode", hash);
		return q;
	}

	public static RegularUser finalizePassReset(String token) {
		InfoChanger ic = InfoChanger.findByHash(token).getSingleResult();
		ic.setUsed(true);
		ic.merge();
		return ic.getRegularUser();
	}
	
	public static List<InfoChanger> findAllActiveChangePasswordRequests(){
		TypedQuery<InfoChanger> q = entityManager().createQuery("SELECT o FROM InfoChanger o WHERE o.expired = false AND o.used = false",InfoChanger.class);
		return q.getResultList();
	}

}
