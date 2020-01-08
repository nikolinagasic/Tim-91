package rs.zis.app.zis.domain;

import javax.persistence.*;

@Entity
@Table(name= "MedicalReview")
public class MedicalReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "date")
    private String date;

    @Column(name= "medicalResults")
    private String medicalResults;  //rezulatati analiza, opis simptoma pacienta

    @Column(name= "diagnosis")
    private String diagnosis;

    @Column(name= "therapy")
    private String therapy;    //na osnovu ovoga recepte praviti

    @Column(name= "id_doctor")
    private Long id_doctor;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MedicalRecord medicalRecord;

    public MedicalReview() {
    }

    public MedicalReview(Long id, String date, String medicalResults, String diagnosis, String therapy, Long id_doctor, MedicalRecord medicalRecord) {
        this.id = id;
        this.date = date;
        this.medicalResults = medicalResults;
        this.diagnosis = diagnosis;
        this.therapy = therapy;
        this.id_doctor = id_doctor;
        this.medicalRecord = medicalRecord;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMedicalResults() {
        return medicalResults;
    }

    public void setMedicalResults(String medicalResults) {
        this.medicalResults = medicalResults;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTherapy() {
        return therapy;
    }

    public void setTherapy(String therapy) {
        this.therapy = therapy;
    }

    public Long getId_doctor() {
        return id_doctor;
    }

    public void setId_doctor(Long id_doctor) {
        this.id_doctor = id_doctor;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }
}
