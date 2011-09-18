package freedays.mongo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.roo.addon.javabean.RooJavaBean;

import freedays.schedule.FreeDayScheduleServiceImpl;
import freedays.util.PhraseUtils;

/**
 * Small MongoDB application that uses spring data to interact with MongoDB.
 * 
 * @author Jeroen Reijn
 */
@RooJavaBean
@Configurable
public class MongoDBApp {

    static final Logger logger = LoggerFactory.getLogger(MongoDBApp.class);

    @Autowired
    ApplicationContext applicationContext;
    
    @Autowired
    PersonRepository personRepository;
    
    @Autowired
    DailyReportRepository dailyReportRepository;
    
    @Autowired
    FreeDayScheduleServiceImpl scheduleService;
    
    public  void execute(){
		logger.info("Bootstrapping MongoDemo application");
		logger.info("app context"+applicationContext);


//        PersonRepository personRepository = applicationContext.getBean(PersonRepository.class);

        // cleanup person collection before insertion
        personRepository.dropPersonCollection();

        //create person collection
        personRepository.createPersonCollection();

        for(int i=0; i<20; i++) {
            personRepository.insertPersonWithNameJohnAndRandomAge();
        }

        personRepository.logAllPersons();
        logger.info("Avarage age of a person is: {}", personRepository.getAvarageAgeOfPerson());

        logger.info("Finished MongoDemo application");
        
        logger.info("daily report thingy");
        
        dailyReportRepository.dropDailyReportCollection();
        dailyReportRepository.createDailyReportCollection();
        for(int i=1;i<20;i++){
        	dailyReportRepository.insertDailyReport(PhraseUtils.getRandomPhrase(),"daily report","mongo@sdl.com");
        }
        
        
        for(int i=0;i<10;i++){
        	scheduleService.reportFreeDays();
        }
        logger.info("finished daily report thingy");
    }
}
