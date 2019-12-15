package rs.zis.app.zis.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.print.Doc;
import java.util.*;

@SuppressWarnings("SpellCheckingInspection")
@Entity
@Table(name = "Clinic")
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private String rating;

    @Column(name = "location")
    private String location;

    // hocu da se formira medjutabela (clinic_doctor) koja ce namapirati ID klinike na ID doktora
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Doctor> doctor = new HashSet<Doctor>();

//    @OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)v
//    private Set<ClinicAdministrator> clinic_admin = new HashSet<ClinicAdministrator>();

    public Clinic(Long id, String name, String address, String description, Set<ClinicAdministrator> clinic_admin,
                  Set<Doctor> doctor, String location) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
//        this.clinic_admin = clinic_admin;
        this.doctor = doctor;
        this.location = location;
    }

    public Clinic() {
    }

    public Set<Doctor> getDoctor() {
        return doctor;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
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
