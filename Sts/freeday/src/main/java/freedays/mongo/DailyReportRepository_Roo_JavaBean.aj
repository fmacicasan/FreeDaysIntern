// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.mongo;

import org.springframework.data.document.mongodb.MongoTemplate;

privileged aspect DailyReportRepository_Roo_JavaBean {
    
    public MongoTemplate DailyReportRepository.getMongoTemplate() {
        return this.mongoTemplate;
    }
    
    public void DailyReportRepository.setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    
}