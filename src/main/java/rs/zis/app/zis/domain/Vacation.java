package rs.zis.app.zis.domain;
import rs.zis.app.zis.dto.VacationDTO;

import javax.persistence.*;

@SuppressWarnings("SpellCheckingInspection")
@Entity
public class Vacation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "startVacation", nullable = false)
    private long pocetak;

    @Column(name = "endVacation", nullable = false)
    private long kraj;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Doctor doctor;

    @Column(name = "active", nullable = false)
    private boolean active;
    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    public Vacation() {
    }

    public Vacation(Long id, long pocetak, long kraj, Doctor doctor, boolean active,boolean enabled) {
        this.id = id;
        this.pocetak = pocetak;
        this.kraj = kraj;
        this.doctor = doctor;
        this.active = active;
        this.enabled = enabled;
    }


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getPocetak() {
        return pocetak;
    }

    public void setPocetak(long pocetak) {
        this.pocetak = pocetak;
    }

    public long getKraj() {
        return kraj;
    }

    public void setKraj(long kraj) {
        this.kraj = kraj;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
