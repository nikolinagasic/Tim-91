package rs.zis.app.zis.domain;

import javax.persistence.*;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
@Entity
public class TipPregleda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public TipPregleda(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TipPregleda() {
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
