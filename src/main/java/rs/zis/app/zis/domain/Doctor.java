package rs.zis.app.zis.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

    @Column(name = "price")
    private double price;           // cena pregleda

    @Column(name= "role")
    private String role;

    @Column(name= "rating")     // prosecna ocena
    private double rating;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private TipPregleda tip;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Clinic clinic;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Vacation> vacation = new HashSet<>();

//    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Set<DoctorTerms> busy_terms = new HashSet<>();

    public Doctor() {
        this.role = "doctor";
    }

    public Doctor(String mail, String password, String firstName, String lastName, Clinic clinic, TipPregleda tip, double price, Set<Vacation> vacation,
                  Timestamp lastPasswordResetDate, List<Authority> authorities, boolean firstLogin) {
        super(mail, password, true, lastPasswordResetDate, authorities, firstLogin);
        this.firstName = firstName;
        this.lastName = lastName;
        this.clinic = clinic;
        this.tip = tip;
        this.role = "doctor";
     //   this.vacation = vacation;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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
