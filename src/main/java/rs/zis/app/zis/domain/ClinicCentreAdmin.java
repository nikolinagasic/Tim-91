package rs.zis.app.zis.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
@Entity
@Table(name = "ClinicCentreAdmin")
public class ClinicCentreAdmin extends Users {

    @Column(name= "predefined")
    private boolean predefined;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name= "role")
    private String role;

    public ClinicCentreAdmin() {
        this.role = "ccadmin";
    }

    public ClinicCentreAdmin(Long id, String mail, String password, boolean predefined, String firstName, String lastName, Timestamp lastPasswordResetDate, List<Authority> authorities) {
        super(mail, password, true, lastPasswordResetDate, authorities);
        this.predefined = predefined;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = "ccadmin";
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
