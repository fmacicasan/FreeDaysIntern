// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.domain;

import freedays.domain.RequestGranter;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;

privileged aspect RequestGranterDataOnDemand_Roo_DataOnDemand {
    
    declare @type: RequestGranterDataOnDemand: @Component;
    
    private Random RequestGranterDataOnDemand.rnd = new java.security.SecureRandom();
    
    private List<RequestGranter> RequestGranterDataOnDemand.data;
    
    public RequestGranter RequestGranterDataOnDemand.getNewTransientRequestGranter(int index) {
        freedays.domain.RequestGranter obj = new freedays.domain.RequestGranter();
        return obj;
    }
    
    public RequestGranter RequestGranterDataOnDemand.getSpecificRequestGranter(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        RequestGranter obj = data.get(index);
        return RequestGranter.findRequestGranter(obj.getId());
    }
    
    public RequestGranter RequestGranterDataOnDemand.getRandomRequestGranter() {
        init();
        RequestGranter obj = data.get(rnd.nextInt(data.size()));
        return RequestGranter.findRequestGranter(obj.getId());
    }
    
    public boolean RequestGranterDataOnDemand.modifyRequestGranter(RequestGranter obj) {
        return false;
    }
    
    public void RequestGranterDataOnDemand.init() {
        data = freedays.domain.RequestGranter.findRequestGranterEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'RequestGranter' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new java.util.ArrayList<freedays.domain.RequestGranter>();
        for (int i = 0; i < 10; i++) {
            freedays.domain.RequestGranter obj = getNewTransientRequestGranter(i);
            obj.persist();
            obj.flush();
            data.add(obj);
        }
    }
    
}
