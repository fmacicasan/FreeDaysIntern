// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.domain;

import freedays.domain.ApplicationRegularUser;
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
import javax.persistence.Version;
import org.springframework.transaction.annotation.Transactional;

privileged aspect ApplicationRegularUser_Roo_Entity {
    
    declare @type: ApplicationRegularUser: @Entity;
    
    declare @type: ApplicationRegularUser: @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS);
    
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private Long ApplicationRegularUser.id;
    
    @Version
    @Column(name = "version")
    private Integer ApplicationRegularUser.version;
    
    public Long ApplicationRegularUser.getId() {
        return this.id;
    }
    
    public void ApplicationRegularUser.setId(Long id) {
        this.id = id;
    }
    
    public Integer ApplicationRegularUser.getVersion() {
        return this.version;
    }
    
    public void ApplicationRegularUser.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void ApplicationRegularUser.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void ApplicationRegularUser.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void ApplicationRegularUser.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public ApplicationRegularUser ApplicationRegularUser.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ApplicationRegularUser merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager ApplicationRegularUser.entityManager() {
        EntityManager em = new ApplicationRegularUser() {
        }.entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long ApplicationRegularUser.countApplicationRegularUsers() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ApplicationRegularUser o", Long.class).getSingleResult();
    }
    
    public static List<ApplicationRegularUser> ApplicationRegularUser.findAllApplicationRegularUsers() {
        return entityManager().createQuery("SELECT o FROM ApplicationRegularUser o", ApplicationRegularUser.class).getResultList();
    }
    
    public static ApplicationRegularUser ApplicationRegularUser.findApplicationRegularUser(Long id) {
        if (id == null) return null;
        return entityManager().find(ApplicationRegularUser.class, id);
    }
    
    public static List<ApplicationRegularUser> ApplicationRegularUser.findApplicationRegularUserEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ApplicationRegularUser o", ApplicationRegularUser.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}