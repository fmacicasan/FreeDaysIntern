// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

import org.springframework.transaction.annotation.Transactional;

privileged aspect RegularUser_Roo_Entity {
    
    declare @type: RegularUser: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long RegularUser.id;
    
    @Version
    @Column(name = "version")
    private Integer RegularUser.version;
    
    public Long RegularUser.getId() {
        return this.id;
    }
    
    public void RegularUser.setId(Long id) {
        this.id = id;
    }
    
    public Integer RegularUser.getVersion() {
        return this.version;
    }
    
    public void RegularUser.setVersion(Integer version) {
        this.version = version;
    }
    
        
    @Transactional
    public void RegularUser.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void RegularUser.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public RegularUser RegularUser.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        RegularUser merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
