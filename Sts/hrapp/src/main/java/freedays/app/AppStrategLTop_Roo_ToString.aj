// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.app;

import java.lang.String;

privileged aspect AppStrategLTop_Roo_ToString {
    
    public String AppStrategLTop.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Succesor: ").append(getSuccesor()).append(", ");
        sb.append("Version: ").append(getVersion());
        return sb.toString();
    }
    
}
