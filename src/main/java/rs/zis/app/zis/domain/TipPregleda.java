package rs.zis.app.zis.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
@Entity
public class TipPregleda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "active", nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "tip",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Doctor> doctors = new ArrayList<Doctor>();

    public TipPregleda(Long id, String name) {
        this.id = id;
        this.name = name;
        this.active = true;
    }

    public TipPregleda() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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
}
