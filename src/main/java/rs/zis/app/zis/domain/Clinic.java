package rs.zis.app.zis.domain;

import javax.persistence.*;
import java.util.*;

@SuppressWarnings("SpellCheckingInspection")
@Entity
@Table(name = "Clinic")
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private double rating;

    @Column(name = "location")
    private String location;

    // hocu da se formira medjutabela (clinic_doctor) koja ce namapirati ID klinike na ID doktora
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Doctor> doctors = new HashSet<Doctor>();

//    @OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)v
//    private Set<ClinicAdministrator> clinic_admin = new HashSet<ClinicAdministrator>();

    public Clinic(Long id, String name, String address, String description, Set<ClinicAdministrator> clinic_admin,
                  Set<Doctor> doctor, String location) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
//        this.clinic_admin = clinic_admin;
        this.doctors = doctor;
        this.location = location;
    }

    public Clinic() {
    }

    public Set<Doctor> getDoctors() {
        return doctors;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
