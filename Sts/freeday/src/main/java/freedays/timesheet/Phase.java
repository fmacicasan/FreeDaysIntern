package freedays.timesheet;
public class Phase {
	private String name;
	private String id;
	private Project p;
	public Phase(String name, String ID, Project p) {
		this.name = name;
		this.id = ID;
		this.p = p;
	}
	public Project getProject() {
		return p;
	}
	public void setProject(Project proj) {
		p = proj;
	}
	public String getName() {
		return name;
	}
	public String getID() {
		return id;
	}
}
