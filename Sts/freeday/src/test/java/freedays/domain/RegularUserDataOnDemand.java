package freedays.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = RegularUser.class)
public class RegularUserDataOnDemand {

	public void setEmail(RegularUser obj, int index) {
        java.lang.String email = "email_" + index +"@test.tst";
        obj.setEmail(email);
    }
	
	public static RegularUser generateRegularUser(){
		RegularUserDataOnDemand rudod = new RegularUserDataOnDemand();
		return rudod.getRandomRegularUser();
	}

	public void setUsername(RegularUser obj, int index) {
        String username = "username_" + index + "@testnew.tst";
        obj.setUsername(username);
    }
}
