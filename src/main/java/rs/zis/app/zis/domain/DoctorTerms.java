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

    @Column(name = "active", nullable = false)          // potrebno za logicko brisanje
    private boolean active;

    @Column(name = "examination", nullable = false)          // pregled -> true, operacija -> false
    private boolean examination;

    @Column(name = "processed_by_cadmin", nullable = false)    // da li ga je admin klinike obradio
    private boolean processedByAdmin;

    @Column(name = "predefined", nullable = false)    // da li je/nije unapred definisan
    private boolean predefined;

    // JoinColumn ce uzeti samo reference iz doktora (doktor ne vidi ove termine)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="doctor_id", referencedColumnName="id")
    private Doctor doctor;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="term_definition_id", referencedColumnName="id")
    private TermDefinition term;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="patient_id", referencedColumnName="id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Room room;

    public DoctorTerms() {
        this.active = true;
        this.processedByAdmin = false;
    }

    public DoctorTerms(Long id, long date, Doctor doctor, Patient patient, boolean active,
                       TermDefinition termDefinition, boolean processedByAdmin, Room room, boolean examination) {
        this.id = id;
        this.date = date;
        this.doctor = doctor;
        this.patient = patient;
        this.active = active;
        this.term = termDefinition;
        this.processedByAdmin = processedByAdmin;
        this.room = room;
        this.examination = examination;
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

    public boolean isProcessedByAdmin() {
        return processedByAdmin;
    }

    public void setProcessedByAdmin(boolean processedByAdmin) {
        this.processedByAdmin = processedByAdmin;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean isExamination() {
        return examination;
    }

    public void setExamination(boolean examination) {
        this.examination = examination;
    }
}
