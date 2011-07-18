package freedays.app;

import freedays.domain.ApplicationRegularUser;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import java.util.Calendar;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

@RooJavaBean
@RooToString
@RooEntity
public class FDUser extends ApplicationRegularUser {

    @NotNull
    @Past
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "S-")
    private Calendar hireDate;

    @NotNull
    @Min(2L)
    @Max(7L)
    private Integer initDays;

    @NotNull
    @Min(21L)
    private Integer maxFreeDays;
}
