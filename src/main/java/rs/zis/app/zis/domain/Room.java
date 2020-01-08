package rs.zis.app.zis.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@SuppressWarnings("SpellCheckingInspection")
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Column(name = "enabled", nullable = false)
        private boolean enabled;
        @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
        private Clinic clinic;

        public Room(Long id, String name, String number, Clinic clinic) {
            this.id = id;
            this.name = name;
            this.number = number;
            this.clinic = clinic;
            this.enabled = true;
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

}
