package freedays.timesheet;
public class PhaseLabor {
	private String id;
	private LaborBilling lb;
	private Phase ph;
	private Float percentage;
	public PhaseLabor(String id, LaborBilling l, Phase p, Float perc) {
		this.id = id;
		this.lb = l;
		this.ph = p;
		this.percentage = perc;
	}
	public Phase getPhase() {
		return ph;
	}
	public LaborBilling getLaborBilling() {
		return lb;
	}
	public Float getPercentage() {
		return percentage;
	}
}
