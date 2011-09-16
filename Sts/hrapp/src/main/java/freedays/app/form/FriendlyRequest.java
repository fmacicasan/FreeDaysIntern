package freedays.app.form;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.app.FreeDay.FreeDayStatus;
import freedays.app.RequestStatus;
import freedays.app.form.FreeDayRequest.RequestType;
import freedays.domain.Request;

@RooJavaBean
@Configurable
public class FriendlyRequest {

	
	private Long id;
	private String appreguser;
	private RequestStatus status;
	private String approver;
	private String date;
	private String type;
	private String reason;
	private FreeDayStatus fdstatus;
	private String sortkey;
	
	public FriendlyRequest(Request r){
		this.id = r.getId();
		this.appreguser = r.getAppreguser().toString();
		this.status = r.getStatus();
		try{
			this.approver = r.getApprover().toString();
		}catch(NullPointerException e){
			this.approver = " ";
		}
		this.fdstatus = r.getRequestable().getStatus();
		this.sortkey=processDate2SortString(r.getRequestable().getDate());
		this.date = r.getRequestable().getDateReport();
		this.type = r.getRequestable().getReportType();
		this.reason = r.getRequestable().getReason();	
	}
	
	private String processDate2SortString(Calendar date2) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%1$tY%1$tm%1$td", date2));
		System.out.println(sb.toString());
		return sb.toString();
	}

	public static List<FriendlyRequest> processRequestList(List<Request> reqlst) {
		List<FriendlyRequest> lfr = new ArrayList<FriendlyRequest>();
		for(Request r : reqlst){
			lfr.add(new FriendlyRequest(r));
		}
		return lfr;
	}
	
	
	
}
