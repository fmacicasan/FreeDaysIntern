// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import freedays.app.AppStrategLTop;
import java.lang.Long;
import java.util.List;
import javax.persistence.Entity;

privileged aspect AppStrategLTop_Roo_Entity {
    
    declare @type: AppStrategLTop: @Entity;
    
    public static long AppStrategLTop.countAppStrategLTops() {
        return entityManager().createQuery("SELECT COUNT(o) FROM AppStrategLTop o", Long.class).getSingleResult();
    }
    
    public static List<AppStrategLTop> AppStrategLTop.findAllAppStrategLTops() {
        return entityManager().createQuery("SELECT o FROM AppStrategLTop o", AppStrategLTop.class).getResultList();
    }
    
    public static AppStrategLTop AppStrategLTop.findAppStrategLTop(Long id) {
        if (id == null) return null;
        return entityManager().find(AppStrategLTop.class, id);
    }
    
    public static List<AppStrategLTop> AppStrategLTop.findAppStrategLTopEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM AppStrategLTop o", AppStrategLTop.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
