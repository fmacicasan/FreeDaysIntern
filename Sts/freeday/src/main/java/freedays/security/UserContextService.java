package freedays.security;

public interface UserContextService {
	
	public String getCurrentUser();
	
	public boolean hasRole(String role);

}
