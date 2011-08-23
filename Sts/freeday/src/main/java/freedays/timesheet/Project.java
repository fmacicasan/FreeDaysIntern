package freedays.timesheet;
import java.util.ArrayList;

public class Project {
	private String id;
	private String name;
	private ArrayList<Phase> phList;
	public Project(String ID, String name) {
		phList = new ArrayList<Phase>();
		this.name = name;
		this.id = ID;
	}
	public void addPhase(Phase newPhase) {
		phList.add(newPhase);
	}
	public String getName() {
		return name;
	}
	public String getID() {
		return id;
	}
}
