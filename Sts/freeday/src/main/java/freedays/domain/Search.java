package freedays.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.util.StringUtils;

/**
 * Wrapper for search-ale
 * @author fmacicasan
 *
 */
@RooJavaBean
public class Search {

	private String searchKey;
	private String searchValue;
	
	/**
	 * Offers %-wildcard enclosing of searchValue
	 * @return %-wildcard enclosing of searchValue
	 * @invariant maintain validity
	 */
	public String searchValueLike(){
		assert this.isValid();
		
		StringBuilder searchValueLike = new StringBuilder();
		searchValueLike.append('%');
		searchValueLike.append(this.searchValue);
		searchValueLike.append('%');
		
		assert this.isValid();
		
		return searchValueLike.toString();
	}
	
	
	/**
	 * Verify validity of Search object (both not empty/null)
	 * @return
	 */
	public boolean isValid(){
		return StringUtils.hasText(this.searchKey) && StringUtils.hasText(this.searchValue);
	}
	
	public boolean isNotValid(){
		return !isValid();
	}
	
	
	
	
}
