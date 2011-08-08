package freedays.security;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import freedays.domain.RegularUser;
import freedays.domain.Request;

/**
 * A {@link org.springframework.security.access.PermissionEvaluator PermissionEvaluator} implementation
 * used for verifying custom permission requests. 
 * 
 * @author fmacicasan
 *
 */
public class CustomPermissionEvaluator implements PermissionEvaluator {

	/**
	 * Permission verification call based on some domain object and an
	 * associated permission. 
	 * @throws UnsupportedOperationException if the target object or the permission is not supported.
	 */
	@Override
	public boolean hasPermission(Authentication authentication,
			Object targetDomainObject, Object permission){
		//TODO: consider visitor (overload method for each supported class)
		if(targetDomainObject instanceof RegularUser){
			RegularUser ru = (RegularUser)targetDomainObject;
			
			if(permission.equals("own")){
				return ru.getUsername().equals(authentication.getName());
			}
		}
		
		if(targetDomainObject instanceof Request){
			Request r = (Request) targetDomainObject;
			
			if(permission.equals("own")){
				return r.isOwner(authentication.getName());
			}
		}
		
		throw new UnsupportedOperationException("hasPermission not supported for object and permission");
	}

	/**
	 * @throws UnsupportedOperationException if the target type or the permission is not supported
	 */
	@Override
	public boolean hasPermission(Authentication authentication,
			Serializable targetId, String targetType, Object permission) {
		
		if(targetId instanceof Long){
			Long id = (Long)targetId;
			if(RegularUser.class.getSimpleName().equals(targetType)){
				RegularUser ru = RegularUser.findRegularUser(id);
				
				if(permission.equals("own")){
					if(ru==null)return false;
					return ru.getUsername().equals(authentication.getName());
				}
			}
		
			if(Request.class.getSimpleName().equals(targetType)){	
				Request ru = Request.findRequest(id);
				
				if(permission.equals("own")){
					if(ru==null)return false;
					return ru.isOwner(authentication.getName());
				}
				
				if(permission.equals("approve")){
					if(ru==null)return false;
					return ru.isApprover(authentication.getName());
				}
			}
		}
		
		throw new UnsupportedOperationException("hasPermission not supported for id, type and permission");
	}

}