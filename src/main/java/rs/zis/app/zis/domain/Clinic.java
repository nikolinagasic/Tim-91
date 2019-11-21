package rs.zis.app.zis.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Clinic")
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mail", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "description", nullable = true)
    private String description;

    // inicijalizovati u konstruktoru
    @OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ClinicAdministrator> clinic_admin = new HashSet<ClinicAdministrator>();

//    @OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private ArrayList<Sala> rooms;
//
//    @OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private ArrayList<Termin> free_term;
//
//    @OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private ArrayList<Float> pricelist;

    public Clinic(Long id, String name, String address, String description, Set<ClinicAdministrator> clinic_admin) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.clinic_admin = clinic_admin;
    }

    public Clinic() {
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

//    public ArrayList<Lekar> getDoctors() {
//        return doctors;
//    }
//
//    public void setDoctors(ArrayList<Lekar> doctors) {
//        this.doctors = doctors;
//    }
//
//    public ArrayList<Sala> getRooms() {
//        return rooms;
//    }
//
//    public void setRooms(ArrayList<Sala> rooms) {
//        this.rooms = rooms;
//    }
//
//    public ArrayList<Termin> getFree_term() {
//        return free_term;
//    }
//
//    public void setFree_term(ArrayList<Termin> free_term) {
//        this.free_term = free_term;
//    }
//
//    public ArrayList<Float> getPricelist() {
//        return pricelist;
//    }
//
//    public void setPricelist(ArrayList<Float> pricelist) {
//        this.pricelist = pricelist;
//    }
}
