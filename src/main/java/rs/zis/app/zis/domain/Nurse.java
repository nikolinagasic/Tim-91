package rs.zis.app.zis.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Nurse")
public class Nurse extends User{

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name= "role")
    private String role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Clinic clinic;                     // klinika u kojoj je zaposlen

 //   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
 //   private Set<Godisnji_odmor> vacation;

    public Nurse() {
        this.role = "nurse";
      //  vacation = new HashSet<Godisnji_odmor>();
    }

    public Nurse(String mail, String password, String firstName, String lastName, Clinic clinic, Set<Vacation> vacation, Timestamp lastPasswordResetDate, List<Authority> authorities) {
        super(mail, password,true, lastPasswordResetDate, authorities);
        this.firstName = firstName;
        this.lastName = lastName;
        this.clinic = clinic;
        this.role = "nurse";
      //  this.vacation = vacation;
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

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

  /*  public Set<Godisnji_odmor> getVacation() {
        return vacation;
    }

    public void setVacation(Set<Godisnji_odmor> vacation) {
        this.vacation = vacation;
    }
*/
}
