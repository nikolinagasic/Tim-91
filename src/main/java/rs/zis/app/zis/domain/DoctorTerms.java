package rs.zis.app.zis.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"SpellCheckingInspection", "unused"})
@Entity
public class DoctorTerms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date", nullable = false)
    private long date;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="term_definition_id", referencedColumnName="id")
    private TermDefinition term;

    @Column(name = "report", nullable = false)          // izvestaj za taj pregled
    private String report;

    // JoinColumn ce uzeti samo reference iz doktora (doktor ne vidi ove termine)
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

    public DoctorTerms(Long id, long date, Doctor doctor, Patient patient, boolean active, String report, TermDefinition termDefinition) {
        this.id = id;
        this.date = date;
        this.doctor = doctor;
        this.patient = patient;
        this.active = active;
        this.report = report;
        this.term = termDefinition;
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

    public TermDefinition getTerm() {
        return term;
    }

    public void setTerm(TermDefinition term) {
        this.term = term;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
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
