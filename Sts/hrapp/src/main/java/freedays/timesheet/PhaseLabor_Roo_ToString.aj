// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.timesheet;

import java.lang.String;

privileged aspect PhaseLabor_Roo_ToString {
    
    public String PhaseLabor.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Id: ").append(getId()).append(", ");
        sb.append("Laborbilling: ").append(getLaborbilling()).append(", ");
        sb.append("Pattern: ").append(getPattern()).append(", ");
        sb.append("Percentage: ").append(getPercentage()).append(", ");
        sb.append("Phase: ").append(getPhase()).append(", ");
        sb.append("Project: ").append(getProject()).append(", ");
        sb.append("Version: ").append(getVersion()).append(", ");
        sb.append("Deletable: ").append(isDeletable());
        return sb.toString();
    }
    
}
