package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.MedicalReview;

public class MedicalReviewDTO {

    private Long id;
    private long date;
    private String medicalResults;
    private String diagnosis;
    private String therapy;
    private String patient_mail;  //na osnovu njega vezujem karton pacijenta
    private Long id_doctor;   //na osnovu njega vezujem koji lekar je overio izvestaj

    public MedicalReviewDTO() {
    }

    public MedicalReviewDTO(Long id, long date, String medicalResults, String diagnosis, String therapy, String patient_mail, Long id_doctor) {
        this.id = id;
        this.date = date;
        this.medicalResults = medicalResults;
        this.diagnosis = diagnosis;
        this.therapy = therapy;
        this.patient_mail = patient_mail;
        this.id_doctor = id_doctor;
    }

    public MedicalReviewDTO(MedicalReview medicalReview) {
        this.id = medicalReview.getId();
        this.date = medicalReview.getDate();
        this.medicalResults = medicalReview.getMedicalResults();
        this.diagnosis = medicalReview.getDiagnosis();
        this.therapy = medicalReview.getTherapy();
        this.patient_mail = medicalReview.getMedicalRecord().getPatintMail();
        this.id_doctor = medicalReview.getId_doctor();
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

    public String getPatient_mail() {
        return patient_mail;
    }

    public void setPatient_mail(String patient_mail) {
        this.patient_mail = patient_mail;
    }

    public Long getId_doctor() {
        return id_doctor;
    }

    public void setId_doctor(Long id_doctor) {
        this.id_doctor = id_doctor;
    }
}
