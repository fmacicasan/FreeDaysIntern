// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.domain;

import java.lang.String;

privileged aspect ApplicationRegularUser_Roo_ToString {
    
    public String ApplicationRegularUser.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("RegularUser: ").append(getRegularUser()).append(", ");
        sb.append("Roles: ").append(getRoles() == null ? "null" : getRoles().size()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
    
}
