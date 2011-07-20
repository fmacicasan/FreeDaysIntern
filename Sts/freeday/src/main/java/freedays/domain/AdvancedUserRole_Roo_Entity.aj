// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.domain;

import freedays.domain.AdvancedUserRole;
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

privileged aspect AdvancedUserRole_Roo_Entity {
    
    declare @type: AdvancedUserRole: @Entity;
    
    declare @type: AdvancedUserRole: @Inheritance(strategy = InheritanceType.SINGLE_TABLE);
    
    @PersistenceContext
    transient EntityManager AdvancedUserRole.entityManager;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long AdvancedUserRole.id;
    
    @Version
    @Column(name = "version")
    private Integer AdvancedUserRole.version;
    
    public Long AdvancedUserRole.getId() {
        return this.id;
    }
    
    public void AdvancedUserRole.setId(Long id) {
        this.id = id;
    }
    
    public Integer AdvancedUserRole.getVersion() {
        return this.version;
    }
    
    public void AdvancedUserRole.setVersion(Integer version) {
        this.version = version;
    }
    
    @Transactional
    public void AdvancedUserRole.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void AdvancedUserRole.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            AdvancedUserRole attached = AdvancedUserRole.findAdvancedUserRole(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void AdvancedUserRole.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void AdvancedUserRole.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public AdvancedUserRole AdvancedUserRole.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        AdvancedUserRole merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager AdvancedUserRole.entityManager() {
        EntityManager em = new AdvancedUserRole().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long AdvancedUserRole.countAdvancedUserRoles() {
        return entityManager().createQuery("SELECT COUNT(o) FROM AdvancedUserRole o", Long.class).getSingleResult();
    }
    
    public static List<AdvancedUserRole> AdvancedUserRole.findAllAdvancedUserRoles() {
        return entityManager().createQuery("SELECT o FROM AdvancedUserRole o", AdvancedUserRole.class).getResultList();
    }
    
    public static AdvancedUserRole AdvancedUserRole.findAdvancedUserRole(Long id) {
        if (id == null) return null;
        return entityManager().find(AdvancedUserRole.class, id);
    }
    
    public static List<AdvancedUserRole> AdvancedUserRole.findAdvancedUserRoleEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM AdvancedUserRole o", AdvancedUserRole.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
