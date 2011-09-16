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
@RooToString
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
	}
}
