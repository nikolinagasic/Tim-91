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
public class Doctor implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mail", nullable = false)
    private String mail;

    @Column(name = "password", nullable = false)
    private String password;

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

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;

    public Doctor() {
     //   appointmentList = new HashSet<Termin>();
     //   vacation = new HashSet<Godisnji_odmor>();
    }

    public Doctor(String mail, String password, String firstName, String lastName, String field, Set<Termin> appointmentList, Clinic clinic, Set<Godisnji_odmor> vacation,Timestamp lastPasswordResetDate, List<Authority> authorities) {
        this.mail = mail;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.field = field;
     //   this.appointmentList = appointmentList;
        this.clinic = clinic;
     //   this.vacation = vacation;
        this.lastPasswordResetDate = lastPasswordResetDate;
        this.authorities = authorities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Timestamp now = new Timestamp(DateTime.now().getMillis());
        this.setLastPasswordResetDate(now);
        this.password = password;
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
    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }
    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return this.mail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
