package freedays.security;

import freedays.domain.RegularUser;


public interface UserContextService {
	
	public String getCurrentUser();
	
	public boolean hasRole(String role);
	
	public boolean isHR();

	public boolean isOwn(RegularUser regularUser);

}
