package rs.zis.app.zis.domain;

import javax.persistence.*;

@Entity
@Table(name = "Diagnosis")
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "cure_password")
    private String cure_password;

    @Column(name="cure_name")
    private String cure_name;

    @Column(name="diagnosis_password")
    private String diagnosis_password;

    @Column(name="diagnosis_name")
    private String diagnosis_name;

    public Diagnosis() {

    }

    public Diagnosis(Long id, String cure_password, String cure_name, String diagnosis_password, String diagnosis_name) {
        this.id=id;
        this.cure_password = cure_password;
        this.cure_name = cure_name;
        this.diagnosis_password = diagnosis_password;
        this.diagnosis_name = diagnosis_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCure_password() {
        return cure_password;
    }

    public void setCure_password(String cure_password) {
        this.cure_password = cure_password;
    }

    public String getCure_name() {
        return cure_name;
    }

    public void setCure_name(String cure_name) {
        this.cure_name = cure_name;
    }

    public String getDiagnosis_password() {
        return diagnosis_password;
    }

    public void setDiagnosis_password(String diagnosis_password) {
        this.diagnosis_password = diagnosis_password;
    }

    public String getDiagnosis_name() {
        return diagnosis_name;
    }

    public void setDiagnosis_name(String diagnosis_name) {
        this.diagnosis_name = diagnosis_name;
    }
}
