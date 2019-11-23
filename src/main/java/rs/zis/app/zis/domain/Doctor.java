package rs.zis.app.zis.domain;

import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Doctor")
public class Doctor extends User {

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "field")
    private String field;

  //  @ManyToMany(mappedBy = "appointments", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  //  private Set<Termin> appointmentList; // zakazani termini pregleda/operacija

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Clinic clinic;                     // klinika u kojoj je zaposlen

  //  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
 //   private Set<Godisnji_odmor> vacation;

    public Doctor() {
     //   appointmentList = new HashSet<Termin>();
     //   vacation = new HashSet<Godisnji_odmor>();
    }

    public Doctor(String mail, String password, String firstName, String lastName, String field, Set<Termin> appointmentList, Clinic clinic, Set<Godisnji_odmor> vacation,Timestamp lastPasswordResetDate, List<Authority> authorities) {
        super(mail, password, true, lastPasswordResetDate, authorities);
        this.firstName = firstName;
        this.lastName = lastName;
        this.field = field;
     //   this.appointmentList = appointmentList;
        this.clinic = clinic;
     //   this.vacation = vacation;
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
