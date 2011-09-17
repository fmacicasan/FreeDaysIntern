package freedays.security;

import freedays.domain.RegularUser;
import freedays.domain.RegularUserDataOnDemand;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = InfoChanger.class)
public class InfoChangerDataOnDemand {

	private static boolean FROM_DB = false;
	private Random rnd = new SecureRandom();
    
    private List<InfoChanger> data;
    
	public void setExpcode(InfoChanger obj, int index) {
        String expcode = obj.genToken();
        obj.setExpcode(expcode);
    }

	public void setExpdate(InfoChanger obj, int index) {
        Calendar expdate = InfoChanger.computeExpireDate();
        obj.setExpdate(expdate);
    }

	public void setExpired(InfoChanger obj, int index) {
        Boolean expired = Boolean.FALSE;
        obj.setExpired(expired);
    }

	public void setRegularUser(InfoChanger obj, int index) {
        RegularUser regularUser = new RegularUserDataOnDemand().getRandomRegularUser();
        obj.setRegularUser(regularUser);
    }

	public void setUsed(InfoChanger obj, int index) {
        Boolean used = Boolean.FALSE;
        obj.setUsed(used);
    }


	public InfoChanger getNewTransientInfoChanger(int index) {
        InfoChanger obj = new InfoChanger();
        setExpdate(obj, index);
        setExpired(obj, index);
        setRegularUser(obj, index);
        setUsed(obj, index);
        setExpcode(obj, index);
        return obj;
    }

	public void init() {
		if(FROM_DB){
	        data = InfoChanger.findInfoChangerEntries(0, 10);
	        if (data == null) throw new IllegalStateException("Find entries implementation for 'InfoChanger' illegally returned null");
	        if (!data.isEmpty()) {
	            return;
	        }
		}
        
        data = new ArrayList<freedays.security.InfoChanger>();
        for (int i = 0; i < 10; i++) {
            InfoChanger obj = getNewTransientInfoChanger(i);
            try {
            	System.out.println("Aaaaaaaaaaaaa"+obj);
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

	public InfoChanger getRandomTransientJustExpiredInfoChanger() {
		InfoChanger obj = new InfoChanger();
		int index = 0;
        setExpiredExpiredate(obj,index);
        setExpired(obj, index);
        setRegularUser(obj, index);
        setUsed(obj, index);
        setExpcode(obj, index);
        return obj;
	}
	
	public InfoChanger getRandomTransientJustCreatedInfoChanger() {
		InfoChanger obj = new InfoChanger();
		int index = 0;
        setExpdate(obj,index);
        setExpired(obj, index);
        setRegularUser(obj, index);
        setUsed(obj, index);
        setExpcode(obj, index);
        return obj;
	}

	public void setExpiredExpiredate(InfoChanger obj, int index) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, (-2)*InfoChanger.DEFAULT_EXPIRE_INTERVAL_IN_HOURS);
		obj.setExpdate(c);
	}
	
	public static void setFromDb(){
		InfoChangerDataOnDemand.FROM_DB = true;
	}
	public static void resetFromDb(){
		InfoChangerDataOnDemand.FROM_DB = false;
	}
}
