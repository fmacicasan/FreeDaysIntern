package freedays.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserContextServiceImlp implements UserContextService{

	@Override
	public String getCurrentUser() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

}
