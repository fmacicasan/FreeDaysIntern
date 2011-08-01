package freedays.app;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import java.util.Calendar;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Future;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

import freedays.validation.annotation.BusinessDay;

@RooJavaBean
@RooToString
@RooEntity
public class FreeDayC extends FreeDay {

    @NotNull
    @Future
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "S-")
    @BusinessDay
    private Calendar requestdate;


	@Override
	public Calendar getDate() {
		return this.getRequestdate();
	}
}
