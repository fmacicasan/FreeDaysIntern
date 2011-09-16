package freedays.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NonUniqueResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import freedays.app.FDUser;
import freedays.domain.AdvancedUserRole;
import freedays.domain.ApplicationRegularUser;
import freedays.domain.RegularUser;

/**
 * Controller used to intercept authentication requests generated due to the
 * access of privileged resources.
 * @author iteglas
 *
 */
@RooJavaBean
@Configurable
public class AuthentificationController extends
		AbstractUserDetailsAuthenticationProvider  {

	@Autowired
	private MessageDigestPasswordEncoder messageDigestPasswordEncoder;
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {

	}

	@Override
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		String password = (String) authentication.getCredentials();
		
		if (!StringUtils.hasText(password)) {
			throw new BadCredentialsException("Please enter the password!");
		}
		String encryptedPassword = messageDigestPasswordEncoder.encodePassword(password, null);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		try {
			RegularUser regularUser =RegularUser
					.findRegularUsersByUsername(username).getSingleResult();
			
			if(!regularUser.getUsername().equals(username)) throw new BadCredentialsException("Invalid username or password");
			if(!regularUser.getPassword().equals(encryptedPassword)) throw new BadCredentialsException("Invalid username or password");
			if(!regularUser.getActiv()) throw new BadCredentialsException("Your account has been disabled!");
			if(regularUser.getDeleted()) throw new BadCredentialsException("Your accout has been deleted!");
			if(FDUser.isUnassociated(regularUser)) throw new BadCredentialsException("Your account has not been processed yet! - (4Test) needs FDUser creation!");
			
			authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
			Set<AdvancedUserRole> set = ApplicationRegularUser.getAllRolesByUsername(username);
			for (AdvancedUserRole aur : set) {
				authorities.add(new GrantedAuthorityImpl(aur.toString()));
			}
			ApplicationRegularUser aru = ApplicationRegularUser.findByUsernameWithRoles(username);
			if(aru.isSuperUser()){
				authorities.add(new GrantedAuthorityImpl("ROLE_SUPERAPPROVER"));
			}
		}catch (EmptyResultDataAccessException e){
			throw new BadCredentialsException("Invalid username or password");
		} catch (EntityNotFoundException e) {
			throw new BadCredentialsException("Invalid user");
		} catch (NonUniqueResultException e) {
			throw new BadCredentialsException("Non-unique user, contact administrator");
		}
		return new User(username, password, true, true, true, true, authorities);
	}

}
