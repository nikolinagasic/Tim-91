package rs.zis.app.zis.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@SuppressWarnings("SpellCheckingInspection")
@Entity
@Table(name = "ClinicAdministrator")
public class ClinicAdministrator extends Users {

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "telephone")
    private long telephone;

    @Column(name= "role")
    private String role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Clinic clinic;

    public ClinicAdministrator() {
        this.role = "cadmin";
    }

    public ClinicAdministrator(Long id, String mail, String password, String firstName, String lastName, Clinic clinic,Timestamp lastPasswordResetDate, List<Authority> authorities) {
        super(mail, password, true, lastPasswordResetDate, authorities);
        this.firstName = firstName;
        this.lastName = lastName;
        this.clinic = clinic;
        this.role = "cadmin";
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Clinic getClinic() {
        return clinic;
    }
}
