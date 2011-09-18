package freedays.mongo;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.document.mongodb.MongoTemplate;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.stereotype.Repository;

import freedays.mongo.domain.DailyReport;
import freedays.mongo.domain.Person;


@RooJavaBean
@Repository("dailyReportRepository")
public class DailyReportRepository {
	
	static final Logger logger = LoggerFactory.getLogger(DailyReportRepository.class);

    @Autowired
    MongoTemplate mongoTemplate;
    
    public void logAllDailyReport() {
        List<DailyReport> results = mongoTemplate.findAll(DailyReport.class);
        logger.info("Total amount of DailyReport: {}", results.size());
        logger.info("Results: {}", results);
    }

    public void insertDailyReport(String email, String subject, String content) {

        DailyReport dl = new DailyReport();
        dl.setContent(content);
        dl.setDestinationEmail(email);
        dl.setSubject(subject);
        dl.setTimestamp(Calendar.getInstance());

        mongoTemplate.insert(dl);
    }

    /**
     * Create a {@link DailyReport} collection if the collection does not already exists
     */
    public void createDailyReportCollection() {
        if (!mongoTemplate.collectionExists(DailyReport.class)) {
            mongoTemplate.createCollection(DailyReport.class);
        }
    }

    /**
     * Drops the {@link DailyReport} collection if the collection does already exists
     */
    public void dropDailyReportCollection() {
        if (mongoTemplate.collectionExists(DailyReport.class)) {
            mongoTemplate.dropCollection(DailyReport.class);
        }
    }
    
    public void insertDailyReport(DailyReport dl){
    	mongoTemplate.insert(dl);
    }
    
    public List<DailyReport> findAllDailyReport(){
    	return mongoTemplate.findAll(DailyReport.class);
    }

}
