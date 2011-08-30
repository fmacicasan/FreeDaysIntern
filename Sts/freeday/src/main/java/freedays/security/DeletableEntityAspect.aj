package freedays.security;

import javax.persistence.Transient;

public aspect DeletableEntityAspect {
	
	declare parents : freedays.domain.RegularUser implements DeletableEntity;
	declare parents : freedays.app.FDUser implements DeletableEntity;
	declare parents : freedays.timesheet.* implements DeletableEntity;



	@Transient
	private transient boolean DeletableEntity.deletable = false;
	
	public boolean DeletableEntity.isDeletable(){
		return deletable;
	}
	
	public void DeletableEntity.setDeletable(boolean deletable){
		this.deletable = deletable;
	}


}
