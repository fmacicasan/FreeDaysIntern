package freedays.app;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import freedays.app.FreeDay.FreeDayStatus;
import freedays.app.form.FreeDayRequest;
import freedays.app.form.FreeDayRequest.RequestType;
import freedays.domain.RegularUser;
import freedays.util.PropertiesUtil;

@RooJavaBean
@RooEntity
@DiscriminatorValue("typeM")
public class FreeDayM extends FreeDayInterval {

	@Override
	protected FreeDayStatus getApproveStatus() {
		return FreeDayStatus.FINALIZE_SUCCESS;
	}

	
	/*
	@Override
	protected void initialize(FreeDayRequest fdr) {
		// nothing special here
	}*/

	@Override
	protected void finalizeFail() {
		// nothing special here

	}

	@Override
	public RequestType getType() {
		return RequestType.M;
	}

	@Override
	public String getReportType() {
		return PropertiesUtil.getProperty("freedaysreport_legend_typem");
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Medical Request:");
		// sb.append(" from ").append(DateUtils.printShortDate(this.getDate()));
		// sb.append(" to ").append(DateUtils.printShortDate(this.getEnd()));
		sb.append(this.getDateReport());
		// DateUtils.dateAddRomanianBusinessDay(this.getDate(), this.getSpan())
		return sb.toString();
	}

	public static List<FreeDayM> getAllGrantedFreeDayMByUsername(String username) {
		if (username == null || username.length() == 0)
			throw new IllegalArgumentException("The username argument is required");
		EntityManager em = RegularUser.entityManager();
		TypedQuery<FreeDayM> q = em.createQuery(
				"SELECT o FROM FreeDayM o, Request r WHERE r.appreguser.regularUser.username = :username AND r.requestable = o AND o.status IN :approveList ",
				FreeDayM.class);
		q.setParameter("username", username);
		q.setParameter("approveList", FreeDayStatus.getAllGrantedStatus());
		return q.getResultList();
	}
}
