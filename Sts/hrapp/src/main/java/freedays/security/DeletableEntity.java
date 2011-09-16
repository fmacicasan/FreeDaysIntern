package freedays.security;

/**
 * Interface describing a deletable entity
 * @author fmacicasan
 *
 */
public interface DeletableEntity {
	/**
	 * Verifies weather or not the entity is deletable
	 * @return
	 */
	public boolean isDeletable();
	
	/**
	 * Sets the deletable status.
	 * @param deletable
	 */
	public void setDeletable(boolean deletable);
}
