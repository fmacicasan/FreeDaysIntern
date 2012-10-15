package freedays.app;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import freedays.app.FreeDayInterval.ConfidenceLevel;
import freedays.app.form.FreeDayRequest;
import freedays.app.form.FreeDayRequest.RequestType;
import freedays.app.form.FreeDayRequestInterval;
import freedays.mongo.PersonRepository;
import freedays.util.DateUtils;
import freedays.util.PropertiesUtil;
import freedays.util.ValidationUtils;

/**
 * Class representing a TypeV request. Such a request represents a vacation
 * taken between two business days.
 * 
 * @author fmacicasan
 * 
 */
@RooJavaBean
@RooToString
@RooEntity
@DiscriminatorValue("typeV")
public class FreeDayVacation extends FreeDayInterval {

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Vacation Request:");
		// sb.append(" from ").append(DateUtils.printShortDate(this.getDate()));
		// sb.append(" to ").append(DateUtils.printShortDate(this.getEnd()));
		sb.append(this.getDateReport());
		// DateUtils.dateAddRomanianBusinessDay(this.getDate(), this.getSpan())
		return sb.toString();
	}

	@Override
	public RequestType getType() {
		return RequestType.V;
	}

}
