package freedays.mongo.domain;

import java.util.Calendar;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.Id;
import org.springframework.data.document.mongodb.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;


@RooJavaBean
@Document
public class DailyReport {
	
	@Id
    private String dailyReportId;
	
	private String content;
	
	private String destinationEmail;
	
	private String subject;
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd-MM-yyyy hh:mm:ss ")
	private Calendar timestamp;
	

}
