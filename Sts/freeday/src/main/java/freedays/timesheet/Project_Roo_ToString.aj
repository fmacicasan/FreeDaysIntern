// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.timesheet;

import java.lang.String;

privileged aspect Project_Roo_ToString {
    
    public String Project.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Code: ").append(getCode()).append(", ");
        sb.append("Name: ").append(getName()).append(", ");
        sb.append("PhaseLst: ").append(getPhaseLst() == null ? "null" : getPhaseLst().size());
        return sb.toString();
    }
    
}
