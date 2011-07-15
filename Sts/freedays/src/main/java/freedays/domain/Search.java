package freedays.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;

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
		return  Search.isStringValid(this.searchKey) && Search.isStringValid(this.searchValue);
	}
	
	public boolean isNotValid(){
		return !isValid();
	}
	
	/**
	 * Checks validity of string (not null, not empty)
	 * TODO: add not whitespace
	 * @param str the string to be checked
	 * @return true for valid / false for invalid
	 */
	public static boolean isStringValid(String str){
		if(str==null)return false;
		if(str.isEmpty())return false;
		return true;
	}
	
	
	
}
