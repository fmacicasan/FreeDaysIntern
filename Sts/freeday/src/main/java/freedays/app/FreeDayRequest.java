package freedays.app;

import java.util.Calendar;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Future;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.validation.annotation.BusinessDay;

@RooJavaBean
public class FreeDayRequest {

    @NotNull
    @Future(message="date must be in future!")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "S-")
    @BusinessDay
    private Calendar reqdate;

    private String reason;
}
