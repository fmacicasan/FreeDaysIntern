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

import freedays.validation.annotation.Weekend;

@RooJavaBean
@RooToString
@RooEntity
public class FreeDayR extends FreeDay {

    @NotNull
    @Future
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "S-")
    @Weekend
    private Calendar recoverdate;

	@Override
	public Calendar getDate() {
		return this.getRecoverdate();
	}
}
