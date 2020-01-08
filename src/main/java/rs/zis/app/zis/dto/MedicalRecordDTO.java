package rs.zis.app.zis.dto;

import rs.zis.app.zis.domain.MedicalRecord;

public class MedicalRecordDTO {

    private Long id;
    private int height;
    private int weight;
    private float dioptreRightEye;
    private float dioptreLeftEye;
    private String bloodGroup;
    private String allergy;
    private String patientMail;

    public MedicalRecordDTO() {
    }

    public MedicalRecordDTO(Long id, int height, int weight, float dioptreRightEye, float dioptreLeftEye, String bloodGroup, String allergy, String patientMail) {
        this.id = id;
        this.height = height;
        this.weight = weight;
        this.dioptreRightEye = dioptreRightEye;
        this.dioptreLeftEye = dioptreLeftEye;
        this.bloodGroup = bloodGroup;
        this.allergy = allergy;
        this.patientMail = patientMail;
    }

    public  MedicalRecordDTO(MedicalRecord medicalRecord){
        this.id = medicalRecord.getId();
        this.height = medicalRecord.getHeight();
        this.weight = medicalRecord.getWeight();
        this.dioptreRightEye = medicalRecord.getDioptreRightEye();
        this.dioptreLeftEye = medicalRecord.getDioptreLeftEye();
        this.bloodGroup = medicalRecord.getBloodGroup();
        this.allergy = medicalRecord.getAllergy();
        this.patientMail = medicalRecord.getPatintMail();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public float getDioptreRightEye() {
        return dioptreRightEye;
    }

    public void setDioptreRightEye(float dioptreRightEye) {
        this.dioptreRightEye = dioptreRightEye;
    }

    public float getDioptreLeftEye() {
        return dioptreLeftEye;
    }

    public void setDioptreLeftEye(float dioptreLeftEye) {
        this.dioptreLeftEye = dioptreLeftEye;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getPatientMail() {
        return patientMail;
    }

    public void setPatientMail(String patientMail) {
        this.patientMail = patientMail;
    }
}
