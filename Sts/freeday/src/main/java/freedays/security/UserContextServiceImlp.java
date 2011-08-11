package freedays.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContextServiceImlp implements UserContextService{

	@Override
	public String getCurrentUser() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	@Override
	public boolean hasRole(String role) {
		Collection<GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority ga : auth) {
			if(ga.getAuthority().equals(role))return true;
		}
		return false;
		
	}

}
