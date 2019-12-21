package rs.zis.app.zis.domain;

import javax.persistence.*;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@Entity
public class DoctorTerms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private long date;

    @Column(name = "start_term", nullable = false)
    private int start_term;

    @Column(name = "end_term", nullable = false)
    private int end_term;

    // formira se medjutabela koja mi odslikava koji je doktor zauzet u kom terminu
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="doctor_id", referencedColumnName="id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="patient_id", referencedColumnName="id")
    private Patient patient;

    // potrebno za logicko brisanje
    @Column(name = "active", nullable = false)
    private boolean active;

    public DoctorTerms() {
        this.active = true;
    }

    public DoctorTerms(Long id, long date, int start_term, int end_term, Doctor doctor, Patient patient, boolean active) {
        this.id = id;
        this.date = date;
        this.start_term = start_term;
        this.end_term = end_term;
        this.doctor = doctor;
        this.patient = patient;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getStart_term() {
        return start_term;
    }

    public void setStart_term(int start_term) {
        this.start_term = start_term;
    }

    public int getEnd_term() {
        return end_term;
    }

    public void setEnd_term(int end_term) {
        this.end_term = end_term;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
