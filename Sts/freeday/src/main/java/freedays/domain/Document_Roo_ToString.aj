// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package freedays.domain;

import java.lang.String;

privileged aspect Document_Roo_ToString {
    
    public String Document.toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Content: ").append(java.util.Arrays.toString(getContent())).append(", ");
        sb.append("ContentType: ").append(getContentType()).append(", ");
        sb.append("Description: ").append(getDescription()).append(", ");
        sb.append("Filename: ").append(getFilename()).append(", ");
        sb.append("Name: ").append(getName()).append(", ");
        sb.append("Size: ").append(getSize()).append(", ");
        sb.append("Url: ").append(getUrl());
        return sb.toString();
    }
    
}
