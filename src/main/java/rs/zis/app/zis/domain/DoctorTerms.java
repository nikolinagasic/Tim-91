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

    @Column(name = "price", nullable = false)    // cena
    private double price;

    @Column(name = "discount", nullable = false)    // popust
    private int discount;

    @Column(name = "rate_clinic", nullable = false)    // pacijent ocenio kliniku
    private boolean rate_clinic;

    @Column(name = "rate_doctor", nullable = false)    // pacijent ocenio doktora
    private boolean rate_doctor;

    @Version
    private Long version;

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
        this.rate_clinic = false;
        this.rate_doctor = false;
    }

    public DoctorTerms(Long id, long date, Doctor doctor, Patient patient, boolean active,
                       TermDefinition termDefinition, boolean processedByAdmin, Room room, boolean examination,
                       double price, int discount, boolean predefined) {
        this.id = id;
        this.date = date;
        this.doctor = doctor;
        this.patient = patient;
        this.active = active;
        this.term = termDefinition;
        this.processedByAdmin = processedByAdmin;
        this.room = room;
        this.examination = examination;
        this.price = price;
        this.discount = discount;
        this.predefined = predefined;
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

    public boolean isPredefined() {
        return predefined;
    }

    public void setPredefined(boolean predefined) {
        this.predefined = predefined;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public boolean isRate_clinic() {
        return rate_clinic;
    }

    public void setRate_clinic(boolean rate_clinic) {
        this.rate_clinic = rate_clinic;
    }

    public boolean isRate_doctor() {
        return rate_doctor;
    }

    public void setRate_doctor(boolean rate_doctor) {
        this.rate_doctor = rate_doctor;
    }
}
