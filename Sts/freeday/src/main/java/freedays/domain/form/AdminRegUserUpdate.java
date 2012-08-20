package freedays.domain.form;

import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;

@RooJavaBean
public class AdminRegUserUpdate {

    @NotNull
    @Column(unique = true)
    @Length(min = 3, max = 45, message = "#{messages['field_invalid_length']}")
    @Email(message = "#{messages['field_invalid_email']}")
    private String username;

    @NotNull
    private String surename;

    @NotNull
    private String firstname;

    private Boolean deleted;

    @NotNull
    private Boolean activ;
    

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "hh:mm:ss dd-MM-yyyy")
    private Calendar lastmodified;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "hh:mm:ss dd-MM-yyyy")
    private Calendar creationdate;

    private String usermodifier;

    @Transient
    private  Long id;
}
