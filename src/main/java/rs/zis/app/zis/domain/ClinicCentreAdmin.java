package rs.zis.app.zis.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "ClinicCentreAdmin")
public class ClinicCentreAdmin extends User {

    @Column(name= "predefined")
    private boolean predefined;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    public ClinicCentreAdmin() {

    }

    public ClinicCentreAdmin(Long id, String mail, String password, boolean predefined, String firstName, String lastName, Timestamp lastPasswordResetDate, List<Authority> authorities) {
        super(mail, password, true, lastPasswordResetDate, authorities);
        this.predefined = predefined;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public boolean isPredefined() {
        return predefined;
    }

    public void setPredefined(boolean predefined) {
        this.predefined = predefined;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
