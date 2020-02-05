package rs.zis.app.zis.domain;

import javax.persistence.*;
import java.util.*;

@SuppressWarnings({"SpellCheckingInspection", "unused", "Convert2Diamond"})
@Entity
@Table(name = "Clinic")
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Patient> patient_rec = new HashSet<>();

    @Column(name = "location")
    private String location;

    @Column(name = "sum_ratings")               // suma svih glasova
    private double sum_ratings;

    @Column(name = "number_ratings")            // broj pacijenata koji su glasali
    private int number_ratings;

    // hocu da se formira medjutabela (clinic_doctor) koja ce namapirati ID klinike na ID doktora
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Doctor> doctors = new HashSet<Doctor>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Room> rooms = new HashSet<Room>();

    @OneToOne(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ClinicAdministrator clinic_admin;

    public Clinic() {
        this.sum_ratings = 0;
        this.number_ratings = 0;
    }

    public Clinic(Long id, String name, String address, String description, ClinicAdministrator clinic_admin,
                  Set<Doctor> doctor, String location, double sum_ratings, int number_ratings) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.clinic_admin = clinic_admin;
        this.doctors = doctor;
        this.location = location;
        this.sum_ratings = sum_ratings;
        this.number_ratings = number_ratings;
    }

    public Set<Doctor> getDoctors() {
        return doctors;
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
    public Set<Room> getRooms() {
        return rooms;
    }

    public void setDoctors(Set<Doctor> doctors) {
        this.doctors = doctors;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;
    }

    public ClinicAdministrator getClinic_admin() {
        return clinic_admin;
    }

    public void setClinic_admin(ClinicAdministrator clinic_admin) {
        this.clinic_admin = clinic_admin;
    }

    public Set<Patient> getPatient_rec() {
        return patient_rec;
    }

    public void setPatient_rec(Set<Patient> patient_rec) {
        this.patient_rec = patient_rec;
    }

    public double getSum_ratings() {
        return sum_ratings;
    }

    public void setSum_ratings(double sum_ratings) {
        this.sum_ratings = sum_ratings;
    }

    public int getNumber_ratings() {
        return number_ratings;
    }

    public void setNumber_ratings(int number_ratings) {
        this.number_ratings = number_ratings;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
