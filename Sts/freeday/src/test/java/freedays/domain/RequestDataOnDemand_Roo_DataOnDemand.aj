// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.domain;

import freedays.app.FreeDay;
import freedays.app.FreeDayDataOnDemand;
import freedays.app.RequestStatus;
import freedays.domain.ApplicationRegularUser;
import freedays.domain.Request;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

privileged aspect RequestDataOnDemand_Roo_DataOnDemand {
    
    declare @type: RequestDataOnDemand: @Component;
    
    private Random RequestDataOnDemand.rnd = new SecureRandom();
    
    private List<Request> RequestDataOnDemand.data;
    
    @Autowired
    private FreeDayDataOnDemand RequestDataOnDemand.freeDayDataOnDemand;
    
    public Request RequestDataOnDemand.getNewTransientRequest(int index) {
        Request obj = new Request();
        setAppreguser(obj, index);
        setApprover(obj, index);
        setRequestable(obj, index);
        setStatus(obj, index);
        return obj;
    }
    
    public void RequestDataOnDemand.setAppreguser(Request obj, int index) {
        ApplicationRegularUser appreguser = null;
        obj.setAppreguser(appreguser);
    }
    
    public void RequestDataOnDemand.setApprover(Request obj, int index) {
        ApplicationRegularUser approver = null;
        obj.setApprover(approver);
    }
    
    public void RequestDataOnDemand.setRequestable(Request obj, int index) {
        FreeDay requestable = freeDayDataOnDemand.getSpecificFreeDay(index);
        obj.setRequestable(requestable);
    }
    
    public void RequestDataOnDemand.setStatus(Request obj, int index) {
        RequestStatus status = RequestStatus.class.getEnumConstants()[0];
        obj.setStatus(status);
    }
    
    public Request RequestDataOnDemand.getSpecificRequest(int index) {
        init();
        if (index < 0) index = 0;
        if (index > (data.size() - 1)) index = data.size() - 1;
        Request obj = data.get(index);
        return Request.findRequest(obj.getId());
    }
    
    public Request RequestDataOnDemand.getRandomRequest() {
        init();
        Request obj = data.get(rnd.nextInt(data.size()));
        return Request.findRequest(obj.getId());
    }
    
    public boolean RequestDataOnDemand.modifyRequest(Request obj) {
        return false;
    }
    
    public void RequestDataOnDemand.init() {
        data = Request.findRequestEntries(0, 10);
        if (data == null) throw new IllegalStateException("Find entries implementation for 'Request' illegally returned null");
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<freedays.domain.Request>();
        for (int i = 0; i < 10; i++) {
            Request obj = getNewTransientRequest(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> it = e.getConstraintViolations().iterator(); it.hasNext();) {
                    ConstraintViolation<?> cv = it.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}
