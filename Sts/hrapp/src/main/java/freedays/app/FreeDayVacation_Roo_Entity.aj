// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import freedays.app.FreeDayVacation;
import java.lang.Long;
import java.util.List;
import javax.persistence.Entity;

privileged aspect FreeDayVacation_Roo_Entity {
    
    declare @type: FreeDayVacation: @Entity;
    
    public static long FreeDayVacation.countFreeDayVacations() {
        return entityManager().createQuery("SELECT COUNT(o) FROM FreeDayVacation o", Long.class).getSingleResult();
    }
    
    public static List<FreeDayVacation> FreeDayVacation.findAllFreeDayVacations() {
        return entityManager().createQuery("SELECT o FROM FreeDayVacation o", FreeDayVacation.class).getResultList();
    }
    
    public static FreeDayVacation FreeDayVacation.findFreeDayVacation(Long id) {
        if (id == null) return null;
        return entityManager().find(FreeDayVacation.class, id);
    }
    
    public static List<FreeDayVacation> FreeDayVacation.findFreeDayVacationEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM FreeDayVacation o", FreeDayVacation.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
