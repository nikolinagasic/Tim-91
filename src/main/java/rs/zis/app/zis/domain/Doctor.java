package rs.zis.app.zis.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@Entity
@Table(name = "Doctor")
public class Doctor extends Users {

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "field")
    private String field;

    @Column(name= "role")
    private String role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private TipPregleda tip;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Clinic clinic;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Vacation> vacation = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DoctorTerms> busy_terms = new HashSet<>();

    public Doctor() {
     //   appointmentList = new HashSet<Termin>();
     //   vacation = new HashSet<Godisnji_odmor>();
        this.role = "doctor";
    }

    public Doctor(String mail, String password, String firstName, String lastName, String field, Set<Vacation> vacation, Timestamp lastPasswordResetDate, List<Authority> authorities) {
        super(mail, password, true, lastPasswordResetDate, authorities);
        this.firstName = firstName;
        this.lastName = lastName;
        this.field = field;
        this.role = "doctor";
     //   this.vacation = vacation;
    }

    public TipPregleda getTip() {
        return tip;
    }

    public void setTip(TipPregleda tip) {
        this.tip = tip;
    }

    public Set<Vacation> getVacation() {
        return vacation;
    }

    public void setVacation(Set<Vacation> vacation) {
        this.vacation = vacation;
    }

    public Set<DoctorTerms> getBusy_terms() {
        return busy_terms;
    }

    public void setBusy_terms(Set<DoctorTerms> busy_terms) {
        this.busy_terms = busy_terms;
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

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
/*    public Set<Termin> getAppointmentList() {
        return appointmentList;
    }

    public void setAppointmentList(Set<Termin> appointmentList) {
        this.appointmentList = appointmentList;
    }
*/
    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

 /*   public Set<Godisnji_odmor> getVacation() {
        return vacation;
    }

    public void setVacation(Set<Godisnji_odmor> vacation) {
        this.vacation = vacation;
    }
*/

}
