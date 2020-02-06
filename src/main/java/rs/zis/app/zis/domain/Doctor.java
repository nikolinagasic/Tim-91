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

    @Column(name = "price")         // cena pregleda
    private double price;

    @Column(name= "role")
    private String role;

    @Column(name= "work_shift")     // smena
    private int workShift;

    @Column(name= "sum_ratings")        // suma svih recenzija
    private double sum_ratings;

    @Column(name= "number_ratings")        // broj recenzija
    private double number_rating;

    @Column(name= "discount")       // popust na pregled (% - u procentima)
    private int discount;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private TipPregleda tip;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Clinic clinic;

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Vacation> vacation = new HashSet<>();


    public Doctor() {
        this.role = "doctor";
        this.workShift = 1;
        this.discount = 0;
        this.sum_ratings = 0;
        this.number_rating = 0;
    }

    public Doctor(String mail, String password, String firstName, String lastName, Clinic clinic, TipPregleda tip, double price,
                  Set<Vacation> vacation, Timestamp lastPasswordResetDate, List<Authority> authorities, boolean firstLogin, 
                  int workShift, int discount, double sum_ratings, double number_rating) {
        super(mail, password, true, lastPasswordResetDate, authorities, firstLogin);
        this.firstName = firstName;
        this.lastName = lastName;
        this.clinic = clinic;
        this.tip = tip;
        this.role = "doctor";
        this.workShift = workShift;
        this.discount = discount;
        this.sum_ratings = sum_ratings;
        this.number_rating = number_rating;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public int getWorkShift() {
        return workShift;
    }

    public void setWorkShift(int workShift) {
        this.workShift = workShift;
    }

    public double getSum_ratings() {
        return sum_ratings;
    }

    public void setSum_ratings(double sum_ratings) {
        this.sum_ratings = sum_ratings;
    }

    public double getNumber_rating() {
        return number_rating;
    }

    public void setNumber_rating(double number_rating) {
        this.number_rating = number_rating;
    }

}
