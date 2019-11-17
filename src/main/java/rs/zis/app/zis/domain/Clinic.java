package rs.zis.app.zis.domain;

import javax.persistence.*;
import java.util.ArrayList;

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

//    @OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private ArrayList<Lekar> doctors=new ArrayList<Lekar>();
//
//    @OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private ArrayList<Sala> rooms;
//
//    @OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private ArrayList<Termin> free_term;
//
//    @OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private ArrayList<Float> pricelist;

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
