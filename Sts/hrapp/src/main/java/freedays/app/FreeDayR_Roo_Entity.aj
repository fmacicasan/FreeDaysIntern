// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import freedays.app.FreeDayR;
import java.lang.Long;
import java.util.List;
import javax.persistence.Entity;

privileged aspect FreeDayR_Roo_Entity {
    
    declare @type: FreeDayR: @Entity;
    
    public static long FreeDayR.countFreeDayRs() {
        return entityManager().createQuery("SELECT COUNT(o) FROM FreeDayR o", Long.class).getSingleResult();
    }
    
    public static List<FreeDayR> FreeDayR.findAllFreeDayRs() {
        return entityManager().createQuery("SELECT o FROM FreeDayR o", FreeDayR.class).getResultList();
    }
    
    public static FreeDayR FreeDayR.findFreeDayR(Long id) {
        if (id == null) return null;
        return entityManager().find(FreeDayR.class, id);
    }
    
    public static List<FreeDayR> FreeDayR.findFreeDayREntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM FreeDayR o", FreeDayR.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}