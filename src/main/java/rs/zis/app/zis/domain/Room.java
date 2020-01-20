package rs.zis.app.zis.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@SuppressWarnings({"SpellCheckingInspection", "unused"})
@Entity
@Table(name = "Room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "number", nullable = false)
    private String number;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Clinic clinic;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DoctorTerms> doctorTerms = new ArrayList<>();

    public Room(Long id, String name, String number, Clinic clinic, List<DoctorTerms> doctorTerms) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.clinic = clinic;
        this.enabled = true;
        this.doctorTerms = doctorTerms;
    }

    public Room() {
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public List<DoctorTerms> getDoctorTerms() {
        return doctorTerms;
    }

    public void setDoctorTerms(List<DoctorTerms> doctorTerms) {
        this.doctorTerms = doctorTerms;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
