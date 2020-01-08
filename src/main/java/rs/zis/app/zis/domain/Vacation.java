package rs.zis.app.zis.domain;
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

    public Vacation() {
    }

    public Vacation(Long id, long pocetak, long kraj, Doctor doctor, boolean active) {
        this.id = id;
        this.pocetak = pocetak;
        this.kraj = kraj;
        this.doctor = doctor;
        this.active = active;
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
