package freedays.timesheet;
public class LaborBilling {
	private String id;
	private String name;
	public LaborBilling(String id, String name) {
		this.id = id;
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public String getId() {
		return id;
	}
}
