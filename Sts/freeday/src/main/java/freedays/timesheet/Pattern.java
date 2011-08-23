package freedays.timesheet;
import java.util.ArrayList;

public class Pattern {
	private ArrayList<PhaseLabor> plArray;
	private Integer noOfHours;
	public Pattern(int noh) {
		plArray = new ArrayList<PhaseLabor>();
		noOfHours = noh;
	}
	public void addPhL(PhaseLabor x) {
		plArray.add(x);
	}
	public PhaseLabor getPhL(int ind) {
		return plArray.get(ind);
	}
	public ArrayList<PhaseLabor> getArrayPhaseLabor() {
		return plArray;
	}
	public Integer getNoOfHours() {
		return noOfHours;
	}
	public void setNoOfHours(Integer noh) {
		noOfHours = noh;
	}
}
