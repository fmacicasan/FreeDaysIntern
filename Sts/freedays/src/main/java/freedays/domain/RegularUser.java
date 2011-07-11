package freedays.domain;

import org.springframework.roo.addon.entity.RooEntity;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import javax.validation.constraints.Size;

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
}
