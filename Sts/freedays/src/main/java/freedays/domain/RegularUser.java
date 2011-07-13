package freedays.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.util.Calendar;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooEntity
public class RegularUser {

    @NotNull
    @Column(unique = true)
    @Size(min = 3)
    private String username;

    @NotNull
    @Size(min = 6)
    private String password;

    @NotNull
    private String email;

    @NotNull
    private String surename;

    @NotNull
    private String firstname;

    @NotNull
    private Boolean deleted;

    @NotNull
    private Boolean activ;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Calendar lastmodified;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Calendar creationdate;

    @ManyToOne
    private freedays.domain.RegularUser usermodify;
}
