package freedays.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import freedays.domain.RegularUser;

@Service("userContextService")
public class UserContextServiceImlp implements UserContextService{

	@Override
	public String getCurrentUser() {
		try{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth.getName().equals("anonymousUser"))return null;
			return auth.getName();
		}catch(NullPointerException e){
			return null;
		}
		
	}

	@Override
	public boolean hasRole(String role) {
		try{
			Collection<GrantedAuthority> auth = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
			for (GrantedAuthority ga : auth) {
				if(ga.getAuthority().equals(role))return true;
			}
		}catch(NullPointerException e){}
		return false;
		
	}

	@Override
	public boolean isHR() {
		return hasRole("ROLE_HRMANAGEMENT");
	}

	@Override
	public boolean isOwn(RegularUser regularUser) {
		return regularUser.getUsername().equals(this.getCurrentUser());
	}
	

}
