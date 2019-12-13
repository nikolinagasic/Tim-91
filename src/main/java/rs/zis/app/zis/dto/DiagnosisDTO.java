package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.Diagnosis;

public class DiagnosisDTO {

    private Long id;
    private String cure_password;
    private String cure_name;
    private String diagnosis_password;
    private String diagnosis_name;

    public DiagnosisDTO() {
    }

    public DiagnosisDTO(Long id, String cure_password, String cure_name, String diagnosis_password, String diagnosis_name) {
        this.id = id;
        this.cure_password = cure_password;
        this.cure_name = cure_name;
        this.diagnosis_password = diagnosis_password;
        this.diagnosis_name = diagnosis_name;
    }

    public DiagnosisDTO(Diagnosis d){
        this.id=d.getId();
        this.cure_password=d.getCure_password();
        this.cure_name=d.getCure_name();
        this.diagnosis_password=d.getDiagnosis_password();
        this.diagnosis_name=d.getDiagnosis_name();
    }

    public Long getId() { return id; }

    public String getCure_password() {
        return cure_password;
    }

    public String getCure_name() {
        return cure_name;
    }

    public String getDiagnosis_password() {
        return diagnosis_password;
    }

    public String getDiagnosis_name() { return diagnosis_name; }

}
