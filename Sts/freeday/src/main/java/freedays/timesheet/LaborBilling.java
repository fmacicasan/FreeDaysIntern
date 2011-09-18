package freedays.timesheet;

import java.util.List;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;

import freedays.domain.RegularUser;

import javax.validation.constraints.NotNull;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@RooJavaBean
@RooToString
@RooEntity
public class LaborBilling {

    @NotNull
    //@Column(unique = true)
    private String code;

    @NotNull
    private String name;
    
    public static LaborBilling getSpecialLaborBilling(String code) {
    	EntityManager em = RegularUser.entityManager();
        TypedQuery<LaborBilling> q = em.createQuery("SELECT o FROM LaborBilling AS o WHERE o.code = :code ", LaborBilling.class);
        q.setParameter("code", code);        
        List<LaborBilling> results = q.getResultList();
        if (!results.isEmpty())
           return  results.get(0);
        else
           return null;

    }
}
