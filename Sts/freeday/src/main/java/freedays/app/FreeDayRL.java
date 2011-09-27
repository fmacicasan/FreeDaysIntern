package freedays.app;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.springframework.format.annotation.DateTimeFormat;

@RooJavaBean
@RooEntity
public class FreeDayRL {

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    @Column(unique=true)
    private Calendar romanianHoliday;

    private String description;


	
	public static List<Calendar> getAllHolidays(){
		TypedQuery<Calendar> q = entityManager().
				createQuery("SELECT o.romanianHoliday FROM FreeDayRL o", Calendar.class);
		return q.getResultList();
	//		TypedQuery<FreeDayRL> q = entityManager().
	//		createQuery(" FROM FreeDayRL ", FreeDayRL.class);
	
	//Query q = entityManager().createQuery(" FROM FreeDayRL ");
	//System.out.println("query"+q.toString());
	//List fdrll = q.getResultList();
	//List<Calendar> lc =  new ArrayList<Calendar>();
	//for(Object fdrl : fdrll){
	//	lc.add(((FreeDayRL)fdrl).getRomanianHoliday());
	//}
	//System.out.println("the list"+lc);
	//return lc;
	//List<FreeDayRL> fdrll = FreeDayRL.findAllFreeDayRLs();
	//
	//List<Calendar> lc =  new ArrayList<Calendar>();
	//for(FreeDayRL fdrl : fdrll){
	//	lc.add(fdrl.getRomanianHoliday());
	//}
	//return lc;
	}

	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Description: ").append(getDescription()).append(", ");
        sb.append("RomanianHoliday: ").append(getRomanianHoliday() == null ? "null" : getRomanianHoliday().getTime());
        return sb.toString();
    }
}
