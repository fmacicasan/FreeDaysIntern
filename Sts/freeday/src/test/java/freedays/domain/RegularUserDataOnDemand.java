package freedays.domain;

import org.springframework.roo.addon.dod.RooDataOnDemand;

@RooDataOnDemand(entity = RegularUser.class)
public class RegularUserDataOnDemand {

	public void setEmail(RegularUser obj, int index) {
        java.lang.String email = "email_" + index +"@test.tst";
        obj.setEmail(email);
    }
}
