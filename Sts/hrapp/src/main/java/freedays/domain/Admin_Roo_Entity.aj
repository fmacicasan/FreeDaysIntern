// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.domain;

import freedays.domain.Admin;
import java.lang.Long;
import java.util.List;
import javax.persistence.Entity;

privileged aspect Admin_Roo_Entity {
    
    declare @type: Admin: @Entity;
    
    public static long Admin.countAdmins() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Admin o", Long.class).getSingleResult();
    }
    
    public static List<Admin> Admin.findAllAdmins() {
        return entityManager().createQuery("SELECT o FROM Admin o", Admin.class).getResultList();
    }
    
    public static Admin Admin.findAdmin(Long id) {
        if (id == null) return null;
        return entityManager().find(Admin.class, id);
    }
    
    public static List<Admin> Admin.findAdminEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Admin o", Admin.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}