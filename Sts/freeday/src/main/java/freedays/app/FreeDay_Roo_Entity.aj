// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import freedays.app.FreeDay;
import java.lang.Integer;
import java.lang.Long;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PersistenceContext;
import javax.persistence.Version;
import org.springframework.transaction.annotation.Transactional;

privileged aspect FreeDay_Roo_Entity {
    
    declare @type: FreeDay: @Entity;
    
    declare @type: FreeDay: @Inheritance(strategy = InheritanceType.SINGLE_TABLE);
    
    @PersistenceContext
    transient EntityManager FreeDay.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long FreeDay.id;
    
    @Version
    @Column(name = "version")
    private Integer FreeDay.version;
    
    public Long FreeDay.getId() {
        return this.id;
    }
    
    public void FreeDay.setId(Long id) {
        this.id = id;
    }
    
    public Integer FreeDay.getVersion() {
        return this.version;
    }
    
    public void FreeDay.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void FreeDay.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void FreeDay.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            FreeDay attached = FreeDay.findFreeDay(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void FreeDay.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void FreeDay.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public FreeDay FreeDay.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        FreeDay merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager FreeDay.entityManager() {
        EntityManager em = new FreeDay() {
            protected java.util.Calendar getDate() { throw new UnsupportedOperationException(); }
            protected void setDate(java.util.Calendar date) { throw new UnsupportedOperationException(); }
            protected freedays.app.FreeDay.FreeDayStatus getApproveStatus() { throw new UnsupportedOperationException(); }
            protected void initialize(freedays.app.FreeDayRequest fdr) { throw new UnsupportedOperationException(); }
            protected void finalizeFail() { throw new UnsupportedOperationException(); }
        }.entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long FreeDay.countFreeDays() {
        return entityManager().createQuery("SELECT COUNT(o) FROM FreeDay o", Long.class).getSingleResult();
    }
    
    public static List<FreeDay> FreeDay.findAllFreeDays() {
        return entityManager().createQuery("SELECT o FROM FreeDay o", FreeDay.class).getResultList();
    }
    
    public static FreeDay FreeDay.findFreeDay(Long id) {
        if (id == null) return null;
        return entityManager().find(FreeDay.class, id);
    }
    
    public static List<FreeDay> FreeDay.findFreeDayEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM FreeDay o", FreeDay.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
