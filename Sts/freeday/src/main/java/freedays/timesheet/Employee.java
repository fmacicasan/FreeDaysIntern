package freedays.timesheet;
import java.util.ArrayList;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
@RooJavaBean
public class Employee {
	
	private Integer id;
	private String name;
	private String position;
	private ArrayList<Schedule> schedList;
	
	public Employee(Integer id, String nm, String position) {
		schedList = new ArrayList<Schedule>();
		this.id = id;
		this.name = nm;
		this.position = position;
	}

	public void addSchedule(Schedule sch) {
		schedList.add(sch);
	}
	

}
