package com.springsource.roo.pizzashop.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RooJavaBean
@RooToString
@RooEntity
public class Base {

    @NotNull
    @Size(min = 2)
    private String name;
    
    @NotNull
    @Size(min = 2)
    private String origin;

//	public String getOrigin() {
//        return this.origin;
//    }
}
